# Transactional Outbox Pattern em Microsserviços
## Apresentação Completa de Projeto Detalhado de Software (PDS) — 20 Minutos (3 Pessoas)

---

## 📌 Guia Rápido de Organização do Grupo

- **Duração Total:** 20 minutos cronometrados (aprox. 6 a 7 minutos por integrante + tempo para perguntas da banca).
- **Tema:** Padrão Arquitetural *Transactional Outbox* para publicação confiável de eventos em arquiteturas distribuídas.
- **Divisão dos Papéis:**
  - **Pessoa 1 (00:00 – 06:30):** O Problema do Mundo Distribuído, Cenário de Negócio e o Anti-pattern *Dual-Write*.
  - **Pessoa 2 (06:30 – 13:30):** O Padrão *Transactional Outbox*, Estrutura de Dados e Estratégias de Relay (Polling vs CDC).
  - **Pessoa 3 (13:30 – 20:00):** Garantias de Entrega (*At-Least-Once*), Idempotência no Consumidor, Relação com Sagas, Trade-offs e Conclusão.

---

# PARTE 1 — PESSOA 1: O Problema e a Motivação (00:00 a 06:30)

### 🎯 Meta da Pessoa 1:
Conscientizar a plateia e o professor de que atualizar um banco de dados e notificar um broker de mensageria ao mesmo tempo é uma armadilha clássica de consistência em sistemas distribuídos (*Dual-Write*), e demonstrar por que soluções ingênuas falham.

---

### Slide 1: Contexto e Cenário em Microsserviços (00:00 – 01:45)
**Título do Slide:** O Desafio da Consistência em Sistemas Distribuídos  
**Itens Visuais no Slide:**
- Arquitetura de microsserviços orientada a eventos.
- Regra de Ouro: *Database-per-Service* (cada microsserviço tem seu próprio banco isolado).
- Caso de Uso Real: **Serviço de Pedidos** (E-commerce).
  - Um cliente fecha uma compra no site.
  - O Serviço de Pedidos precisa:
    1. Salvar o pedido com status `CRIADO` no seu banco relacional (PostgreSQL).
    2. Notificar o **Serviço de Estoque** e o **Serviço de Pagamentos** através de um Message Broker (RabbitMQ / Apache Kafka).
- Pergunta norteadora: *“Como garantir que o evento seja publicado se, e somente se, o pedido for gravado com sucesso no banco?”*

> **🗣️ FALA COMPLETA DA PESSOA 1:**  
> *"Bom dia a todos, professor e colegas. Hoje nosso grupo vai apresentar o padrão arquitetural **Transactional Outbox**, uma das soluções mais elegantes e fundamentais para comunicação confiável em arquiteturas de microsserviços.*  
>  
> *Para entender por que esse padrão existe, precisamos olhar para como construímos sistemas modernos. Hoje, o consenso na indústria é quebrar monólitos em microsserviços autônomos seguindo o padrão **Database-per-Service**. Ou seja: o Serviço de Pedidos tem seu próprio banco, o Serviço de Estoque tem o dele, e eles se comunicam por mensagens assíncronas.*  
>  
> *Imaginem o fluxo clássico de um e-commerce: o usuário clica em 'Finalizar Compra'. O Serviço de Pedidos precisa executar duas ações críticas: primeiro, persistir o pedido no seu banco relacional; segundo, emitir um evento `PedidoCriado` para um broker de mensageria — como Kafka ou RabbitMQ — para que o Estoque reserve as peças e o Pagamento processe o cartão.*  
>  
> *Parece uma tarefa trivial em código procedural, certo? Mas no mundo distribuído, estamos lidando com dois sistemas de armazenamento e comunicação completamente heterogêneos. E é aqui que mora o perigo."*

---

### Slide 2: O Anti-pattern "Dual Write" e as Janelas de Falha (01:45 – 04:15)
**Título do Slide:** O Anti-pattern *Dual-Write* e a Perda de Consistência  
**Itens Visuais no Slide:**
- Diagrama de Sequência de Falha (Referência: `dual-write.mmd`).
- As duas abordagens ingênuas e seus desastres correspondentes:

```text
Abordagem A: Salvar no Banco primeiro -> Publicar no Broker depois
[DB COMMIT] ✅  --->  💥 CRASH DA APLICAÇÃO / QUEDA DE REDE  --->  [BROKER] ❌
Resultado: Pedido salvo no banco, mas NUNCA anunciado! (Pedido Fantasma)

Abordagem B: Publicar no Broker primeiro -> Salvar no Banco depois
[BROKER] ✅  --->  💥 ERRO DE CONSTRAINT / TIMEOUT / ROLLBACK  --->  [DB ROLLBACK] ❌
Resultado: Estoque debitado para um pedido que NUNCA existiu no banco!
```

