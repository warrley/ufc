-- 1. Cidades com maior fluxo de passageiros embarcados
SELECT 
    c.id_cidade, 
    c.nome, 
    c.estado, 
    COUNT(r.id_passagem) AS total_embarques 
FROM cidade c 
JOIN rodoviaria ro ON ro.id_cidade = c.id_cidade 
JOIN trecho t ON t.id_origem = ro.id_rodoviaria 
JOIN reserva r ON r.id_trecho = t.id_trecho 
JOIN passagem p ON p.id_passagem = r.id_passagem 
WHERE p.status IN ('Confirmada', 'Utilizada') 
GROUP BY 
    c.id_cidade, 
    c.nome, 
    c.estado 
HAVING COUNT(r.id_passagem) > 20 
ORDER BY total_embarques DESC;


-- 2. Melhores rotas (origem → destino) por receita e demanda
SELECT 
    co.nome AS cidade_origem, 
    co.estado AS uf_origem, 
    cd.nome AS cidade_destino, 
    cd.estado AS uf_destino, 
    COUNT(DISTINCT t.id_viagem) AS qtd_viagens_realizadas, 
    COUNT(r.id_passagem) AS total_passageiros_transportados, 
    SUM(t.preco_trecho) AS receita_estimada, 
    ROUND(AVG(t.preco_trecho), 2) AS preco_medio_praticado 
FROM trecho t 
JOIN rodoviaria ro ON ro.id_rodoviaria = t.id_origem 
JOIN rodoviaria rd ON rd.id_rodoviaria = t.id_destino 
JOIN cidade co ON co.id_cidade = ro.id_cidade 
JOIN cidade cd ON cd.id_cidade = rd.id_cidade 
JOIN reserva r ON r.id_trecho = t.id_trecho 
JOIN passagem p ON p.id_passagem = r.id_passagem 
WHERE p.status IN ('Confirmada', 'Utilizada') 
GROUP BY 
    co.nome, 
    co.estado, 
    cd.nome, 
    cd.estado 
HAVING COUNT(r.id_passagem) >= 10 
ORDER BY receita_estimada DESC;


-- 3. Rodoviárias mais movimentadas (embarques + desembarques)
SELECT 
    r.id_rodoviaria, 
    r.nome, 
    c.nome AS cidade, 
    SUM(movimento.qtd) AS total_movimentos 
FROM rodoviaria r 
JOIN cidade c ON c.id_cidade = r.id_cidade 
JOIN (
    SELECT 
        id_origem AS id_rodoviaria, 
        COUNT(*) AS qtd  
    FROM trecho  
    GROUP BY id_origem 
    
    UNION ALL 
    
    SELECT 
        id_destino AS id_rodoviaria, 
        COUNT(*) AS qtd  
    FROM trecho  
    GROUP BY id_destino
) AS movimento ON movimento.id_rodoviaria = r.id_rodoviaria 
GROUP BY 
    r.id_rodoviaria, 
    r.nome, 
    c.nome 
HAVING SUM(movimento.qtd) >= 5 
ORDER BY total_movimentos DESC;