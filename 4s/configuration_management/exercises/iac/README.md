# Atividade IaC - Google Cloud Compute Engine

Este projeto provisiona automaticamente um servidor web no **Google Cloud Platform (GCP)** utilizando **Terraform** (Infraestrutura como Código - IaC).

---

## Estrutura dos Arquivos

- `main.tf`: Declaração dos recursos GCP (Regra de Firewall HTTP e VM Compute Engine `e2-micro` com script de inicialização do Apache).
- `variables.tf`: Definição das variáveis (`project_id`, `student_name`, `region`, `zone`).
- `outputs.tf`: Exporta o IP público e o link direto para acesso no navegador.
- `terraform.tfvars.example`: Exemplo de configuração de valores das variáveis.

---

## Passo a Passo de Execução

### Opção 1: Direto no Google Cloud Shell (Recomendado - Mais Rápido)

O Cloud Shell já possui o `gcloud` e o `terraform` instalados e autenticados com a sua conta.

1. Acesse o [Google Cloud Console](https://console.cloud.google.com/).
2. Abra o **Cloud Shell** clicando no ícone `>_` no canto superior direito.
3. Crie uma pasta e suba ou crie os arquivos:
   ```bash
   mkdir -p ~/iac-atividade && cd ~/iac-atividade
   ```
4. Obtenha o seu ID de projeto atual:
   ```bash
   gcloud config get-value project
   ```
5. Copie o arquivo `main.tf`, `variables.tf`, `outputs.tf` para esta pasta (ou crie o arquivo `terraform.tfvars` com seu `project_id` e `student_name`):
   ```bash
   cat << 'EOF' > terraform.tfvars
   project_id   = "SEU_PROJECT_ID_AQUI"
   student_name = "Warley"
   EOF
   ```
6. Inicialize o Terraform:
   ```bash
   terraform init
   ```
7. Aplique a infraestrutura:
   ```bash
   terraform apply -auto-approve
   ```
8. Aguarde cerca de 1 a 2 minutos para a VM inicializar e o script de inicialização (startup script) instalar o Apache.
9. Copie o IP exibido em `website_url` ou `public_ip` e acesse no navegador:
   ```text
   http://<IP_DO_SERVIDOR>
   ```
10. **Tire o print da tela** mostrando:
    - O endereço IP na barra do navegador (ou comando `curl http://<IP>`).
    - A mensagem exibida com o seu nome:
      > `Eu Warley consegui usar IaC na Google Cloud na disciplina de Gerência de Configuração - UFC Quixadá`
11. **Destrua os recursos** após tirar o print para não gerar cobranças:
    ```bash
    terraform destroy -auto-approve
    ```

---

### Opção 2: Pela sua Máquina Local (Arch Linux)

1. Instale o Google Cloud SDK e o Terraform:
   ```bash
   sudo pacman -S terraform google-cloud-cli
   ```
2. Faça login na sua conta Google Cloud:
   ```bash
   gcloud auth application-default login
   ```
3. Crie um arquivo `terraform.tfvars` na pasta `exercises/iac`:
   ```bash
   cp terraform.tfvars.example terraform.tfvars
   ```
   Edite com seu `project_id` real.
4. Execute o Terraform:
   ```bash
   terraform init
   terraform apply
   ```
5. Acesse `http://<IP>` no navegador, capture o print e destrua:
   ```bash
   terraform destroy
   ```