> **🗣️ FALA COMPLETA DA PESSOA 1:**  
> *(Apontando para o slide / diagrama `dual-write.mmd`)*  
> *"A maioria dos desenvolvedores iniciantes tenta resolver isso através do que a literatura chama de **Dual-Write** — fazer duas escritas independentes em rede sequencialmente.*  
>  
> *Vejamos a **Abordagem A**: o desenvolvedor grava no banco com sucesso e dá `COMMIT`. Na linha de código seguinte, ele tenta chamar `broker.publish('PedidoCriado')`. O que acontece se nesse exato milissegundo a conexão com o Kafka cair? Ou se o container da aplicação for terminado por falta de memória (OOM Killer)? O commit no banco já ocorreu e não volta atrás. Porém, o evento nunca foi enviado. O cliente teve seu pedido registrado, mas o estoque nunca foi avisado, o pagamento nunca foi cobrado e o pedido fica eternamente parado em um limbo.*  
>  
> *Aí o desenvolvedor pensa: 'Ah, então vou inverter! Vou publicar no broker primeiro e gravar no banco depois' — essa é a **Abordagem B**. Mas ela é ainda pior! Você envia o evento para o Kafka, o serviço de estoque consome e decrementa os itens da prateleira. Quando seu código tenta gravar no banco de dados, ocorre uma violação de chave estrangeira, um lock timeout ou o banco rejeita a transação. O banco faz `ROLLBACK`. Agora temos um desastre de consistência: o estoque debitou mercadorias para um pedido que juridicamente não existe no banco de pedidos!*  
>  
> *Ambas as abordagens quebram a consistência do sistema porque **não existe atomicidade** nativa entre uma chamada de rede para um broker e uma transação local de banco de dados."*

---

### Slide 3: Por que soluções ingênuas e o 2PC falham? (04:15 – 06:30)
**Título do Slide:** Por que soluções triviais e 2PC não resolvem?  
**Itens Visuais no Slide:**
- Tópico 1: *"E se usarmos um bloco `try/catch` com retentativas (retries)?"*
  - Retries síncronos seguram a requisição do usuário, esgotam threads de pool de conexões e geram efeito dominó (cascading failure).
  - Se o processo da aplicação morrer fisicamente (crash de hardware ou reboot do SO), retries em memória são perdidos.
- Tópico 2: *"E quanto a Transações Distribuídas (Two-Phase Commit / XA)?"*
  - O famoso protocolo 2PC (Coordenador de Transações).
  - Problemas do 2PC:
    - Altíssima latência (múltiplas rodadas de confirmação em rede).
    - Bloqueios prolongados de linhas e tabelas no banco de dados.
    - O coordenador é um ponto único de falha (*Single Point of Failure*).
    - O mercado moderno rejeitou: Apache Kafka, RabbitMQ, AWS SQS e bancos NoSQL modernos **não suportam** o protocolo XA/2PC.

> **🗣️ FALA COMPLETA DA PESSOA 1:**  
> *"Nesse momento, alguém na equipe sempre sugere duas alternativas.*  
>  
> *A primeira é: 'Basta colocar um retry com backoff exponencial no try/catch da publicação do broker'. O problema é que retentativas em memória só funcionam para falhas efêmeras de microssegundos. Se o broker estiver fora por 2 minutos, ou se a máquina virtual reiniciar, toda a memória volátil evapora junto com os eventos que deveriam ser reenviados. Além disso, reter threads esperando o broker responder trava os recursos da API.*  
>  
> *A segunda alternativa teórica seria o **Two-Phase Commit (2PC)** com transações XA, muito estudado em bancos de dados. No 2PC, um coordenador central pergunta para o banco e para o broker: 'Vocês estão prontos para commitar?'. Se ambos disserem sim, ele confirma.*  
> *Por que não usamos isso na prática hoje? Primeiro, porque o 2PC é extremamente lento e gera locks pessimistas que derrubam o throughput de microsserviços. Segundo, e mais definitivo: tecnologias modernas de alta escala como **Apache Kafka, RabbitMQ, Redis e bancos NoSQL simplesmente não implementam XA**. Elas foram desenhadas para desacoplamento assíncrono, não para protocolos de consenso bloqueantes.*  
>  
> *Então temos um impasse: precisamos de garantia de entrega atômica, mas só dispomos de transações locais no nosso banco de dados. Como resolver isso de forma elegante e resiliente?*  
> *Passo a palavra agora para o(a) [Nome da Pessoa 2], que vai apresentar a solução definitiva: o padrão **Transactional Outbox**."*

