# Atividade: GitHub Actions - Primeiro Workflow

## Descrição da Atividade

Configuração de um workflow de CI no GitHub Actions executado automaticamente a cada `push` no repositório.

### Requisitos Atendidos:
1. **Gatilho de Execução (`on: push`)**: O workflow roda em qualquer push no repositório.
2. **Action Oficial 1 (`actions/checkout@v4`)**: Faz o download do código-fonte do repositório no runner.
3. **Action Oficial 2 (`actions/setup-node@v4`)**: Configura o ambiente Node.js (versão 20).
4. **Comandos de Execução**:
   - Inspeção de versão do Node e NPM (`node -v`, `npm -v`)
   - Listagem dos arquivos (`ls -la`)
   - Instalação e compilação do projeto TypeScript (`atividade_mongo`)
   - Impressão no console da mensagem de sucesso: `echo "Pipeline executada com sucesso!"`
5. **Caminho do Workflow**: `.github/workflows/primeiro-workflow.yml`
