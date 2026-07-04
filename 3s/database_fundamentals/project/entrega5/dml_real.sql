-- DML REAL E ESTRUTURADO PARA AS CONSULTAS (ENTREGA 4 / FBD)
-- Limpeza prévia para evitar duplicações se rodado várias vezes
TRUNCATE reserva, passagem, trecho, viagem_motorista, viagem, assento, onibus, motorista, usuario, viacao, pessoa_telefone, pessoa, rodoviaria, cidade RESTART IDENTITY CASCADE;

---------------------------------------------------------
-- 1. CIDADES (20 Cidades Reais)
---------------------------------------------------------
INSERT INTO cidade (id_cidade, nome, estado) VALUES 
(1, 'São Paulo', 'SP'),
(2, 'Rio de Janeiro', 'RJ'),
(3, 'Fortaleza', 'CE'),
(4, 'Belo Horizonte', 'MG'),
(5, 'Curitiba', 'PR'),
(6, 'Salvador', 'BA'),
(7, 'Brasília', 'DF'),
(8, 'Porto Alegre', 'RS'),
(9, 'Recife', 'PE'),
(10, 'Goiânia', 'GO'),
(11, 'Campinas', 'SP'),
(12, 'Ribeirão Preto', 'SP'),
(13, 'Juiz de Fora', 'MG'),
(14, 'Uberlândia', 'MG'),
(15, 'Londrina', 'PR'),
(16, 'Florianópolis', 'SC'),
(17, 'Natal', 'RN'),
(18, 'João Pessoa', 'PB'),
(19, 'Maceió', 'AL'),
(20, 'Aracaju', 'SE'),
(21, 'Teresina', 'PI');
-- Ajustando o contador do id serial
SELECT setval('cidade_id_cidade_seq', 21);

---------------------------------------------------------
-- 2. RODOVIÁRIAS (21 Terminais Reais correspondentes)
---------------------------------------------------------
INSERT INTO rodoviaria (id_rodoviaria, nome, cep, rua, numero, bairro, id_cidade) VALUES 
(1, 'Terminal Rodoviário Tietê', '02030-000', 'Av. Cruzeiro do Sul', '1800', 'Santana', 1),
(2, 'Rodoviária Novo Rio', '20220-310', 'Av. Francisco Bicalho', '1', 'Santo Cristo', 2),
(3, 'Terminal Rodoviário Eng. João Thomé', '60415-510', 'Av. Borges de Melo', '1630', 'Fátima', 3),
(4, 'Terminal Rodoviário Governador Israel Pinheiro', '30111-050', 'Praça Rio Branco', '100', 'Centro', 4),
(5, 'Rodoferroviária de Curitiba', '80060-090', 'Av. Pres. Affonso Camargo', '330', 'Jardim Botânico', 5),
(6, 'Terminal Rodoviário de Salvador', '41110-000', 'Av. Antônio Carlos Magalhães', '4362', 'Pituba', 6),
(7, 'Rodoviária Interestadual de Brasília', '70610-635', 'SMAS, Trecho 4', '5/6', 'Zona Industrial', 7),
(8, 'Estação Rodoviária de Porto Alegre', '90035-040', 'Largo Vespasiano Júlio Veppo', '70', 'Centro Histórico', 8),
(9, 'Terminal Integrado de Passageiros (TIP)', '50790-900', 'Rodovia BR-232', 'Km 15', 'Várzea', 9),
(10, 'Terminal Rodoviário de Goiânia', '74063-010', 'Rua 44', '399', 'Setor Norte Ferroviário', 10),
(11, 'Terminal Rodoviário de Campinas', '13035-505', 'Rua Dr. Pereira Lima', '85', 'Vila Industrial', 11),
(12, 'Terminal Rodoviário de Ribeirão Preto', '14010-040', 'Av. Jerônimo Gonçalves', '640', 'Centro', 12),
(13, 'Terminal Rodoviário Miguel Mansur', '36080-060', 'Av. Brasil', '9501', 'São Dimas', 13),
(14, 'Terminal Rodoviário Presidente Castelo Branco', '38400-384', 'Praça da Bíblia', '200', 'Martins', 14),
(15, 'Terminal Rodoviário de Londrina', '86046-140', 'Av. Dez de Dezembro', '1830', 'Igapó', 15),
(16, 'Terminal Rodoviário Rita Maria', '88010-280', 'Av. Paulo Fontes', '1101', 'Centro', 16),
(17, 'Complexo Rodoviário Severino Tomaz da Silveira', '59070-400', 'Av. Capitão-Mor Gouveia', '1237', 'Cidade da Esperança', 17),
(18, 'Terminal Rodoviário de João Pessoa', '58010-150', 'Rua Francisco Londres', '151', 'Varadouro', 18),
(19, 'Terminal Rodoviário João Paulo II', '57040-000', 'Av. Gov. Lamenha Filho', '2083', 'Feitosa', 19),
(20, 'Terminal Rodoviário José Rollemberg Leite', '49080-470', 'Av. Tancredo Neves', 'S/N', 'Capucho', 20),
(21, 'Terminal Rodoviário Lucídio Portela', '64017-115', 'Rod. BR-343', 'S/N', 'Redenção', 21);
SELECT setval('rodoviaria_id_rodoviaria_seq', 21);

