/**
 * CONSUMIDOR IDEMPOTENTE (INBOX PATTERN / DEDUPLICAÇÃO)
 * 
 * Como o Transactional Outbox garante entrega AT-LEAST-ONCE, o consumidor
 * PODE receber a mesma mensagem mais de uma vez (ex: se o relay cair antes
 * de marcar `processado_em`).
 * 
 * Este arquivo demonstra como o serviço de destino se protege contra duplicatas
 * gravando a chave do evento na mesma transação que processa o negócio.
 */

import { PoolClient } from 'pg';

interface EventoConsumido {
  id: string; // ID único do evento vindo da tabela outbox
  tipo: string;
  dados: {
    pedidoId: string;
    itens: Array<{ produtoId: string; quantidade: number }>;
  };
}

export class EstoqueConsumerIdempotente {
  constructor(private dbPool: { connect(): Promise<PoolClient> }) {}

  async processarEventoPedidoCriado(evento: EventoConsumido): Promise<void> {
    const client = await this.dbPool.connect();

    try {
      await client.query('BEGIN');

      // 1. TENTA REGISTRAR O ID DO EVENTO NA TABELA DE MENSAGENS PROCESSADAS
      // Se a chave já existir, o PostgreSQL ignora ou lança conflito
      const deduplicacao = await client.query(
        `INSERT INTO mensagens_processadas (mensagem_id)
         VALUES ($1)
         ON CONFLICT (mensagem_id) DO NOTHING
         RETURNING mensagem_id`,
        [evento.id]
      );

      // Se não retornou nada, significa que essa mensagem JÁ FOI PROCESSADA anteriormente!
      if (deduplicacao.rowCount === 0) {
        console.warn(`[Estoque] Mensagem duplicada ignorada com segurança: ${evento.id}`);
        await client.query('COMMIT');
        return;
      }

      // 2. PROCESSA A REGRA DE NEGÓCIO (ex: dar baixa no estoque)
      for (const item of evento.dados.itens) {
        await client.query(
          `UPDATE estoque 
           SET quantidade = quantidade - $1 
           WHERE produto_id = $2`,
          [item.quantidade, item.produtoId]
        );
      }

      // 3. COMMIT ATÔMICO
      // A baixa no estoque e a marcação de duplicata são gravadas juntas!
      await client.query('COMMIT');
      console.log(`[Estoque] Pedido ${evento.dados.pedidoId} processado com sucesso!`);
    } catch (error) {
      await client.query('ROLLBACK');
      console.error(`[Estoque] Erro ao processar evento ${evento.id}:`, error);
      throw error;
    } finally {
      client.release();
    }
  }
}
