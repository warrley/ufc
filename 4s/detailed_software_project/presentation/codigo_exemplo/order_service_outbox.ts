/**
 * SOLUÇÃO: TRANSACTIONAL OUTBOX PATTERN (Escrita Atômica Local)
 * 
 * Ambos os registros (Entidade de Domínio + Mensagem Outbox)
 * são persistidos na MESMA transação ACID do PostgreSQL.
 */

import { PoolClient } from 'pg';
import { randomUUID } from 'crypto';

interface NovoPedidoDTO {
  clienteId: string;
  valorTotal: number;
  itens: Array<{ produtoId: string; quantidade: number }>;
}

export class PedidoServiceOutbox {
  constructor(private dbPool: { connect(): Promise<PoolClient> }) {}

  async criarPedido(dados: NovoPedidoDTO): Promise<string> {
    const pedidoId = randomUUID();
    const outboxId = randomUUID();

    const eventoPayload = {
      pedidoId,
      clienteId: dados.clienteId,
      valorTotal: dados.valorTotal,
      itens: dados.itens,
      ocorreuEm: new Date().toISOString()
    };

    const client = await this.dbPool.connect();

    try {
      // 1. INICIA TRANSAÇÃO LOCAL DO BANCO
      await client.query('BEGIN');

      // 2. Grava o dado de negócio
      await client.query(
        `INSERT INTO pedidos (id, cliente_id, valor_total, status)
         VALUES ($1, $2, $3, $4)`,
        [pedidoId, dados.clienteId, dados.valorTotal, 'CRIADO']
      );

      // 3. Grava o evento na tabela outbox NA MESMA TRANSAÇÃO
      await client.query(
        `INSERT INTO outbox_messages 
         (id, aggregate_type, aggregate_id, type, payload)
         VALUES ($1, $2, $3, $4, $5)`,
        [
          outboxId,
          'Pedido',
          pedidoId, // Partition key para manter ordenação por pedido no Kafka
          'PedidoCriadoEvent',
          JSON.stringify(eventoPayload)
        ]
      );

      // 4. COMMIT ATÔMICO
      // Ou ambos persistem no disco com garantias ACID, ou NADA é salvo.
      await client.query('COMMIT');

      return pedidoId;
    } catch (error) {
      await client.query('ROLLBACK');
      console.error('Falha ao criar pedido. Transação revertida com segurança:', error);
      throw error;
    } finally {
      client.release();
    }
  }
}