---------------------------------------------------------
-- 3. VIAÇÕES (20 Empresas Reais do Brasil)
---------------------------------------------------------
INSERT INTO viacao (id_viacao, cnpj, nome) VALUES 
(1, '45.123.456/0001-00', 'Viação Cometa'),
(2, '12.345.678/0001-11', 'Expresso Guanabara'),
(3, '98.765.432/0001-22', 'Gontijo'),
(4, '33.444.555/0001-33', 'Auto Viação 1001'),
(5, '22.111.999/0001-44', 'Viação Águia Branca'),
(6, '77.888.222/0001-55', 'Itapemirim'),
(7, '66.555.444/0001-66', 'Catarinense'),
(8, '55.444.333/0001-77', 'Reunidas Paulista'),
(9, '44.333.222/0001-88', 'Real Expresso'),
(10, '33.222.111/0001-99', 'Rápido Federal'),
(11, '11.111.111/0001-10', 'Viação Garcia'),
(12, '22.222.222/0001-20', 'Expresso do Sul'),
(13, '33.333.333/0001-30', 'Pluma'),
(14, '44.444.444/0001-40', 'Penha'),
(15, '55.555.555/0001-50', 'Andorinha'),
(16, '66.666.666/0001-60', 'Útil'),
(17, '77.777.777/0001-70', 'Viação Motta'),
(18, '88.888.888/0001-80', 'Eucatur'),
(19, '99.999.999/0001-90', 'Auto Viação Progresso'),
(20, '10.000.000/0001-01', 'Viação Kaissara');
SELECT setval('viacao_id_viacao_seq', 20);

---------------------------------------------------------
-- 4. PESSOAS, USUÁRIOS E MOTORISTAS (Nomes e CPFs Gerados)
---------------------------------------------------------
INSERT INTO pessoa (cpf, nome, email, data_nascimento) VALUES 
('11111111111', 'Carlos Drummond de Andrade', 'carlos.drummond@email.com', '1980-05-12'),
('22222222222', 'Clarice Lispector', 'clarice.lispector@email.com', '1985-11-23'),
('33333333333', 'Machado de Assis', 'machado.assis@email.com', '1975-02-15'),
('44444444444', 'Jorge Amado', 'jorge.amado@email.com', '1990-08-30'),
('55555555555', 'Guimarães Rosa', 'guimaraes.rosa@email.com', '1982-01-10'),
('66666666666', 'Cecília Meireles', 'cecilia.meireles@email.com', '1995-07-05'),
('77777777777', 'Mario Quintana', 'mario.quintana@email.com', '1978-04-18'),
('88888888888', 'Lygia Fagundes Telles', 'lygia.telles@email.com', '1989-12-01'),
('99999999999', 'Graciliano Ramos', 'graciliano.ramos@email.com', '1972-09-25'),
('10101010101', 'José de Alencar', 'jose.alencar@email.com', '1984-06-14'),
('12121212121', 'Érico Veríssimo', 'erico.verissimo@email.com', '1992-03-08'),
('13131313131', 'Vinicius de Moraes', 'vinicius.moraes@email.com', '1987-10-22'),
('14141414141', 'João Cabral de Melo Neto', 'joao.cabral@email.com', '1979-05-05'),
('15151515151', 'Manuel Bandeira', 'manuel.bandeira@email.com', '1981-11-19'),
('16161616161', 'Aluísio Azevedo', 'aluisio.azevedo@email.com', '1993-02-28'),
('17171717171', 'Monteiro Lobato', 'monteiro.lobato@email.com', '1986-07-12'),
('18181818181', 'Rubem Braga', 'rubem.braga@email.com', '1976-04-30'),
('19191919191', 'Rachel de Queiroz', 'rachel.queiroz@email.com', '1991-09-15'),
('20202020202', 'Oswald de Andrade', 'oswald.andrade@email.com', '1983-12-25'),
('21212121212', 'Mário de Andrade', 'mario.andrade@email.com', '1974-08-08');

