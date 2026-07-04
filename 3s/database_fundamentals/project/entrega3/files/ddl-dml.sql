---------------- CRIACAO DO BANCO
-- Enums
CREATE TYPE tipo_onibus AS ENUM ('Convencional', 'Executivo', 'Semi-Leito', 'Leito', 'Leito-Cama');
CREATE TYPE status_viagem AS ENUM ('Agendada', 'Em Andamento', 'Concluida', 'Cancelada', 'Atrasada');
CREATE TYPE status_passagem AS ENUM ('Pendente', 'Confirmada', 'Cancelada', 'Reembolsada', 'Utilizada');

-- Pessoa para heranca
CREATE TABLE pessoa (
    cpf VARCHAR(15) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL
);

-- Telefone (mutivalorado)
CREATE TABLE pessoa_telefone (
    cpf VARCHAR(15) REFERENCES pessoa(cpf),
    telefone VARCHAR(15) NOT NULL,
    PRIMARY KEY (cpf, telefone)
);

CREATE TABLE viacao (
    id_viacao SERIAL PRIMARY KEY,
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL
);

-- Motorista herda de pessoa
CREATE TABLE motorista (
    cpf VARCHAR(15) PRIMARY KEY REFERENCES pessoa(cpf),
    cnh VARCHAR(15) UNIQUE NOT NULL,
    id_viacao INT NOT NULL REFERENCES viacao(id_viacao)
);

-- Usuario herda de pessoa
CREATE TABLE usuario (
    cpf VARCHAR(15) PRIMARY KEY REFERENCES pessoa(cpf)
);

CREATE TABLE onibus (
    id_onibus SERIAL PRIMARY KEY,
    placa VARCHAR(8) UNIQUE NOT NULL,
    capacidade INT NOT NULL,
    tipo tipo_onibus NOT NULL,
    id_viacao INT NOT NULL REFERENCES viacao(id_viacao)
);

-- Entidade fraca de ônibus
CREATE TABLE assento (
    id_onibus INT REFERENCES onibus(id_onibus),
    numero_assento VARCHAR(10) NOT NULL,
    PRIMARY KEY (id_onibus, numero_assento)
);

CREATE TABLE cidade (
    id_cidade SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL
);

CREATE TABLE rodoviaria (
    id_rodoviaria SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    rua VARCHAR(100) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    bairro VARCHAR(50) NOT NULL,
    id_cidade INT NOT NULL REFERENCES cidade(id_cidade)
);

CREATE TABLE viagem (
    id_viagem SERIAL PRIMARY KEY,
    status status_viagem NOT NULL,
    id_onibus INT NOT NULL REFERENCES onibus(id_onibus)
);

-- Tabela associativa para permitir varios motoristas em uma mesma viagem
CREATE TABLE viagem_motorista (
    id_viagem INT REFERENCES viagem(id_viagem),
    cpf_motorista VARCHAR(15) REFERENCES motorista(cpf),
    PRIMARY KEY (id_viagem, cpf_motorista)
);

-- Setor especifico da viagem
CREATE TABLE trecho (
    id_trecho SERIAL PRIMARY KEY,
    id_viagem INT NOT NULL REFERENCES viagem(id_viagem),
    ordem INT NOT NULL,
    distancia DECIMAL(6,2) NOT NULL,
    data_hora_saida TIMESTAMP NOT NULL,
    data_hora_chegada TIMESTAMP NOT NULL,
    preco_trecho DECIMAL(10,2) NOT NULL,
    id_origem INT NOT NULL REFERENCES rodoviaria(id_rodoviaria),
    id_destino INT NOT NULL REFERENCES rodoviaria(id_rodoviaria)
);

CREATE TABLE passagem (
    id_passagem SERIAL PRIMARY KEY,
    data_compra TIMESTAMP NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    status status_passagem NOT NULL,
    cpf_usuario VARCHAR(15) NOT NULL REFERENCES usuario(cpf),
    id_viagem INT NOT NULL REFERENCES viagem(id_viagem)
);

