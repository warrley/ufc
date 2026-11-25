/**
 * COMPONENTE RELAY / MESSAGE PUBLISHER
 * 
 * Processo em background (Worker) responsável por:
 * 1. Fazer polling seguro das mensagens pendentes na tabela outbox
 * 2. Publicar no Message Broker (Kafka / RabbitMQ)
 * 3. Marcar a mensagem como processada
 * 
 * DESTAQUE TÉCNICO:
 * Usa `FOR UPDATE SKIP LOCKED` do PostgreSQL para permitir múltiplos
 * workers em paralelo sem concorrência de leitura ou bloqueio mútuo!
 */

import { PoolClient } from 'pg';

interface MessageBroker {
  publish(topic: string, partitionKey: string, payload: any): Promise<void>;
}

export class OutboxRelayWorker {
  private isRunning = false;

  constructor(
    private dbPool: { connect(): Promise<PoolClient> },
    private broker: MessageBroker,
    private pollIntervalMs: number = 2000,
    private batchSize: number = 50
  ) {}

  async start() {
    this.isRunning = true;
    console.log('[OutboxRelay] Worker iniciado. Monitorando tabela outbox...');

    while (this.isRunning) {
      try {
        await this.processBatch();
      } catch (err) {
        console.error('[OutboxRelay] Erro ao processar lote:', err);
      }
      await new Promise(resolve => setTimeout(resolve, this.pollIntervalMs));
    }
  }

  stop() {
    this.isRunning = false;
  }

  private async processBatch(): Promise<void> {
    const client = await this.dbPool.connect();

    try {
      await client.query('BEGIN');

      // 🔒 FOR UPDATE SKIP LOCKED:
      // Se houver múltiplos pods/instâncias rodando este relay, cada um
      // puxa 50 mensagens diferentes simultaneamente sem colisão!
      const query = `
        SELECT id, aggregate_id, type, payload
        FROM outbox_messages
        WHERE processado_em IS NULL
        ORDER BY criado_em ASC
        LIMIT $1
        FOR UPDATE SKIP LOCKED
      `;

      const result = await client.query(query, [this.batchSize]);

      if (result.rows.length === 0) {
        await client.query('COMMIT');
        return;
      }

      console.log(`[OutboxRelay] Processando lote de ${result.rows.length} mensagens...`);

      for (const row of result.rows) {
        try {
          // Publica no broker
          await this.broker.publish(
            'eventos.pedidos',
            row.aggregate_id, // chave de partição (garante ordem no Kafka)
            {
              id: row.id,
              tipo: row.type,
              dados: row.payload
            }
          );

          // Atualiza status para processado
          await client.query(
            `UPDATE outbox_messages 
             SET processado_em = NOW() 
             WHERE id = $1`,
            [row.id]
          );
        } catch (publishError: any) {
          console.error(`[OutboxRelay] Falha ao publicar mensagem ${row.id}:`, publishError);

          // Incrementa contagem de tentativas e guarda último erro
          await client.query(
            `UPDATE outbox_messages 
             SET tentativas = tentativas + 1, ultimo_erro = $2
             WHERE id = $1`,
            [row.id, String(publishError.message || publishError)]
          );
          // Não derrubamos o lote inteiro se uma falhar, mas podemos interromper
          // para preservar a ordem se for estrito.
        }
      }

      await client.query('COMMIT');
    } catch (error) {
      await client.query('ROLLBACK');
      throw error;
    } finally {
      client.release();
    }
  }
}