-- 5 Motoristas
INSERT INTO motorista (cpf, cnh, id_viacao) VALUES 
('11111111111', '12345678901', 1), -- Cometa
('22222222222', '12345678902', 2), -- Guanabara
('33333333333', '12345678903', 3), -- Gontijo
('44444444444', '12345678904', 4), -- 1001
('55555555555', '12345678905', 5); -- Águia Branca

-- 15 Usuários (Passageiros)
INSERT INTO usuario (cpf) VALUES 
('66666666666'), ('77777777777'), ('88888888888'), ('99999999999'), 
('10101010101'), ('12121212121'), ('13131313131'), ('14141414141'), 
('15151515151'), ('16161616161'), ('17171717171'), ('18181818181'), 
('19191919191'), ('20202020202'), ('21212121212');

---------------------------------------------------------
-- 5. ÔNIBUS (Reais, tipos variados)
---------------------------------------------------------
INSERT INTO onibus (id_onibus, placa, capacidade, tipo, id_viacao) VALUES 
(1, 'COM-1001', 46, 'Executivo', 1),
(2, 'GUA-2022', 42, 'Semi-Leito', 2),
(3, 'GON-3033', 26, 'Leito-Cama', 3),
(4, 'AUT-4044', 50, 'Convencional', 4),
(5, 'AGU-5055', 46, 'Executivo', 5),
(6, 'ITA-6066', 42, 'Leito', 6),
(7, 'CAT-7077', 50, 'Convencional', 7),
(8, 'REU-8088', 42, 'Semi-Leito', 8),
(9, 'REA-9099', 26, 'Leito-Cama', 9),
(10, 'RAP-1111', 46, 'Executivo', 10),
(11, 'GAR-2222', 42, 'Leito', 11),
(12, 'SUL-3333', 50, 'Convencional', 12),
(13, 'PLU-4444', 46, 'Executivo', 13),
(14, 'PEN-5555', 42, 'Semi-Leito', 14),
(15, 'AND-6666', 26, 'Leito-Cama', 15),
(16, 'UTI-7777', 46, 'Executivo', 16),
(17, 'MOT-8888', 42, 'Leito', 17),
(18, 'EUC-9999', 50, 'Convencional', 18),
(19, 'PRO-1010', 46, 'Executivo', 19),
(20, 'KAI-2020', 42, 'Semi-Leito', 20);
SELECT setval('onibus_id_onibus_seq', 20);

---------------------------------------------------------
-- 6. GERANDO ASSENTOS DOS ONIBUS VIA BLOCO (Mais prático que 800 INSERTS manuais)
---------------------------------------------------------
DO $$
DECLARE v_id INT; v_cap INT; i INT;
BEGIN
    FOR v_id, v_cap IN SELECT id_onibus, capacidade FROM onibus LOOP
        FOR i IN 1..v_cap LOOP
            INSERT INTO assento (id_onibus, numero_assento) VALUES (v_id, LPAD(i::TEXT, 2, '0'));
        END LOOP;
    END LOOP;
END $$;

---------------------------------------------------------
-- 7. VIAGENS
---------------------------------------------------------
INSERT INTO viagem (id_viagem, status, id_onibus) VALUES 
(1, 'Concluida', 1),   -- SP -> RJ (Viação Cometa)
(2, 'Concluida', 2),   -- CE -> RN -> PB (Expresso Guanabara)
(3, 'Concluida', 3),   -- MG -> SP (Gontijo)
(4, 'Concluida', 4),   -- RJ -> SP (Auto Viação 1001)
(5, 'Concluida', 5),   -- ES -> BA -> PE (Águia Branca - usando id 6, 9)
(6, 'Agendada', 6),    -- PR -> SC -> RS
(7, 'Em Andamento', 7),-- GO -> DF
(8, 'Concluida', 8),   -- SP -> PR
(9, 'Concluida', 9),   -- DF -> GO -> MG
(10, 'Agendada', 10);  -- AL -> SE -> BA
SELECT setval('viagem_id_viagem_seq', 10);

-- Vinculando motoristas às viagens
INSERT INTO viagem_motorista (id_viagem, cpf_motorista) VALUES 
(1, '11111111111'), (2, '22222222222'), (3, '33333333333'), 
(4, '44444444444'), (5, '55555555555'), (6, '11111111111'), 
(7, '22222222222'), (8, '33333333333'), (9, '44444444444'), (10, '55555555555');

