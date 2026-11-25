# Transactional Outbox em microsserviços
## Roteiro para 3 pessoas — 20 minutos

**Objetivo:** explicar o dual write, a solução Outbox, o fluxo de publicação e as garantias e limitações.

**Importante:** Outbox não é Saga. Outbox pode ser usado por um serviço participante de uma Saga para publicar eventos de forma confiável. Se apresentar os dois, título sugerido: “Transactional Outbox como suporte à comunicação em Sagas”.

## Divisão de tempo

| Pessoa | Tempo | Conteúdo |
|---|---:|---|
| 1 | 0:00–6:00 (6 min) | Cenário, microsserviços e problema dual write |
| 2 | 6:00–13:00 (7 min) | Solução Outbox, componentes e fluxo |
| 3 | 13:00–20:00 (7 min) | Implementação, falhas, trade-offs e conclusão |

## Pessoa 1 — problema e motivação (6 min)

**Slide 1 — Cenário (1 min):** serviço de pedidos grava um pedido e precisa avisar estoque e pagamento. Pergunta: como evitar salvar o pedido sem publicar o evento?

**Slide 2 — Dual write (2 min):** duas escritas em sistemas diferentes (banco e broker) podem falhar independentemente. Falha A: pedido confirmado, aplicação cai antes do evento. Falha B: evento enviado, mas banco faz rollback. Resultado: serviços discordam sobre o pedido.

**Slide 3 — Por que não publicar diretamente? (3 min):** publicar antes do commit pode anunciar dado que não existe; publicar depois do commit deixa uma janela para falha. A transação local do banco não inclui automaticamente o broker. Mostrar `dual-write.mmd`.

Transição: “Precisamos registrar a intenção de publicar na mesma transação local que salva o pedido.”

## Pessoa 2 — solução e fluxo (7 min)

**Slide 4 — Ideia do Outbox (2 min):** criar tabela/coleção Outbox no banco do serviço. Na mesma transação, gravar pedido e mensagem pendente. Depois do commit, um relay lê a Outbox e publica no broker.

**Slide 5 — Componentes (3 min):** serviço, banco com dados de negócio e Outbox, relay, broker e consumidor. O relay pode fazer polling (consulta periódica) ou CDC (captura mudanças no log de transações). Mostrar `outbox-componentes.mmd` e `outbox-sequencia.mmd`.

**Slide 6 — Passo a passo (2 min):** (1) BEGIN; (2) inserir pedido; (3) inserir `PedidoCriado` pendente; (4) COMMIT; (5) relay publica; (6) marca como processada; (7) estoque consome. Se houver rollback, pedido e mensagem não persistem.

Pseudocódigo para o slide:

```text
begin transaction
  insert pedido
  insert outbox(PedidoCriado, pendente)
commit

relay: ler pendentes → publicar no broker → marcar processada
```

## Pessoa 3 — garantias e trade-offs (7 min)

**Slide 7 — Garantias e duplicatas (2 min):** normalmente a entrega é *at-least-once* (pelo menos uma vez), não “exactly once”. Se o relay publica e cai antes de marcar como processada, pode reenviar. Consumidores devem ser idempotentes.

**Slide 8 — Implementação e decisões (3 min):** campos típicos: `id`, `tipo`, `payload`, `criada_em`, `processada_em/status`, tentativas/erro. Polling é simples, mas pode aumentar carga e latência. CDC reduz polling, mas adiciona infraestrutura. Considerar concorrência, ordem, retries, observabilidade e limpeza de mensagens antigas.

**Slide 9 — Outbox e Saga (2 min):** Saga coordena uma operação de negócio entre serviços e trata falhas com compensações ou orquestração. Outbox não coordena a Saga; ajuda cada serviço a publicar eventos. Exemplo: pedido publica `PedidoCriado` pela Outbox; a Saga segue para estoque e pagamento. Fechar com a pergunta: “E se o relay publicar, mas cair antes de marcar a mensagem?”

## Fontes para pesquisa e imagens

1. Milan Jovanović, [Implementing the Outbox Pattern](https://milanjovanovic.tech/blog/implementing-the-outbox-pattern) — motivação, implementação em .NET/PostgreSQL, polling, CDC, trade-offs e escalabilidade. A página inclui imagem de fluxo; atribuir ao autor e verificar as condições de uso antes de reutilizar.
2. Chris Richardson, [Transactional Outbox](https://microservices.io/patterns/data/transactional-outbox.html) — contexto, solução, relay, benefícios e duplicatas.
3. AWS Prescriptive Guidance, [Transactional outbox pattern](https://docs.aws.amazon.com/prescriptive-guidance/latest/cloud-design-patterns/transactional-outbox.html) — arquitetura e considerações operacionais.
4. Debezium, [Outbox Event Router](https://debezium.io/documentation/reference/stable/transformations/outbox-event-router.html) — implementação baseada em CDC.
5. AWS Prescriptive Guidance, [Saga pattern](https://docs.aws.amazon.com/prescriptive-guidance/latest/modernization-data-persistence/saga-pattern.html) — referência para diferenciar Saga de Outbox.

Os diagramas `.mmd` desta pasta são originais e editáveis em Mermaid Live Editor ou ferramentas compatíveis. Incluem sequência do problema, sequência da solução, componentes e classes conceituais. O diagrama de classes é apenas um modelo didático; o padrão não exige essas classes específicas.

## Cuidados para a apresentação

- Não dizer que Outbox garante entrega exatamente uma vez; explicar duplicatas e idempotência.
- Explicar que a transação atômica é local ao banco do serviço; o envio ocorre depois.
- Não chamar Outbox de Saga: são padrões distintos e podem ser combinados.
- Usar o diagrama para explicar primeiro o problema, depois revelar a solução.
