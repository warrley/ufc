SET ROLE admin_user;

INSERT INTO cidade (nome, estado) VALUES ('Quixadá Teste', 'CE');

RESET ROLE;

SET ROLE leitura_user;

SELECT * FROM cidade LIMIT 5;

SELECT * FROM vw_itinerario_trechos;

SELECT * FROM vw_resumo_financeiro_viacao;

INSERT INTO cidade (nome, estado) VALUES ('Invasão', 'SP');

RESET ROLE;

-- 1. Criação dos usuários
CREATE USER admin_user WITH PASSWORD 'senha_admin123';
CREATE USER leitura_user WITH PASSWORD 'senha_leitura123';

-- 2. Concedendo permissões totais para o Admin (Leitura e Escrita)
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO admin_user;

-- Permite que o admin_user crie novas estruturas (Views, Tabelas, etc.) no schema public
GRANT CREATE ON SCHEMA public TO admin_user;

-- 3. Concedendo permissão de leitura para o usuário restrito
GRANT USAGE ON SCHEMA public TO leitura_user;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO leitura_user;

-- 4. USO DO REVOKE (Garantindo restrição explícita conforme requisito)
REVOKE INSERT, UPDATE, DELETE, TRUNCATE ON ALL TABLES IN SCHEMA public FROM leitura_user;

-- 5. Boas práticas: garantir que tabelas criadas no futuro respeitem essas regras
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO admin_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT ON TABLES TO leitura_user;



-- Visão 1: Resumo de Receita e Vendas por Viação (Usa JOIN, GROUP BY e SUM/COUNT)
CREATE OR REPLACE VIEW vw_resumo_financeiro_viacao AS
SELECT 
    v.nome AS nome_viacao,
    COUNT(p.id_passagem) AS total_passagens_vendidas,
    SUM(p.valor_total) AS receita_total
FROM viacao v
JOIN onibus o ON v.id_viacao = o.id_viacao
JOIN viagem vg ON o.id_onibus = vg.id_onibus
JOIN passagem p ON vg.id_viagem = p.id_viagem
WHERE p.status = 'Confirmada'
GROUP BY v.nome;

-- Visão 2: Itinerário Detalhado dos Trechos (Usa múltiplos JOINs na mesma tabela para Origem/Destino)
CREATE OR REPLACE VIEW vw_itinerario_trechos AS
SELECT 
    t.id_trecho,
    c_origem.nome AS cidade_origem,
    r_origem.nome AS rodoviaria_origem,
    c_destino.nome AS cidade_destino,
    r_destino.nome AS rodoviaria_destino,
    t.data_hora_saida,
    t.data_hora_chegada,
    t.preco_trecho
FROM trecho t
JOIN rodoviaria r_origem ON t.id_origem = r_origem.id_rodoviaria
JOIN cidade c_origem ON r_origem.id_cidade = c_origem.id_cidade
JOIN rodoviaria r_destino ON t.id_destino = r_destino.id_rodoviaria
JOIN cidade c_destino ON r_destino.id_cidade = c_destino.id_cidade
ORDER BY t.data_hora_saida;