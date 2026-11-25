-- ==============================================================================
-- SCHEMA SQL: TRANSACTIONAL OUTBOX + IDEMPOTENT INBOX PATTERN
-- Banco de Dados: PostgreSQL
-- ==============================================================================

-- 1. Tabela de Domínio de Negócio
CREATE TABLE pedidos (
    id UUID PRIMARY KEY,
    cliente_id VARCHAR(100) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL, -- 'CRIADO', 'PAGO', 'CANCELADO'
    criado_em TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 2. Tabela Transactional Outbox
-- Armazena eventos que DEVEM ser publicados no Message Broker
CREATE TABLE outbox_messages (
    id UUID PRIMARY KEY,
    aggregate_type VARCHAR(100) NOT NULL,  -- Ex: 'Pedido'
    aggregate_id VARCHAR(100) NOT NULL,    -- Ex: id do pedido (usado como partition key no Kafka)
    type VARCHAR(100) NOT NULL,            -- Ex: 'PedidoCriadoEvent'
    payload JSONB NOT NULL,                -- Corpo do evento serializado em JSON
    criado_em TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    processado_em TIMESTAMP WITH TIME ZONE NULL,
    tentativas INT DEFAULT 0 NOT NULL,
    ultimo_erro TEXT NULL
);

-- Índice parcial essencial para performance de Polling:
-- Apenas indexa mensagens pendentes de processamento, mantendo o índice minúsculo e ultra rápido!
CREATE INDEX idx_outbox_pendentes ON outbox_messages (criado_em)
WHERE processado_em IS NULL;

-- 3. Tabela do Lado Consumidor: Idempotent Consumer (Inbox Pattern)
-- Garante que o consumidor ignore mensagens duplicadas reenviadas pelo Outbox Relay
CREATE TABLE mensagens_processadas (
    mensagem_id UUID PRIMARY KEY,
    processado_em TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL
);