---

# PARTE 2 — PESSOA 2: O Padrão Outbox e seus Componentes (06:30 a 13:30)

### 🎯 Meta da Pessoa 2:
Apresentar o conceito central do *Transactional Outbox*, a anatomia da tabela Outbox no banco, o diagrama de sequência com transação local atômica e detalhar as duas principais estratégias de publicação do Relay (Polling vs CDC).

---

### Slide 4: A Ideia Central do Transactional Outbox (06:30 – 08:30)
**Título do Slide:** A Ideia do Padrão: Converter I/O de Rede em Transação Local  
**Itens Visuais no Slide:**
- Diagrama de Componentes (Referência: `outbox-componentes.mmd`).
- Conceito Central:
  - Em vez de fazer uma chamada de rede para o broker durante a requisição web...
  - Nós gravamos a intenção do evento em uma tabela do nosso próprio banco relacional chamada **`outbox`** (Caixa de Saída).
  - A gravação do pedido e a gravação da mensagem no `outbox` ocorrem **DENTRO DA MESMA TRANSAÇÃO ACID LOCAL**!
- Fórmula do Sucesso:
  $$\text{Commit Local} = \text{Tabela Negócio (Pedidos)} + \text{Tabela Outbox (Mensagens)}$$
- Se o pedido falhar $\rightarrow$ Rollback em ambos.
- Se o pedido persistir $\rightarrow$ A mensagem está seguramente gravada em disco.

> **🗣️ FALA COMPLETA DA PESSOA 2:**  
> *"Obrigado(a), [Nome da Pessoa 1].*  
> *A sacada genial do padrão **Transactional Outbox** — documentado por Chris Richardson em seu livro de Microservices Patterns — é tão simples quanto brilhante: **se não podemos incluir o broker de mensagens na transação do banco, trazemos a mensagem para dentro do banco de dados!***  
>  
> *Em vez de tentar disparar um pacote TCP para o Kafka enquanto o usuário aguarda a resposta da API, nós criamos uma tabela auxiliar no mesmo banco de dados do microsserviço chamada `outbox`, que funciona como uma caixa de saída de correio.*  
>  
> *Quando o serviço de pedidos cria um novo registro, ele executa uma única transação ACID local: insere o pedido na tabela `pedidos` e insere o evento correspondente na tabela `outbox`. Como ambas as tabelas estão no mesmo banco, as propriedades ACID garantem atomicidade absoluta:*  
> *Se houver erro de negócio, nada é salvo. Mas se a transação der commit, temos a certeza matemática e física de que a mensagem está persistida no disco rígido do banco.*  
>  
> *Agora, a publicação no broker não é mais um problema síncrono da requisição do cliente: ela se torna um processo desacoplado em segundo plano, executado por um componente que chamamos de **Outbox Relay** ou Message Publisher."*

---

### Slide 5: Anatomia da Tabela Outbox e Fluxo Atômico (08:30 – 11:00)
**Título do Slide:** Estrutura de Dados e Diagrama de Sequência  
**Itens Visuais no Slide:**
- Estrutura da tabela `outbox_messages`:
  - `id` (UUID): Identificador único global da mensagem.
  - `aggregate_type` (VARCHAR): Ex: `'Pedido'` (tipo da raiz de agregação DDD).
  - `aggregate_id` (VARCHAR): Ex: `'pedido-123'` (chave de particionamento no Kafka).
  - `type` (VARCHAR): Ex: `'PedidoCriadoEvent'`.
  - `payload` (JSONB): O corpo completo do evento.
  - `criado_em` (TIMESTAMP): Data e hora de criação.
  - `processado_em` (TIMESTAMP NULL): Nulo enquanto pendente; carimbado quando enviado.
- Diagrama de Sequência (Referência: `outbox-sequencia.mmd`).
- Snippet de código demonstrando o bloco `BEGIN ... COMMIT` (Referência: `codigo_exemplo/order_service_outbox.ts`).

