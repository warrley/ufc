const { Pool } = require("pg");

const pool = new Pool({
  host: process.env.DB_HOST || "postgres",
  port: parseInt(process.env.DB_PORT || "5432", 10),
  user: process.env.DB_USER || "postgres",
  password: process.env.DB_PASSWORD || "postgres",
  database: process.env.DB_NAME || "sales_db",
});

async function initDB(retries = 5, delay = 2000) {
  while (retries > 0) {
    try {
      const client = await pool.connect();
      await client.query(`
        CREATE TABLE IF NOT EXISTS produtos (
          id SERIAL PRIMARY KEY,
          nome VARCHAR(150) NOT NULL,
          descricao TEXT,
          preco NUMERIC(10, 2) NOT NULL,
          estoque INTEGER DEFAULT 0,
          categoria VARCHAR(50),
          criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        );
      `);
      client.release();
      console.log("Conexão com o PostgreSQL estabelecida e tabela de produtos pronta.");
      return;
    } catch (err) {
      console.error(
        `Falha ao conectar com o banco de dados. Tentativas restantes: ${retries - 1}`,
        err.message,
      );
      retries -= 1;
      if (retries === 0) throw err;
      await new Promise((res) => setTimeout(res, delay));
    }
  }
}

module.exports = {
  query: (text, params) => pool.query(text, params),
  initDB,
};