---------------------------------------------------------
-- 8. TRECHOS 
-- GARANTIA PARA A QUERY 1: Tietê (1) e Novo Rio (2) precisam de pelo menos 5 movimentos.
---------------------------------------------------------
INSERT INTO trecho (id_trecho, id_viagem, ordem, distancia, data_hora_saida, data_hora_chegada, preco_trecho, id_origem, id_destino) VALUES 
-- Viagem 1: SP (1) -> RJ (2)
(1, 1, 1, 430.00, '2023-10-01 08:00:00', '2023-10-01 14:00:00', 120.50, 1, 2),
-- Viagem 4: RJ (2) -> SP (1)
(2, 4, 1, 430.00, '2023-10-02 10:00:00', '2023-10-02 16:00:00', 130.00, 2, 1),
-- Outros trechos avulsos para Tietê(1) bater os 5 movimentos (Campinas(11) -> Tietê(1) etc)
(3, 8, 1, 95.00, '2023-10-03 14:00:00', '2023-10-03 15:30:00', 45.00, 11, 1),
(4, 8, 2, 400.00, '2023-10-03 16:00:00', '2023-10-03 22:00:00', 150.00, 1, 5),
(5, 3, 1, 580.00, '2023-10-04 22:00:00', '2023-10-05 06:00:00', 180.00, 4, 1),
-- Viagem 2: CE (3) -> RN (17) -> PB (18)  
-- (Esta será a Rota Mestra para as Queries 2 e 3)
(6, 2, 1, 530.00, '2023-11-10 18:00:00', '2023-11-11 02:00:00', 210.00, 3, 17),
(7, 2, 2, 180.00, '2023-11-11 02:30:00', '2023-11-11 05:30:00', 60.00, 17, 18);
SELECT setval('trecho_id_trecho_seq', 7);

---------------------------------------------------------
-- 9. PASSAGENS E RESERVAS (GARANTIAS PARA AS QUERIES 2 E 3)
-- A Query 2 exige: COUNT(r.id_passagem) >= 10 na rota (Fortaleza -> Natal)
-- A Query 3 exige: COUNT(r.id_passagem) > 20 embarques na cidade (Fortaleza)
---------------------------------------------------------
DO $$
DECLARE 
    v_id_pass INT;
    v_user VARCHAR(15);
    c_users VARCHAR(15)[] := ARRAY['66666666666', '77777777777', '88888888888', '99999999999', '10101010101', '12121212121', '13131313131', '14141414141', '15151515151', '16161616161'];
    i INT;
BEGIN
    -- Vamos inserir 25 passagens na Viagem 2 (Fortaleza -> Natal, Trecho 6)
    FOR i IN 1..25 LOOP
        v_user := c_users[1 + (i % 10)];
        
        INSERT INTO passagem (data_compra, valor_total, status, cpf_usuario, id_viagem)
        VALUES ('2023-11-01', 210.00, 'Confirmada', v_user, 2)
        RETURNING id_passagem INTO v_id_pass;

        INSERT INTO reserva (id_passagem, id_trecho, id_onibus, numero_assento)
        VALUES (v_id_pass, 6, 2, LPAD(i::TEXT, 2, '0'));
    END LOOP;

    -- Inserir também umas 12 passagens na Rota SP(1) -> RJ(2) para que ela apareça na Query 2
    FOR i IN 1..12 LOOP
        v_user := c_users[1 + (i % 10)];
        INSERT INTO passagem (data_compra, valor_total, status, cpf_usuario, id_viagem)
        VALUES ('2023-09-25', 120.50, 'Confirmada', v_user, 1)
        RETURNING id_passagem INTO v_id_pass;

        INSERT INTO reserva (id_passagem, id_trecho, id_onibus, numero_assento)
        VALUES (v_id_pass, 1, 1, LPAD(i::TEXT, 2, '0'));
    END LOOP;

    -- Umas passagens normais para as outras viagens...
    FOR i IN 1..5 LOOP
        v_user := c_users[1 + (i % 10)];
        INSERT INTO passagem (data_compra, valor_total, status, cpf_usuario, id_viagem)
        VALUES ('2023-10-01', 130.00, 'Confirmada', v_user, 4)
        RETURNING id_passagem INTO v_id_pass;

        INSERT INTO reserva (id_passagem, id_trecho, id_onibus, numero_assento)
        VALUES (v_id_pass, 2, 4, LPAD(i::TEXT, 2, '0'));
    END LOOP;
END $$;