> **🗣️ FALA COMPLETA DA PESSOA 2:**  
> *(Mostrando o esquema SQL e o diagrama de sequência `outbox-sequencia.mmd`)*  
> *"Vejam como a modelagem é elegante e direta ao ponto.*  
> *A tabela `outbox_messages` guarda a identidade do evento, o tipo do agregado, o payload serializado em JSON e dois timestamps essenciais: quando a mensagem foi criada e quando ela foi processada.*  
>  
> *Olhando o fluxo de execução:*  
> *1. O serviço abre a transação com `BEGIN`.*  
> *2. Executa o `INSERT` na tabela `pedidos`.*  
> *3. Executa o `INSERT` na tabela `outbox_messages` com o payload do evento e `processado_em` nulo.*  
> *4. Executa o `COMMIT`.*  
>  
> *A partir do momento em que o commit retorna sucesso para a aplicação, o cliente já pode receber um código HTTP 201 Created. O dado está seguro!*  
> *Mas agora vem a pergunta de implementação prática: como essas mensagens saem da tabela `outbox` e chegam até os tópicos do Kafka ou RabbitMQ?*  
> *Existem duas estratégias principais na engenharia de software para fazer esse transporte: o **Polling Publisher** e o **Change Data Capture (CDC)**."*

---

### Slide 6: As Duas Estratégias do Message Relay (11:00 – 13:30)
**Título do Slide:** Como o Relay publica as mensagens? Polling vs CDC  
**Itens Visuais no Slide:**
- Tabela Comparativa entre as duas abordagens:

| Característica | 1. Polling Publisher (Worker) | 2. Transaction Log Tailing (CDC / Debezium) |
|---|---|---|
| **Como funciona?** | Worker executa consultas periódicas com `SELECT ... WHERE processado_em IS NULL` | Conector lê diretamente o log binário de transações do banco (Postgres WAL / MySQL binlog) |
| **Complexidade** | **Baixa:** Feito com código de aplicação padrão (ex: Spring Scheduled, NestJS, Go worker) | **Média/Alta:** Exige infraestrutura dedicada (Kafka Connect, Debezium, permissões de replicação) |
| **Impacto no Banco** | Pode gerar overhead de I/O de queries repetidas em tabelas com milhões de linhas | **Zero overhead de query:** Lê os logs sequenciais de disco que o banco já gera nativamente |
| **Latência** | Limitada pelo intervalo de polling (ex: a cada 1 ou 2 segundos) | **Sub-segundo / Quase Tempo Real** (milissegundos após o commit) |
| **Segredo Técnico** | Requer `FOR UPDATE SKIP LOCKED` para rodar múltiplos workers sem colisão! | Streaming nativo de replicação lógica |

- Diagrama CDC (Referência: `outbox-cdc.mmd`).

> **🗣️ FALA COMPLETA DA PESSOA 2:**  
> *"A primeira estratégia, mais comum em projetos menores ou médios, é o **Polling Publisher**.*  
> *Trata-se de um processo em background — um worker ou cronjob — que a cada poucos segundos roda um `SELECT` buscando mensagens onde `processado_em` é nulo, envia para o broker e roda um `UPDATE` marcando como processado.*  
> *Aqui existe um segredo de implementação fundamental para sistemas concorrentes: se você subir duas instâncias da aplicação para alta disponibilidade, os dois workers podem tentar ler e publicar as mesmas mensagens ao mesmo tempo! No PostgreSQL, resolvemos isso usando a cláusula `FOR UPDATE SKIP LOCKED`. Ela faz com que cada worker pegue um lote exclusivo de registros bloqueados, ignorando as linhas que já estão sendo processadas pelo outro pod. Isso escala com maestria sem race conditions!*  
>  
> *A segunda estratégia, padrão ouro em grandes empresas como Uber, Netflix e Nubank, é o **Change Data Capture (CDC)**, frequentemente implementado com **Debezium e Kafka Connect**.*  
> *O banco de dados grava todas as alterações em um arquivo interno de log sequencial chamado WAL (Write-Ahead Log) no Postgres ou binlog no MySQL. O Debezium conecta diretamente nesse fluxo de replicação de baixo nível. No milissegundo em que o banco grava a linha na tabela outbox, o Debezium intercepta essa alteração diretamente do log de disco e joga no Kafka, sem nunca rodar um único `SELECT` no banco!*  
>  
> *Isso parece perfeito, não é? Mas será que enviar para o broker resolve todos os nossos problemas de confiabilidade? E se o relay cair no meio do caminho?*  
> *Para responder isso e falar sobre as garantias reais de entrega, passo a palavra ao(à) [Nome da Pessoa 3]."*

---

# PARTE 3 — PESSOA 3: Garantias, Idempotência, Sagas e Conclusão (13:30 a 20:00)

### 🎯 Meta da Pessoa 3:
Desmistificar o "Exactly-Once" provando que o Outbox garante *At-Least-Once*, apresentar o *Idempotent Consumer Pattern* (Inbox Pattern), desfazer a confusão entre Outbox e Sagas, listar os trade-offs operacionais e fechar a apresentação com convicção.

---

