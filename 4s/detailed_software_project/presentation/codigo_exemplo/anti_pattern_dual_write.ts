/**
 * ANTI-PATTERN: DUAL-WRITE (O que NÃO fazer em microsserviços)
 * 
 * Este arquivo ilustra as falhas catastróficas que ocorrem quando
 * tentamos atualizar o banco e publicar no broker de forma direta.
 */

import { PoolClient } from 'pg';

interface BrokerClient {
  publicarEvento(topico: string, payload: any): Promise<void>;
}

export class PedidoServiceAntiPattern {
  constructor(
    private dbPool: { connect(): Promise<PoolClient> },
    private broker: BrokerClient
  ) {}

  // CENÁRIO 1: Commit no Banco Primeiro, depois publica no Broker
  async criarPedidoComCommitPrimeiro(pedido: any): Promise<void> {
    const client = await this.dbPool.connect();

    try {
      await client.query('BEGIN');
      await client.query(
        'INSERT INTO pedidos (id, cliente_id, valor_total, status) VALUES ($1, $2, $3, $4)',
        [pedido.id, pedido.clienteId, pedido.valorTotal, 'CRIADO']
      );
      await client.query('COMMIT'); // BANCO GRAVADO COM SUCESSO!
    } finally {
      client.release();
    }

    // 💥 JANELA DE FALHA CATASTRÓFICA:
    // Se a aplicação sofrer crash (OOM killer, restart do container, perda de rede)
    // EXATAMENTE AQUI, ou se o broker estiver fora do ar:
    // -> O pedido existe no banco.
    // -> O evento NUNCA é enviado.
    // -> O serviço de estoque/pagamento NUNCA fica sabendo.
    // -> Estado inconsistente permanente!
    await this.broker.publicarEvento('pedidos.criados', {
      pedidoId: pedido.id,
      valorTotal: pedido.valorTotal
    });
  }

  // CENÁRIO 2: Publica no Broker Primeiro, depois faz Commit no Banco
  async criarPedidoComPublicacaoPrimeira(pedido: any): Promise<void> {
    // 1. Publica no broker primeiro achando que está protegido
    await this.broker.publicarEvento('pedidos.criados', {
      pedidoId: pedido.id,
      valorTotal: pedido.valorTotal
    });

    const client = await this.dbPool.connect();
    try {
      await client.query('BEGIN');
      await client.query(
        'INSERT INTO pedidos (id, cliente_id, valor_total, status) VALUES ($1, $2, $3, $4)',
        [pedido.id, pedido.clienteId, pedido.valorTotal, 'CRIADO']
      );
      // 💥 JANELA DE FALHA INVERSA:
      // Se der violação de chave única, timeout de lock ou falha de disco no banco:
      await client.query('COMMIT');
    } catch (err) {
      await client.query('ROLLBACK');
      // -> O evento JÁ FOI para o Kafka/RabbitMQ!
      // -> O Estoque já debitou os itens!
      // -> Mas o pedido NÃO existe no banco de pedidos! Pedido Fantasma!
      throw err;
    } finally {
      client.release();
    }
  }
}