-- Relacionamento ternário
CREATE TABLE reserva (
    id_passagem INT REFERENCES passagem(id_passagem),
    id_trecho INT REFERENCES trecho(id_trecho),
    id_onibus INT NOT NULL,
    numero_assento VARCHAR(10) NOT NULL,
    FOREIGN KEY (id_onibus, numero_assento) REFERENCES assento(id_onibus, numero_assento),
    PRIMARY KEY (id_passagem, id_trecho, id_onibus, numero_assento),
    CONSTRAINT assento_unico_por_trecho UNIQUE (id_trecho, id_onibus, numero_assento)
);






--DML ----------------------------------------------------------------------------
DO $$
DECLARE
    -- Arrays para geração de dados realistas
    nomes TEXT[] := ARRAY['João', 'Maria', 'José', 'Ana', 'Carlos', 'Antônio', 'Francisco', 'Luiz', 'Paulo', 'Marcos', 'Lucas', 'Pedro', 'Rafael', 'Diego', 'Bruno', 'Rodrigo', 'Fernanda', 'Juliana', 'Camila', 'Aline', 'Amanda', 'Letícia', 'Larissa', 'Bruna'];
    sobrenomes TEXT[] := ARRAY['Silva', 'Santos', 'Oliveira', 'Souza', 'Rodrigues', 'Ferreira', 'Alves', 'Pereira', 'Lima', 'Gomes', 'Costa', 'Ribeiro', 'Martins', 'Carvalho', 'Almeida', 'Lopes', 'Soares', 'Fernandes', 'Vieira', 'Barbosa', 'Rocha'];
    
    -- Cidades do Ceará (40% do peso aproximado)
    cidades_ce TEXT[] := ARRAY['Fortaleza', 'Juazeiro do Norte', 'Sobral', 'Crato', 'Caucaia', 'Maracanaú', 'Iguatu', 'Quixadá', 'Itapipoca', 'Crateús', 'Aracati', 'Camocim', 'Tianguá', 'Barbalha', 'Russas'];
    -- Cidades do Resto do Brasil (60% do peso)
    cidades_br TEXT[] := ARRAY['São Paulo/SP', 'Rio de Janeiro/RJ', 'Belo Horizonte/MG', 'Salvador/BA', 'Brasília/DF', 'Curitiba/PR', 'Recife/PE', 'Porto Alegre/RS', 'Belém/PA', 'Goiânia/GO', 'Manaus/AM', 'Natal/RN', 'João Pessoa/PB', 'Teresina/PI', 'São Luís/MA', 'Maceió/AL', 'Aracaju/SE', 'Florianópolis/SC', 'Vitória/ES', 'Cuiabá/MT'];
    
    viacoes_nomes TEXT[] := ARRAY['Viação Guanabara', 'Auto Viação Progresso', 'Expresso Guanabara', 'Viação Cometa', 'Auto Viação 1001', 'Gontijo', 'Águia Branca', 'Itapemirim', 'Catarinense', 'Reunidas', 'Real Expresso', 'Rápido Federal'];
    
    -- Variáveis de controle
    v_cpf VARCHAR(15);
    v_email VARCHAR(100);
    v_cnpj VARCHAR(18);
    v_placa VARCHAR(8);
    v_id_cidade INT;
    v_id_viacao INT;
    v_id_onibus INT;
    v_id_viagem INT;
    v_id_passagem INT;
    v_id_trecho INT;
    
    -- Variáveis para a lógica das viagens longas
    v_num_trechos INT;
    v_origem_rod INT;
    v_destino_rod INT;
    v_data_atual TIMESTAMP;
    v_distancia DECIMAL(6,2);
    v_preco DECIMAL(10,2);
    
    -- Loops e contadores
    i INT;
    j INT;
    k INT;
    t INT;
    
    -- Cursors para buscar chaves estrangeiras
    c_motoristas VARCHAR(15)[];
    c_usuarios VARCHAR(15)[];
    c_rodoviarias INT[];
    c_onibus_lista INT[];
    c_trechos_viagem INT[];