### Slide 7: O Mito do "Exactly-Once" e a Garantia "At-Least-Once" (13:30 – 15:30)
**Título do Slide:** O Mito do "Exactly-Once": Lidando com a Falha do Relay  
**Itens Visuais no Slide:**
- Cenário Crítico de Falha no Relay:
  ```text
  1. Relay busca mensagem no Outbox: [ID: 99, PedidoCriado]
  2. Relay publica no Apache Kafka com SUCESSO! ✅
  3. 💥 O POD DO RELAY CAI / REDE OSCILA ANTES DO UPDATE NO BANCO!
  4. O banco de dados NUNCA recebe o 'UPDATE outbox SET processado_em = now()'
  5. Novo Relay assume -> Mensagem continua com processado_em = NULL!
  6. Relay lê novamente e REENVIA para o Kafka! ⚠️ (Mensagem Duplicada!)
  ```
- **Conclusão Teórica Inegociável:**
  - O Transactional Outbox garante entrega **AT-LEAST-ONCE (Pelo Menos Uma Vez)**.
  - Ele **NÃO** garante *Exactly-Once* (Exatamente Uma Vez) no transporte!
  - Duplicações em redes distribuídas são matematicamente inevitáveis perante partições de rede.

> **🗣️ FALA COMPLETA DA PESSOA 3:**  
> *"Obrigado(a), [Nome da Pessoa 2].*  
> *Chegamos ao ponto em que muitos engenheiros cometem um erro grave de premissa. Frequentemente ouvimos pessoas dizendo: 'Implementei o Outbox Pattern, então meu sistema agora tem entrega Exactly-Once (exatamente uma vez)'.*  
>  
> *Isto é uma ilusão técnica. O Transactional Outbox garante formalmente entrega **AT-LEAST-ONCE** (pelo menos uma vez). E por que isso acontece?*  
>  
> *Imaginem o seguinte cenário: o Relay lê a mensagem do pedido 99 na tabela outbox. Ele faz a chamada TCP para o Kafka e o Kafka responde: 'Mensagem recebida e commitada no log com sucesso'.*  
> *Nesse momento, antes que o Relay consiga executar a query `UPDATE outbox_messages SET processado_em = NOW()`, o servidor do Relay sofre um desligamento abrupto ou perde a rota de rede com o banco.*  
>  
> *Quando o worker se restabelecer (ou um pod substituto subir no Kubernetes), ele vai olhar para o banco e a mensagem 99 continuará lá, marcada como pendente! O que o worker faz? Ele publica novamente no Kafka.*  
> *Resultado: a mesma mensagem de `PedidoCriado` foi postada duas vezes no broker.*  
> *Portanto, implementar o Outbox no emissor é apenas **metade** da solução arquitetural. A outra metade obrigatória reside no consumidor."*

---

### Slide 8: O Lado do Consumidor: Idempotent Consumer Pattern (15:30 – 17:15)
**Título do Slide:** O Complemento Obrigatório: Consumidor Idempotente (*Inbox Pattern*)  
**Itens Visuais no Slide:**
- Diagrama de Consumo Idempotente (Referência: `inbox-consumidor.mmd`).
- Estratégias de Idempotência:
  1. **Idempotência Natural de Domínio:** Operações que produzem o mesmo estado final não importando quantas vezes rodem (ex: `UPDATE pedido SET status = 'CANCELADO'`).
  2. **Idempotência Sintética (Inbox Pattern / Tabela de Deduplicação):**
     - O consumidor mantém uma tabela `mensagens_processadas(mensagem_id PRIMARY KEY)`.
     - Usa o `id` (UUID) originado lá na tabela Outbox.
     - Executa o processamento do evento e a inserção da chave na mesma transação atômica do banco do consumidor:
       ```sql
       INSERT INTO mensagens_processadas (mensagem_id) VALUES ($1)
       ON CONFLICT (mensagem_id) DO NOTHING;
       ```
     - Se a chave já existir, a transação ignora o processamento e comita com segurança.

> **🗣️ FALA COMPLETA DA PESSOA 3:**  
> *(Apontando para o diagrama `inbox-consumidor.mmd`)*  
> *"Como mensagens duplicadas são uma certeza no mundo distribuído, todo consumidor de eventos precisa ser **idempotente** — ou seja, receber o mesmo evento uma, dez ou cem vezes deve produzir exatamente o mesmo efeito final que recebê-lo uma única vez.*  
>  
> *Algumas operações são naturalmente idempotentes, como definir o status de uma conta para 'SUSPENSA'. Não importa quantas vezes você suspenda, ela continuará suspensa.*  
> *Mas e quando a operação é um débito financeiro ou dedução de estoque? Se você processar duas vezes uma mensagem de 'debitar 1 item', o estoque perderá 2 itens indevidamente.*  
>  
> *Para isso, implementamos o **Idempotent Consumer Pattern**, também conhecido como **Inbox Pattern**. Quando o Serviço de Estoque recebe o evento, ele aproveita o UUID global gerado pelo Outbox e tenta inseri-lo em uma tabela local de `mensagens_processadas` dentro da mesma transação que altera o estoque.*  
> *Se a mensagem for uma duplicata retransmitida pelo relay, a chave primária colide no banco (`ON CONFLICT DO NOTHING`), e o consumidor descarta o processamento duplicado silenciosamente sem causar dano ao negócio!*  
> *Essa união — **Transactional Outbox no produtor + Idempotent Consumer no receptor** — é a receita da indústria para consistência eventual perfeita."*

