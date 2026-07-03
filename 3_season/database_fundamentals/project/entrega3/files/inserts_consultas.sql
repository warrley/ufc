-- Este script insere dados determinísticos para garantir que as 3 queries 
-- (Rodoviárias movimentadas, Melhores rotas, e Cidades com maior fluxo)
-- retornem resultados, já que o script DML original gerava dados aleatórios
-- que dificilmente atingiriam os requisitos do HAVING (ex: > 20 passagens).

DO $$
DECLARE
    v_cidade_origem INT;
    v_cidade_destino INT;
    v_rodo_origem INT;
    v_rodo_destino INT;
    v_viacao INT;
    v_onibus INT;
    v_viagem INT;
    v_trecho INT;
    v_usuario VARCHAR(15);
    v_passagem INT;
    i INT;
BEGIN
    -- 1. Buscar registros genéricos já existentes para não ferir foreign keys
    SELECT cpf INTO v_usuario FROM usuario LIMIT 1;
    SELECT id_viacao INTO v_viacao FROM viacao LIMIT 1;

    -- 2. Inserir Cidades específicas para o teste
    INSERT INTO cidade (nome, estado) 
    VALUES ('Cidade Fluxo Máximo', 'CE') 
    RETURNING id_cidade INTO v_cidade_origem;
    
    INSERT INTO cidade (nome, estado) 
    VALUES ('Metrópole Destino', 'SP') 
    RETURNING id_cidade INTO v_cidade_destino;

    -- 3. Inserir Rodoviárias específicas
    INSERT INTO rodoviaria (nome, cep, rua, numero, bairro, id_cidade) 
    VALUES ('Rodoviária Central (Teste)', '60000-000', 'Rua A', '1', 'Centro', v_cidade_origem) 
    RETURNING id_rodoviaria INTO v_rodo_origem;

    INSERT INTO rodoviaria (nome, cep, rua, numero, bairro, id_cidade) 
    VALUES ('Terminal Tietê (Teste)', '01000-000', 'Rua B', '2', 'Centro', v_cidade_destino) 
    RETURNING id_rodoviaria INTO v_rodo_destino;

    -- 4. Inserir Ônibus com 45 lugares
    INSERT INTO onibus (placa, capacidade, tipo, id_viacao) 
    VALUES ('TST-9999', 45, 'Leito', v_viacao) 
    RETURNING id_onibus INTO v_onibus;

    -- Inserir os assentos deste ônibus
    FOR i IN 1..45 LOOP
        INSERT INTO assento (id_onibus, numero_assento) VALUES (v_onibus, LPAD(i::TEXT, 2, '0'));
    END LOOP;

    -- 5. Inserir Viagem
    INSERT INTO viagem (status, id_onibus) 
    VALUES ('Concluida', v_onibus) 
    RETURNING id_viagem INTO v_viagem;

    -- 6. Inserir 6 Trechos (mesmo trajeto) para bater a Query 1 
    -- Query 1: HAVING SUM(movimento.qtd) >= 5
    FOR i IN 1..6 LOOP
        INSERT INTO trecho (id_viagem, ordem, distancia, data_hora_saida, data_hora_chegada, preco_trecho, id_origem, id_destino)
        VALUES (v_viagem, i, 500.00, NOW() - (i || ' days')::interval, NOW() - (i || ' days')::interval + '5 hours'::interval, 150.00, v_rodo_origem, v_rodo_destino)
        RETURNING id_trecho INTO v_trecho; 
        -- v_trecho armazenará o último trecho inserido
    END LOOP;

    -- 7. Inserir Passagens e Reservas para bater a Query 2 e 3
    -- Query 2: HAVING COUNT(r.id_passagem) >= 10
    -- Query 3: HAVING COUNT(r.id_passagem) > 20
    -- Inserindo 25 passagens (Confirmadas) vendidas para esse trajeto!
    
    FOR i IN 1..25 LOOP
        INSERT INTO passagem (data_compra, valor_total, status, cpf_usuario, id_viagem)
        VALUES (NOW() - '10 days'::interval, 150.00, 'Confirmada', v_usuario, v_viagem) 
        RETURNING id_passagem INTO v_passagem;

        -- Vinculando a passagem ao trecho e ao assento do ônibus
        INSERT INTO reserva (id_passagem, id_trecho, id_onibus, numero_assento)
        VALUES (v_passagem, v_trecho, v_onibus, LPAD(i::TEXT, 2, '0'));
    END LOOP;

END $$;
