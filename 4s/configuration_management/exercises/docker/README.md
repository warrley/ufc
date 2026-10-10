# API de Vendas On-line (Back-end)

Aplicação Web back-end com Node.js (Express), banco de dados relacional PostgreSQL e suporte a contêineres via Docker e Docker Compose.

---

## 📁 Estrutura do Projeto

```text
.
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
├── package.json
├── README.md
└── src/
    ├── db.js
    └── server.js
```

---

## 🗄️ Modelo do Produto (`Produto`)

Tabela criada automaticamente no PostgreSQL:
- `id`: Identificador único serial (chave primária)
- `nome`: Nome do produto (`VARCHAR`)
- `descricao`: Descrição detalhada (`TEXT`)
- `preco`: Preço unitário (`NUMERIC(10,2)`)
- `estoque`: Quantidade em estoque (`INTEGER`)
- `categoria`: Categoria do produto (`VARCHAR`)
- `criado_em`: Data e hora de criação (`TIMESTAMP`)

---

## 🚀 Como Executar

Certifique-se de ter o Docker e Docker Compose instalados. Na raiz do projeto, execute:

```bash
docker compose up --build
```

Para rodar em segundo plano:

```bash
docker compose up -d --build
```

Para parar os serviços:

```bash
docker compose down
```

---

## 📌 Rotas da API

A API estará disponível em: `http://localhost:3000`

### 1. Listar todos os produtos
- **Método**: `GET`
- **URL**: `http://localhost:3000/itens`
- **Resposta**: `200 OK` (Array de produtos)

### 2. Listar um produto específico
- **Método**: `GET`
- **URL**: `http://localhost:3000/item/1` ou `http://localhost:3000/item?id=1`
- **Resposta**: `200 OK` (Objeto do produto) ou `404 Not Found`

### 3. Cadastrar produto
- **Método**: `POST`
- **URL**: `http://localhost:3000/item`
- **Headers**: `Content-Type: application/json`
- **Body** (JSON):
```json
{
  "nome": "Notebook Dell Inspiron",
  "descricao": "Intel Core i7, 16GB RAM, 512GB SSD",
  "preco": 4299.90,
  "estoque": 15,
  "categoria": "Informática"
}
```
- **Resposta**: `201 Created`

### 4. Excluir produto
- **Método**: `DELETE`
- **URL**: `http://localhost:3000/item/1` ou `http://localhost:3000/item?id=1`
- **Resposta**: `200 OK` ou `404 Not Found`

---

## 🧪 Exemplos de Teste via cURL

```bash
# 1. Cadastrar produto
curl -X POST http://localhost:3000/item \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Smartphone Galaxy S23",
    "descricao": "256GB, Preto, 5G",
    "preco": 3499.00,
    "estoque": 20,
    "categoria": "Smartphones"
  }'

# 2. Listar todos os produtos
curl -X GET http://localhost:3000/itens

# 3. Obter um produto por ID
curl -X GET http://localhost:3000/item/1

# 4. Excluir produto
curl -X DELETE http://localhost:3000/item/1
```