---

### Slide 9: Esclarecendo a Dúvida Clássica: Outbox vs Saga Pattern (17:15 – 18:45)
**Título do Slide:** Diferenciação Arquitetural: Outbox NÃO é Saga!  
**Itens Visuais no Slide:**
- Matriz Conceitual Comparativa:

| Critério | Transactional Outbox Pattern | Saga Pattern (Coreografia / Orquestração) |
|---|---|---|
| **Qual é o escopo?** | **Local a um único microsserviço** | **Global entre múltiplos microsserviços** |
| **Qual é a responsabilidade?** | Garantir que o banco de dados e o broker fiquem sincronizados na publicação de eventos. | Orquestrar uma transação de negócio distribuída de longa duração e tratar compensações (rollbacks lógicos). |
| **Como eles se relacionam?** | **O Outbox é a fundação da Saga!** Cada serviço participante de uma Saga utiliza o Transactional Outbox para emitir seus comandos e eventos compensatórios de maneira atômica e confiável. |

- Analogia Didática:
  - *"O Outbox é o carteiro confiável que garante que a carta seja colocada na caixa do correio sem ser perdida."*
  - *"A Saga é o contrato de negócio que determina o que fazer caso o destinatário não aceite a encomenda."*

> **🗣️ FALA COMPLETA DA PESSOA 3:**  
> *"Antes de concluirmos, queremos esclarecer uma confusão conceitual muito frequente em provas acadêmicas e entrevistas técnicas de arquitetura: **a confusão entre Outbox e Saga**.*  
>  
> *Muitas pessoas acham que Outbox e Saga são padrões concorrentes ou que fazem a mesma coisa. Isso é incorreto!*  
> *Uma **Saga** gerencia a regra de negócio distribuída: 'Cria pedido $\rightarrow$ cobra cartão $\rightarrow$ reserva estoque'. Se o cartão falhar, a Saga dispara transações compensatórias para desfazer as etapas anteriores.*  
>  
> *O **Transactional Outbox**, por outro lado, opera no nível de infraestrutura de persistência dentro de cada microsserviço individual. O Outbox não sabe o que é uma compensação de negócio; ele apenas garante que, quando um participante da Saga decide publicar o evento `PedidoCriado` ou o evento de compensação `CancelarReserva`, essa publicação ocorra sem falhas de Dual-Write.*  
>  
> *Em resumo: **o Outbox é o alicerce de transporte atômico que torna uma Saga distribuída viável na prática**."*

---

### Slide 10: Trade-offs, Desafios Operacionais e Conclusão (18:45 – 20:00)
**Título do Slide:** Trade-offs, Boas Práticas Operacionais e Conclusão  
**Itens Visuais no Slide:**
- **Os Trade-offs (Nada é de graça na arquitetura):**
  - **Inchaço da Tabela Outbox (Table Bloat):** Em um sistema com 10 milhões de eventos por dia, a tabela outbox cresce rapidamente. *Solução:* Jobs periódicos de limpeza (Purge/Retention Policy), particionamento de tabela por data, ou remoção imediata pós-ACK no caso de CDC.
  - **Ordenação de Mensagens:** Se tivermos múltiplos workers, eventos de um mesmo pedido podem ser processados fora de ordem. *Solução:* Usar o `aggregate_id` como Partition Key no Kafka ou chave de agrupamento no broker.
  - **Consumo Adicional de Espaço e I/O de Banco:** Toda mensagem passa pelo disco antes de ir para a rede.
- **Quando NÃO usar:**
  - Aplicações monolíticas com um único banco compartilhado.
  - Cenários de telemetria/IoT onde a perda de métricas pontuais é aceitável em troca de throughput ultra elevado.
- **Conclusão:**
  - O Transactional Outbox é o padrão definitivo para eliminar o problema do Dual-Write sem o custo proibitivo do 2PC.
  - Transforma consistência eventual em uma realidade robusta, auditável e resiliente a falhas.

