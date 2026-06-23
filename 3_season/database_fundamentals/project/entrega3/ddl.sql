------------------------------------------------------ CRIACAO DO BANCO
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
