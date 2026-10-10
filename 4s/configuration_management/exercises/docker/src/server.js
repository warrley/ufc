const express = require("express");
const { initDB, query } = require("./db");

const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.json());

// Listar todos os itens: GET /itens
app.get("/itens", async (req, res) => {
  try {
    const { rows } = await query("SELECT * FROM produtos ORDER BY id ASC;");
    return res.status(200).json(rows);
  } catch (error) {
    console.error("Erro ao buscar itens:", error);
    return res.status(500).json({ error: "Erro interno ao listar itens." });
  }
});

// Listar um item específico: GET /item/:id ou GET /item?id=X
app.get(["/item/:id", "/item"], async (req, res) => {
  try {
    const id = req.params.id || req.query.id;

    if (!id) {
      return res
        .status(400)
        .json({ error: "ID do item é obrigatório (ex: /item/1 ou /item?id=1)." });
    }

    const { rows } = await query("SELECT * FROM produtos WHERE id = $1;", [id]);

    if (rows.length === 0) {
      return res.status(404).json({ error: "Item não encontrado." });
    }

    return res.status(200).json(rows[0]);
  } catch (error) {
    console.error("Erro ao buscar item:", error);
    return res.status(500).json({ error: "Erro interno ao obter item." });
  }
});

// Adicionar um novo item: POST /item
app.post("/item", async (req, res) => {
  try {
    const { nome, descricao, preco, estoque, categoria } = req.body;

    if (!nome || preco === undefined || preco === null) {
      return res.status(400).json({ error: 'Os campos "nome" e "preco" são obrigatórios.' });
    }

    const insertQuery = `
      INSERT INTO produtos (nome, descricao, preco, estoque, categoria)
      VALUES ($1, $2, $3, $4, $5)
      RETURNING *;
    `;
    const values = [
      nome,
      descricao || null,
      parseFloat(preco),
      estoque !== undefined ? parseInt(estoque, 10) : 0,
      categoria || null,
    ];

    const { rows } = await query(insertQuery, values);
    return res.status(201).json(rows[0]);
  } catch (error) {
    console.error("Erro ao cadastrar item:", error);
    return res.status(500).json({ error: "Erro interno ao cadastrar item." });
  }
});

// Excluir um item: DELETE /item/:id ou DELETE /item?id=X
app.delete(["/item/:id", "/item"], async (req, res) => {
  try {
    const id = req.params.id || req.query.id || req.body?.id;

    if (!id) {
      return res
        .status(400)
        .json({ error: "ID do item é obrigatório (ex: /item/1 ou /item?id=1)." });
    }

    const { rowCount } = await query("DELETE FROM produtos WHERE id = $1;", [id]);

    if (rowCount === 0) {
      return res.status(404).json({ error: "Item não encontrado para exclusão." });
    }

    return res.status(200).json({ message: `Item com ID ${id} excluído com sucesso.` });
  } catch (error) {
    console.error("Erro ao excluir item:", error);
    return res.status(500).json({ error: "Erro interno ao excluir item." });
  }
});

async function startServer() {
  try {
    await initDB();
    app.listen(PORT, () => {
      console.log(`Servidor rodando na porta ${PORT}`);
    });
  } catch (err) {
    console.error("Falha ao inicializar o banco de dados:", err);
    process.exit(1);
  }
}

startServer();