> **🗣️ FALA COMPLETA DA PESSOA 3:**  
> *"Para finalizar nossa apresentação, como bons engenheiros de software, precisamos analisar os trade-offs. Nenhum padrão arquitetural é uma bala de prata.*  
>  
> *Primeiro: **Armazenamento e Limpeza**. Se sua aplicação gera milhões de eventos, a tabela outbox vai crescer vertiginosamente. É indispensável implementar políticas de retenção — como deletar mensagens com mais de 7 dias de processadas ou usar tabelas particionadas no Postgres.*  
> *Segundo: **Ordenação**. Para garantir que o evento `PedidoCriado` chegue antes do `PedidoCancelado`, o Relay deve enviar o ID da entidade como chave de partição no Kafka, garantindo que eventos do mesmo agregado caiam na mesma partição ordenada.*  
>  
> *Em conclusão: em arquiteturas de microsserviços, você não pode confiar que a rede e os brokers estarão sempre disponíveis no instante exato do commit. O padrão **Transactional Outbox** converte uma operação arriscada de I/O distribuído em uma transação local ACID sólida como uma rocha.*  
>  
> *Combinado com consumidores idempotentes e ferramentas como Debezium ou workers otimizados com `SKIP LOCKED`, ele forma a espinha dorsal dos maiores sistemas orientados a eventos do mundo.*  
>  
> *Agradecemos a atenção de todos e estamos abertos para perguntas do professor e da turma. Muito obrigado!"*

---

# 🧠 FAQ DA BANCA AVALIADORA: Perguntas Difíceis e Respostas Prontas

Abaixo estão as perguntas mais prováveis que o professor de PDS ou os avaliadores farão após a apresentação, acompanhadas das melhores respostas técnicas estruturadas:

---

### Pergunta 1: *"Por que simplesmente não usamos Two-Phase Commit (2PC) / protocolo XA para coordenar o banco relacional e o Message Broker?"*
- **Quem responde:** Pessoa 1 ou Pessoa 2.
- **Resposta Técnica Ideal:**  
  *"Professor, o 2PC é um protocolo de consenso distribuído síncrono desenhado nas décadas de 80 e 90. Ele sofre de três problemas capitais em microsserviços:  
  1. **Disponibilidade (Teorema CAP):** No 2PC, se um único participante (ou o coordenador) oscilar ou ficar inacessível na fase de preparação (*prepare phase*), todos os outros nós ficam com recursos e locks bloqueados, paralisando o sistema.  
  2. **Latência Inaceitável:** O protocolo exige pelo menos duas viagens de ida e volta na rede (2 Round-Trip Times) com fsync em disco em cada participante antes de liberar o commit, o que destrói o throughput.  
  3. **Suporte Tecnológico:** Praticamente nenhum broker de eventos moderno de alta escala — como Apache Kafka, RabbitMQ em clustering moderno ou AWS SQS — implementa a especificação XA/2PC. Eles foram concebidos sob o princípio do desacoplamento assíncrono e persistência sequencial em log, tornando o 2PC tecnicamente inviável."*

---

### Pergunta 2: *"Na estratégia de Polling Worker, o que acontece se o banco tiver milhões de linhas na tabela outbox? Como evitar que o `SELECT` trave o banco de produção?"*
- **Quem responde:** Pessoa 2.
- **Resposta Técnica Ideal:**  
  *"Excelente pergunta. Se fizermos um `SELECT * FROM outbox WHERE processado_em IS NULL` ingênuo em uma tabela com milhões de linhas, causaremos um Full Table Scan desastroso. Aplicamos três técnicas de engenharia para mitigar isso:  
  1. **Índice Parcial (Partial Index) no PostgreSQL:** Criamos o índice `CREATE INDEX idx_outbox_pendentes ON outbox_messages (criado_em) WHERE processado_em IS NULL`. Como a imensa maioria dos registros já foi processada, esse índice indexa apenas as poucas linhas pendentes daquele segundo, mantendo a árvore B-Tree minúscula (poucos kilobytes) e em memória RAM.  
  2. **Uso de `LIMIT` e Paginação:** O worker nunca lê a tabela inteira; ele processa em lotes (ex: `LIMIT 50` ou `LIMIT 100`).  
  3. **Concorrência com `FOR UPDATE SKIP LOCKED`:** Se precisarmos de múltiplos workers consumindo da outbox para dar vazão, usamos `SKIP LOCKED`. Assim, cada instância pega 50 linhas distintas instantaneamente, sem bloqueio mútuo e sem esperas de lock."*

---