BEGIN

    --------------------------------------------------------------------
    -- 1. POPULAR TABELA: pessoa (Gerar ~300 para suprir motoristas e usuários)
    --------------------------------------------------------------------
    FOR i IN 1..300 LOOP
        v_cpf := LPAD(CAST(CAST(random() * 89999999999 AS BIGINT) + 10000000000 AS TEXT), 11, '0');
        v_email := lower(nomes[1 + floor(random() * array_length(nomes, 1))]) || i || '@email.com';
        
        INSERT INTO pessoa (cpf, nome, email, data_nascimento)
        VALUES (
            v_cpf,
            nomes[1 + floor(random() * array_length(nomes, 1))] || ' ' || sobrenomes[1 + floor(random() * array_length(sobrenomes, 1))],
            v_email,
            -- CORREÇÃO: DATE em vez de DATA
            DATE '1960-01-01' + CAST(random() * 15000 AS INT)
        ) ON CONFLICT DO NOTHING;
    END LOOP;

    --------------------------------------------------------------------
    -- 2. POPULAR TABELA: pessoa_telefone (Multivalorado)
    --------------------------------------------------------------------
    FOR v_cpf IN SELECT cpf FROM pessoa LOOP
        INSERT INTO pessoa_telefone (cpf, telefone)
        VALUES (v_cpf, '(85) 9' || CAST(CAST(random() * 89999999 AS INT) + 10000000 AS TEXT));
        -- Alguns têm dois telefones
        IF random() > 0.6 THEN
            INSERT INTO pessoa_telefone (cpf, telefone)
            VALUES (v_cpf, '(85) 9' || CAST(CAST(random() * 89999999 AS INT) + 10000000 AS TEXT))
            ON CONFLICT DO NOTHING;
        END IF;
    END LOOP;

    --------------------------------------------------------------------
    -- 3. POPULAR TABELA: viacao (120 Empresas)
    --------------------------------------------------------------------
    FOR i IN 1..120 LOOP
        v_cnpj := LPAD(i::TEXT, 14, '0'); 
        INSERT INTO viacao (cnpj, nome)
        VALUES (
            v_cnpj, 
            viacoes_nomes[1 + ((i-1) % array_length(viacoes_nomes, 1))] || ' - Filial ' || i
        );
    END LOOP;

    --------------------------------------------------------------------
    -- 4. POPULAR TABELA: motorista (130 motoristas)
    --------------------------------------------------------------------
    i := 1;
    FOR v_cpf IN SELECT cpf FROM pessoa LIMIT 130 LOOP
        SELECT id_viacao INTO v_id_viacao FROM viacao OFFSET (i % 120) LIMIT 1;
        INSERT INTO motorista (cpf, cnh, id_viacao)
        VALUES (v_cpf, LPAD(CAST(CAST(random() * 899999999 AS BIGINT) + 100000000 AS TEXT), 11, '0'), v_id_viacao);
        i := i + 1;
    END LOOP;

    --------------------------------------------------------------------
    -- 5. POPULAR TABELA: usuario (Restante das pessoas viram usuários, > 150)
    --------------------------------------------------------------------
    INSERT INTO usuario (cpf)
    SELECT cpf FROM pessoa WHERE cpf NOT IN (SELECT cpf FROM motorista);

    --------------------------------------------------------------------
    -- 6. POPULAR TABELA: cidade (Cumprindo a regra dos 40% Ceará)
    --------------------------------------------------------------------
    -- 50 Cidades do Ceará (40% de 125)
    FOR i IN 1..50 LOOP
        INSERT INTO cidade (nome, estado) 
        VALUES (cidades_ce[1 + ((i-1) % array_length(cidades_ce, 1))] || ' ' || i, 'CE');
    END LOOP;
    
    -- 75 Cidades do Resto do Brasil (60%)
    FOR i IN 1..75 LOOP
        INSERT INTO cidade (nome, estado) 
        VALUES (
            split_part(cidades_br[1 + ((i-1) % array_length(cidades_br, 1))], '/', 1) || ' ' || i, 
            split_part(cidades_br[1 + ((i-1) % array_length(cidades_br, 1))], '/', 2)
        );
    END LOOP;

    --------------------------------------------------------------------
    -- 7. POPULAR TABELA: rodoviaria (130 Rodoviárias distribuídas nas cidades)
    --------------------------------------------------------------------
    i := 1;
    FOR v_id_cidade IN SELECT id_cidade FROM cidade LOOP
        INSERT INTO rodoviaria (nome, cep, rua, numero, bairro, id_cidade)
        VALUES (
            'Terminal Rodoviário de ' || (SELECT nome FROM cidade WHERE id_cidade = v_id_cidade),
            LPAD(CAST(CAST(random() * 89999 AS INT) + 10000 AS TEXT), 5, '0') || '-000',
            'Av. Central',
            CAST(CAST(random() * 2000 AS INT) + 1 AS TEXT),
            'Centro',
            v_id_cidade
        );
        i := i + 1;
        EXIT WHEN i > 130;
    END LOOP;
    
    WHILE (SELECT count(*) FROM rodoviaria) < 130 LOOP
        SELECT id_cidade INTO v_id_cidade FROM cidade ORDER BY random() LIMIT 1;
        INSERT INTO rodoviaria (nome, cep, rua, numero, bairro, id_cidade)
        VALUES ('Terminal Secundário ' || random(), '60000-000', 'Rua B', '12', 'Bairro', v_id_cidade);
    END LOOP;

    --------------------------------------------------------------------
    -- 8. POPULAR TABELA: onibus (130 Ônibus)
    --------------------------------------------------------------------
    FOR i IN 1..130 LOOP
        v_placa := CHR(65 + (random()*25)::int) || CHR(65 + (random()*25)::int) || CHR(65 + (random()*25)::int) || '-' || CAST(CAST(random() * 8999 AS INT) + 1000 AS TEXT);
        SELECT id_viacao INTO v_id_viacao FROM viacao OFFSET (i % 120) LIMIT 1;
        
        INSERT INTO onibus (placa, capacidade, tipo, id_viacao)
        VALUES (
            v_placa,
            42,
            (ARRAY['Convencional', 'Executivo', 'Semi-Leito', 'Leito', 'Leito-Cama'])[1 + floor(random() * 5)]::tipo_onibus,
            v_id_viacao
        );
    END LOOP;

    --------------------------------------------------------------------
    -- 9. POPULAR TABELA: assento (42 assentos por ônibus -> > 5000 entradas)
    --------------------------------------------------------------------
    FOR v_id_onibus IN SELECT id_onibus FROM onibus LOOP
        FOR j IN 1..42 LOOP
            INSERT INTO assento (id_onibus, numero_assento)
            VALUES (v_id_onibus, LPAD(j::TEXT, 2, '0'));
        END LOOP;
    END LOOP;

    --------------------------------------------------------------------
    -- 10. POPULAR TABELA: viagem (125 Viagens)
    --------------------------------------------------------------------
    FOR i IN 1..125 LOOP
        SELECT id_onibus INTO v_id_onibus FROM onibus OFFSET (i-1) LIMIT 1;
        INSERT INTO viagem (status, id_onibus)
        VALUES (
            (ARRAY['Agendada', 'Em Andamento', 'Concluida'])[1 + floor(random() * 3)]::status_viagem,
            v_id_onibus
        );
    END LOOP;

    --------------------------------------------------------------------
    -- 11. POPULAR TABELA: viagem_motorista (Vincula motoristas às viagens)
    --------------------------------------------------------------------
    c_motoristas := ARRAY(SELECT cpf FROM motorista);
    i := 1;
    FOR v_id_viagem IN SELECT id_viagem FROM viagem LOOP
        SELECT m.cpf INTO v_cpf 
        FROM motorista m
        JOIN viacao v ON m.id_viacao = v.id_viacao
        JOIN onibus o ON o.id_viacao = v.id_viacao
        JOIN viagem vi ON vi.id_onibus = o.id_onibus
        WHERE vi.id_viagem = v_id_viagem LIMIT 1;
        
        IF v_cpf IS NULL THEN
            v_cpf := c_motoristas[1 + (i % array_length(c_motoristas, 1))];
        END IF;

        INSERT INTO viagem_motorista (id_viagem, cpf_motorista) VALUES (v_id_viagem, v_cpf) ON CONFLICT DO NOTHING;
        
        IF i <= 30 THEN 
            v_cpf := c_motoristas[1 + ((i+5) % array_length(c_motoristas, 1))];
            INSERT INTO viagem_motorista (id_viagem, cpf_motorista) VALUES (v_id_viagem, v_cpf) ON CONFLICT DO NOTHING;
        END IF;
        i := i + 1;
    END LOOP;

    --------------------------------------------------------------------
    -- 12. POPULAR TABELA: trecho (Viagens longas com 12 a 15 trechos sequenciais)
    --------------------------------------------------------------------
    c_rodoviarias := ARRAY(SELECT id_rodoviaria FROM rodoviaria);
    i := 1;
    
    FOR v_id_viagem IN SELECT id_viagem FROM viagem LOOP
        IF i <= 25 THEN
            v_num_trechos := CAST(random() * 3 AS INT) + 12;
        ELSE
            v_num_trechos := CAST(random() * 3 AS INT) + 2;
        END IF;
        
        v_data_atual := NOW() + (i || ' days')::interval;
        v_origem_rod := c_rodoviarias[1 + (i % array_length(c_rodoviarias, 1))];
        
        FOR t IN 1..v_num_trechos LOOP
            v_destino_rod := c_rodoviarias[1 + ((i + t) % array_length(c_rodoviarias, 1))];
            
            IF v_origem_rod = v_destino_rod THEN
                v_destino_rod := c_rodoviarias[1 + ((i + t + 1) % array_length(c_rodoviarias, 1))];
            END IF;
            
            v_distancia := CAST(100 + (random() * 150) AS DECIMAL(6,2)); 
            v_preco := CAST((v_distancia * 0.35) AS DECIMAL(10,2)); 
            
            INSERT INTO trecho (id_viagem, ordem, distancia, data_hora_saida, data_hora_chegada, preco_trecho, id_origem, id_destino)
            VALUES (
                v_id_viagem,
                t,
                v_distancia,
                v_data_atual,
                v_data_atual + (v_distancia / 60 || ' hours')::interval,
                v_preco,
                v_origem_rod,
                v_destino_rod
            );
            
            v_data_atual := v_data_atual + (v_distancia / 60 || ' hours')::interval + '30 minutes'::interval;
            v_origem_rod := v_destino_rod;
        END LOOP;
        
        i := i + 1;
    END LOOP;

    --------------------------------------------------------------------
    -- 13. POPULAR TABELA: passagem (140 Passagens vendidas)
    --------------------------------------------------------------------
    c_usuarios := ARRAY(SELECT cpf FROM usuario);
    i := 1;
    FOR v_id_viagem IN SELECT id_viagem FROM viagem LOOP
        FOR j IN 1..2 LOOP
            v_cpf := c_usuarios[1 + ((i + j) % array_length(c_usuarios, 1))];
            
            INSERT INTO passagem (data_compra, valor_total, status, cpf_usuario, id_viagem)
            VALUES (
                NOW() - '5 days'::interval,
                0.00, 
                (ARRAY['Confirmada', 'Pendente', 'Utilizada'])[1 + floor(random() * 3)]::status_passagem,
                v_cpf,
                v_id_viagem
            ) RETURNING id_passagem INTO v_id_passagem;
            
            --------------------------------------------------------------------
            -- 14. RELACIONAMENTO TERNÁRIO: reserva
            --------------------------------------------------------------------
            SELECT id_onibus INTO v_id_onibus FROM viagem WHERE id_viagem = v_id_viagem;
            
            c_trechos_viagem := ARRAY(SELECT id_trecho FROM trecho WHERE id_viagem = v_id_viagem ORDER BY ordem);
            
            IF array_length(c_trechos_viagem, 1) >= 2 THEN
                FOR k IN 1..2 LOOP
                    v_id_trecho := c_trechos_viagem[k];
                    
                    INSERT INTO reserva (id_passagem, id_trecho, id_onibus, numero_assento)
                    VALUES (
                        v_id_passagem,
                        v_id_trecho,
                        v_id_onibus,
                        LPAD((10 + j)::TEXT, 2, '0') 
                    ) ON CONFLICT DO NOTHING;
                    
                    UPDATE passagem 
                    SET valor_total = valor_total + (SELECT preco_trecho FROM trecho WHERE id_trecho = v_id_trecho)
                    WHERE id_passagem = v_id_passagem;
                END LOOP;
            END IF;
            
            i := i + 1;
            EXIT WHEN i > 140;
        END LOOP;
        EXIT WHEN i > 140;
    END LOOP;

END $$;