### Pergunta 3: *"Por que o Outbox não pode garantir Exactly-Once por si só? Não poderíamos envolver a publicação no Kafka e o update no banco em uma transação mágica?"*
- **Quem responde:** Pessoa 3.
- **Resposta Técnica Ideal:**  
  *"Não podemos porque caímos no Teorema dos Dois Generais (*Two Generals' Problem*). Quando o Relay envia a mensagem para o broker pela rede, essa mensagem chega ao broker e é gravada. Porém, o pacote de confirmação de rede (ACK) pode ser perdido ou a máquina do Relay pode reiniciar antes de conseguir executar o `UPDATE` no banco de dados.  
  Do ponto de vista do banco de dados, o evento continua pendente. Do ponto de vista do broker, o evento já existe. Quando o Relay reiniciar, para garantir que nada seja perdido, ele obrigatoriamente reenvia a mensagem. Por isso a teoria de sistemas distribuídos prova que em canais não confiáveis só é possível garantir **At-Least-Once** no transporte. A ilusão de Exactly-Once só é alcançada no nível da aplicação somando a entrega At-Least-Once do Outbox com a idempotência no consumidor através do Inbox Pattern."*

---

### Pergunta 4: *"Como vocês resolvem a ordenação dos eventos se usarem múltiplos workers no Outbox Relay?"*
- **Quem responde:** Pessoa 2 ou Pessoa 3.
- **Resposta Técnica Ideal:**  
  *"A ordenação estrita global em sistemas distribuídos de alta concorrência é um gargalo de desempenho massivo. Por isso, seguimos o princípio do Domain-Driven Design (DDD): ordenação só importa **por raiz de agregação** (ex: os eventos do Pedido #100 precisam estar ordenados entre si; mas o Pedido #100 e o Pedido #200 podem ser processados em qualquer ordem relativa).  
  Para garantir isso:  
  1. Na tabela outbox gravamos a coluna `aggregate_id` (o ID do pedido).  
  2. Ao publicar no Apache Kafka, utilizamos o `aggregate_id` como a **Partition Key** da mensagem.  
  3. O Kafka garante ordenação estrita garantida dentro de uma mesma partição. Logo, todos os eventos do mesmo pedido cairão na mesma partição e serão lidos em ordem cronológica pelo consumidor."*

---

### Pergunta 5: *"O que acontece se uma mensagem na Outbox tiver um payload corrompido que o broker rejeita eternamente (uma Poison Pill)?"*
- **Quem responde:** Pessoa 2 ou Pessoa 3.
- **Resposta Técnica Ideal:**  
  *"Essa é a situação clássica da 'pílula de veneno'. Se o worker tentar reenviar indefinidamente uma mensagem com erro semântico, ele pode travar a fila de processamento.  
  Em nossa modelagem da tabela outbox incluímos as colunas `tentativas INT` e `ultimo_erro TEXT`.  
  Implementamos uma política de Dead Letter: após um número limite de falhas consecutivas (ex: 5 tentativas com backoff exponencial), o Relay marca a mensagem com um status de falha definitiva `FALHA_MANUAL`, emite um alerta para a equipe de observabilidade (ex: Prometheus/DataDog) e pula para a próxima mensagem, não bloqueando o fluxo dos outros pedidos saudáveis."*

---

## 📂 Recursos e Diagramas Inclusos no Projeto

Os arquivos nesta pasta servem de apoio visual para a apresentação:
1. `dual-write.mmd`: Sequência do problema do Dual Write (Slide 2 - Pessoa 1).
2. `outbox-componentes.mmd`: Arquitetura de componentes do padrão (Slide 4 - Pessoa 2).
3. `outbox-sequencia.mmd`: Sequência atômica da gravação e do relay (Slide 5 - Pessoa 2).
4. `outbox-cdc.mmd`: Arquitetura de Change Data Capture com Debezium (Slide 6 - Pessoa 2).
5. `inbox-consumidor.mmd`: Fluxo de consumo idempotente com deduplicação (Slide 8 - Pessoa 3).
6. `outbox-classes.mmd`: Diagrama de classes conceituais para referência em PDS.
7. Pasta `codigo_exemplo/`:
   - `schema.sql`: DDL do Postgres com tabela de negócio, outbox com índices parciais e inbox.
   - `anti_pattern_dual_write.ts`: O código incorreto demonstrando os pontos de falha.
   - `order_service_outbox.ts`: O serviço de domínio executando a transação atômica local.
   - `outbox_relay_worker.ts`: O worker de polling concorrente com `FOR UPDATE SKIP LOCKED`.
   - `idempotent_consumer.ts`: O consumidor idempotente demonstrando a deduplicação atômica.
