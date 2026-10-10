# Respostas — Questionário de Mineração de Dados (Revisão 1)

---

### 1. Qual das alternativas abaixo descreve corretamente a hierarquia de tipos de atributos do mais simples ao mais complexo em termos de operações permitidas?
**Resposta: B) Nominal → Ordinal → Intervalar → Razão.**  
*Justificativa:* Segue a escala clássica de medição (Stevens): nominal permite apenas igualdade/diferença; ordinal introduz ordem; intervalar permite medir distâncias (mas com zero arbitrário); e razão tem zero absoluto, permitindo operações como dobro ou metade.

---

### 2. Um atributo como 'Grau de Instrução' (Fundamental, Médio, Superior) é classificado como qual tipo e por quê?
**Resposta: C) Ordinal, pois os valores possuem uma ordem significativa entre si, mas a distância exata entre os níveis não é quantificada.**  
*Justificativa:* Há uma relação clara de hierarquia/avanço entre os níveis, mas não dá para quantificar numericamente a distância matemática exata entre ter ensino médio e superior.

---

### 3. Qual a principal diferença entre atributos de escala intervalar e escala de razão?
**Resposta: B) Atributos de razão possuem um ponto zero inerente e significativo, permitindo dizer que um valor é o dobro de outro.**  
*Justificativa:* A escala intervalar tem um zero arbitrário (ex.: 0°C não significa ausência de temperatura), enquanto a de razão tem zero real/absoluto (ex.: R$ 0,00 ou 0 kg), viabilizando multiplicações e proporções.

---

### 4. Como os atributos ordinais são tratados para que possam ser utilizados em algoritmos baseados em distância numérica?
**Resposta: A) Eles são mapeados para seus postos (ranks) e então normalizados para o intervalo [0, 1].**  
*Justificativa:* Substitui-se cada categoria pelo seu índice de ordem ($1, 2, \dots, M$) e aplica-se a fórmula $z = \frac{rank - 1}{M - 1}$, deixando os valores na mesma escala das variáveis numéricas.

---

### 5. Como se define a dissimilaridade para um atributo binário simétrico entre dois objetos?
**Resposta: A) É a razão entre o número de atributos onde os valores são diferentes e o número total de atributos binários.**  
*Justificativa:* Por ser simétrico, tanto a concordância em 0 quanto em 1 têm o mesmo valor. A dissimilaridade é simplesmente a proporção de discordâncias: $d = \frac{discordâncias}{total}$.

---

### 6. Em mineração de dados, o que define um atributo binário simétrico?
**Resposta: C) Ambos os seus estados (0 e 1) são igualmente importantes e carregam o mesmo peso informativo.**  
*Justificativa:* Nenhum estado é considerado "evento raro" ou mais valioso que o outro (como sexo masculino/feminino).

---

### 7. Qual é a distinção crucial entre atributos binários simétricos e assimétricos no cálculo de proximidade?
**Resposta: C) Em atributos assimétricos, a coincidência do valor '0' (ausência) é considerada irrelevante e não entra no cálculo de similaridade.**  
*Justificativa:* Em dados assimétricos (ex.: presença de doenças ou palavras em texto), dois objetos não possuírem um atributo comum não significa que eles sejam semelhantes.

---

### 11. Em um conjunto de dados de salários: 30, 36, 47, 50, 52, 52, 56, 60, 63, 70, 70, 110 (em milhares), qual é o valor da mediana e por que ela é útil?
**Resposta: B) 54 e é útil por ser menos sensível a valores extremos (como 110) do que a média.**  
*Justificativa:* Com 12 valores ordenados, a mediana é a média dos elementos centrais (6º e 7º): $\frac{52 + 56}{2} = 54$. Ela divide a base ao meio sem ser distorcida pelo outlier de 110.

---

### 12. Se uma distribuição de dados é 'positivamente inclinada' (positively skewed), qual é a relação típica entre média, mediana e moda?
**Resposta: D) Moda < Mediana < Média**  
*Justificativa:* A cauda longa à direita puxa a média para cima (direção dos valores altos), enquanto a moda permanece no pico da distribuição e a mediana fica no centro.

---

### 13. Qual a diferença fundamental entre variância e desvio padrão como medidas de dispersão?
**Resposta: D) O desvio padrão está na mesma unidade que os dados originais, facilitando a interpretação, enquanto a variância está em unidades quadráticas.**  
*Justificativa:* Como a variância calcula a média dos desvios ao quadrado, sua unidade fica ao quadrado (ex.: $\text{salário}^2$). O desvio padrão aplica a raiz quadrada e volta à unidade original.

---

### 14. O que o 'intervalo interquartil' (IQR) mede e como ele ajuda na detecção de outliers?
**Resposta: A) Mede a dispersão dos 50% centrais dos dados; valores muito além de 1,5 × IQR dos quartis são considerados potenciais outliers.**  
*Justificativa:* O IQR ($Q_3 - Q_1$) cobre o miolo da distribuição. A regra padrão do boxplot marca como anomalias os pontos fora do intervalo $[Q_1 - 1{,}5 \cdot IQR,\; Q_3 + 1{,}5 \cdot IQR]$.

---

### 15. No algoritmo k-Nearest-Neighbor (KNN), qual é o efeito comum de selecionar um valor de k muito pequeno em um conjunto de dados com ruído?
**Resposta: B) O modelo torna-se excessivamente sensível ao ruído local, aumentando o risco de overfitting.**  
*Justificativa:* Com $k$ pequeno (como $k=1$), qualquer ponto com ruído ou outlier vizinho define diretamente a predição, criando fronteiras de decisão instáveis e superajustadas.

---

### 16. No processo de integração de dados, qual problema ocorre quando um mesmo conceito possui nomes diferentes em bases distintas?
**Resposta: B) Problema de identificação de entidade.**  
*Justificativa:* Trata-se de conciliar esquemas diferentes que representam a mesma entidade real (ex.: `cod_cliente` em uma base e `id_usuario` em outra).

---

### 17. Qual técnica de limpeza de dados é considerada a mais robusta para lidar com valores ausentes ao aproveitar as correlações entre atributos?
**Resposta: B) Utilizar o valor mais provável determinado por ferramentas de inferência como indução de árvores de decisão ou formalismo Bayesiano.**  
*Justificativa:* Em vez de imputar um valor estático (como a média), modelos preditivos estimam o dado faltante considerando todas as outras variáveis daquele registro.

---

### 18. Ao lidar com dados 'sujos' onde muitos registros de renda estão ausentes, qual estratégia de limpeza é preferível se a distribuição da renda por classe for assimétrica (skewed)?
**Resposta: B) Preencher com a mediana dos valores daquela classe.**  
*Justificativa:* A mediana não é distorcida pela assimetria/outliers da renda e imputar por classe preserva o padrão de cada grupo alvo.

---

### 19. Como a técnica de 'Binning' auxilia no tratamento de dados ruidosos em atributos numéricos?
**Resposta: C) Ao agrupar valores em 'baldes' e substituí-los pela média ou mediana do balde, suavizando flutuações irrelevantes.**  
*Justificativa:* Ao particionar os dados ordenados em intervalos e suavizar seus valores, o binning atenua variações pontuais e ruídos locais.

---

### 20. Como a técnica de 'smoothing by bin means' atua na redução de ruído nos dados?
**Resposta: B) Ela divide os dados ordenados em 'caixas' (bins) e substitui cada valor pela média dos valores contidos naquela caixa.**  
*Justificativa:* É a aplicação direta da média do intervalo: cada dado do bin assume o valor médio dos elementos que caíram ali.

---

### 21. Ao comparar a eficácia de algoritmos com dados ruidosos, por que o Naive Bayes é frequentemente considerado mais robusto ao ruído do que o KNN?
**Resposta: A) O Naive Bayes usa probabilidades agregadas de todo o conjunto de dados, enquanto o KNN é afetado pelo ruído de vizinhos locais específicos.**  
*Justificativa:* O Naive Bayes calcula probabilidades globais, diluindo pontos ruidosos isolados, enquanto o KNN depende diretamente de poucos vizinhos na vizinhança imediata.

---

### 22. Em relação à eficácia com dados ruidosos, por que modelos de Árvores de Decisão podem sofrer mais do que SVMs se não forem devidamente ajustados?
**Resposta: B) As árvores tendem a criar sub-divisões muito específicas para cada outlier (overfitting), enquanto o SVM foca na margem de separação global.**  
*Justificativa:* Árvores não podadas criam ramificações profundas só para isolar amostras atípicas, enquanto o SVM busca uma margem de separação maximizada no espaço de características.

---

### 23. Cenário: Você precisa classificar documentos de texto em categorias temáticas. Os dados são vetores de frequência de termos esparsos e de alta dimensão. Qual métrica de similaridade e algoritmo seriam ideais?
**Resposta: B) Similaridade de Cosseno com Naive Bayes Multinomial.**  
*Justificativa:* A similaridade de cosseno compara a direção dos vetores de termos independentemente do tamanho do texto, e o Naive Bayes Multinomial é o modelo padrão para contagem de palavras em dados esparsos.

---

### 24. Dadas as tuplas de atributos numéricos A = (22, 1, 42, 10) e B = (20, 0, 36, 8), qual é a distância Euclidiana entre esses dois objetos?
**Resposta: C) $\sqrt{45} \approx 6,71$**  
*Justificativa:*  
$d = \sqrt{(22-20)^2 + (1-0)^2 + (42-36)^2 + (10-8)^2} = \sqrt{2^2 + 1^2 + 6^2 + 2^2} = \sqrt{4 + 1 + 36 + 4} = \sqrt{45} \approx 6{,}71$.

---

### 25. Qual é a principal aplicação da medida de dissimilaridade de Minkowski no contexto de mineração de dados?
**Resposta: A) Agir como uma generalização que inclui as distâncias Euclidiana e de Manhattan dependendo do parâmetro h.**  
*Justificativa:* A métrica de Minkowski ($L_p$) generaliza distâncias baseadas em normas: com $h=1$ temos Manhattan, com $h=2$ temos Euclidiana e com $h \to \infty$ temos Chebyshev.

---

### 26. A distância de Manhattan é frequentemente preferida à Euclidiana em quais situações específicas?
**Resposta: A) Em cenários onde o movimento é restrito a caminhos em eixos ortogonais, como o traçado de ruas em uma cidade.**  
*Justificativa:* Ela calcula a soma das diferenças absolutas em cada dimensão, correspondendo exatamente a trajetos em grade ("quarteirões").

---

### 27. Para calcular a dissimilaridade entre dois objetos descritos por atributos nominais, qual fórmula é utilizada?
**Resposta: C) $d(i, j) = \frac{p - m}{p}$, onde $m$ é o número de matches e $p$ o total de atributos.**  
*Justificativa:* Mede a fração de atributos com valores discordantes: total de variáveis ($p$) menos as iguais ($m$), dividido por $p$.

---

### 28. O coeficiente de Jaccard é uma medida de similaridade aplicada preferencialmente a qual tipo de dado?
**Resposta: B) Atributos binários assimétricos.**  
*Justificativa:* O coeficiente de Jaccard ($J = \frac{q}{q + r + s}$) foca apenas nas presenças compartilhadas ($1-1$), ignorando a ausência mútua ($0-0$).

---

### 29. Ao lidar com atributos de tipos mistos em um banco de dados, qual é o procedimento padrão para calcular uma matriz de dissimilaridade única?
**Resposta: C) Processar todos os atributos juntos, normalizando-os para uma escala comum entre 0,0 e 1,0.**  
*Justificativa:* Aplica-se a abordagem padrão (como o coeficiente de Gower), calculando a dissimilaridade normalizada $[0, 1]$ para cada tipo de atributo e fazendo a média ponderada.

---

### 30. O que diferencia fundamentalmente um classificador k-nearest-neighbor (KNN) de métodos como árvores de decisão ou máquinas de vetores de suporte (SVM)?
**Resposta: A) O KNN é um aprendiz preguiçoso (lazy learner) que armazena os dados de treinamento e adia a generalização até encontrar uma tupla de teste.**  
*Justificativa:* Ele não constrói um modelo explícito durante o treinamento; apenas armazena os dados e realiza todo o cálculo no momento em que precisa classificar uma nova consulta.

---

### 31. Por que a normalização de atributos numéricos, como 'renda' e 'idade', é considerada um passo crítico antes de aplicar o classificador KNN?
**Resposta: B) Para evitar que atributos com escalas maiores dominem injustamente o cálculo da distância.**  
*Justificativa:* Como a distância euclidiana soma as diferenças quadradas, uma variável na casa dos milhares (renda) anularia a influência de uma variável com valores baixos (idade).

---

### 32. Qual é a principal desvantagem computacional do classificador KNN em comparação com algoritmos de 'aprendizado ávido' (eager learners)?
**Resposta: D) O custo de classificação é alto, pois exige a busca em toda a base de treinamento para cada novo teste.**  
*Justificativa:* Para cada nova instância, o KNN precisa calcular a distância para todos os pontos da base de treino, tornando a fase de inferência cara em grandes volumes de dados.

---

### 33. Na construção de uma Árvore de Decisão, o que o conceito de 'Entropia' busca medir ao selecionar um atributo para divisão?
**Resposta: A) A impureza ou desordem dos dados em uma partição.**  
*Justificativa:* A entropia quantifica o grau de incerteza/mistura de classes em um nó. O algoritmo busca divisões que maximizem o ganho de informação (redução da entropia).

---

### 34. Qual é o objetivo principal da técnica de 'poda posterior' (postpruning) em algoritmos de árvore de decisão como o CART?
**Resposta: A) Remover ramos que capturaram ruído ou anomalias nos dados de treinamento para melhorar a generalização.**  
*Justificativa:* A árvore cresce por completo até memorizar os dados e, em seguida, os nós que não agregam capacidade preditiva geral em dados de validação são podados para evitar overfitting.

---

### 35. Como o método RainForest aborda o desafio da escalabilidade na indução de árvores de decisão em grandes bases de dados?
**Resposta: C) Mantendo estruturas AVC-set que resumem a contagem de classes por valor de atributo, evitando carregar todos os dados na memória.**  
*Justificativa:* O RainForest cria tabelas agregadas chamadas AVC (*Attribute-Value, Class count*), suficientes para calcular critérios de divisão sem precisar reprocessar os dados brutos a cada nó.

---

### 36. Qual é a principal distinção no funcionamento dos algoritmos de ensemble Bagging e Boosting?
**Resposta: B) O Bagging treina modelos em paralelo de forma independente, enquanto o Boosting treina modelos sequencialmente, onde cada novo modelo tenta corrigir os erros do anterior.**  
*Justificativa:* Bagging foca em reduzir variância combinando modelos independentes treinados com bootstrap. Boosting foca em reduzir viés com aprendizado iterativo que dá peso aos erros passados.

---

### 37. Como o algoritmo Random Forest expande o conceito de Bagging para melhorar a diversidade entre as árvores?
**Resposta: B) Ele introduz a seleção aleatória de um subconjunto de atributos em cada nó para determinar a melhor divisão.**  
*Justificativa:* Além de amostrar linhas (bootstrap), sorteia um subconjunto de colunas/atributos em cada divisão, descorrelacionando as árvores entre si.

---

### 38. Cenário: Um banco quer prever a inadimplência de clientes. Os dados históricos têm pouquíssimos exemplos de inadimplentes (classe minoritária). Qual estratégia de ensemble é mais agressiva para melhorar a detecção dessa classe?
**Resposta: B) Boosting (como o AdaBoost).**  
*Justificativa:* O Boosting repondera as instâncias a cada rodada, focando intensamente nos erros das iterações anteriores, o que força o modelo a aprender a classe minoritária.

---

### 39. Qual é a suposição simplificadora do classificador Naive Bayes e como ela afeta sua aplicação?
**Resposta: D) Assume a independência condicional de classe, o que simplifica o cálculo de probabilidades mas pode ignorar correlações importantes.**  
*Justificativa:* Assume que, dada a classe, a presença de um atributo é independente de qualquer outro, simplificando a conta para uma multiplicação direta de probabilidades.

---

### 40. Se um atributo categórico no Naive Bayes apresenta uma probabilidade de zero para uma determinada classe no conjunto de treinamento, qual é a consequência no cálculo da probabilidade posterior?
**Resposta: D) A probabilidade posterior total daquela classe será anulada (tornando-se zero), independentemente dos outros atributos.**  
*Justificativa:* Como a probabilidade final é obtida pelo produtório das probabilidades condicionais, um único fator zero zera todo o produto (motivo pelo qual se usa suavização de Laplace).

---

### 41. Qual das seguintes situações representa um cenário ideal para o uso do classificador Naive Bayes?
**Resposta: C) Classificação de textos em larga escala, onde o volume de dados é imenso e os atributos (palavras) são tratados como independentes.**  
*Justificativa:* Em tarefas de NLP (como filtragem de spam e análise de tópicos), ele é rápido de treinar, escalável e performa surpreendentemente bem mesmo com a suposição de independência.

---

### 42. O que define o 'Hiperplano de Margem Máxima' (MMH) em um modelo de Support Vector Machines (SVM)?
**Resposta: A) É o hiperplano que separa as duas classes garantindo a maior distância possível até os pontos de treinamento mais próximos.**  
*Justificativa:* O objetivo geométrico do SVM é maximizar a margem (a distância mínima entre a fronteira de decisão e os vetores de suporte de ambos os lados).

---

### 43. Em uma Máquina de Vetores de Suporte (SVM), qual é a função dos 'vetores de suporte'?
**Resposta: D) Eles são as tuplas de treinamento mais próximas do hiperplano que definem sua posição e margem.**  
*Justificativa:* Apenas esses pontos críticos determinam a orientação e a posição do hiperplano ideal; remover qualquer outro ponto fora da margem não altera a solução.

---

### 44. Como o 'truque do kernel' (kernel trick) permite que o SVM lide com dados que não são linearmente separáveis?
**Resposta: D) Ele calcula produtos escalares em um espaço de maior dimensão sem precisar transformar explicitamente os dados.**  
*Justificativa:* Em vez de mapear explicitamente os dados para um espaço de alta dimensão (o que seria inviável computacionalmente), ele avalia diretamente o produto interno entre os pontos no espaço original via função de kernel.

---

### 45. Cenário: Você possui um conjunto de dados médico com 50 atributos complexos e correlações desconhecidas. A precisão é vital, mas os médicos precisam entender o porquê de cada diagnóstico. Qual algoritmo é mais adequado?
**Resposta: C) Árvore de Decisão.**  
*Justificativa:* Árvores geram regras if-then transparentes e visualmente interpretáveis, permitindo aos médicos auditar os critérios que levaram ao diagnóstico.

---

### 46. Cenário: Uma empresa de e-commerce precisa classificar milhões de transações em tempo real para detectar fraudes. O sistema recebe um fluxo constante de novos dados. Qual abordagem minimiza o custo de treinamento contínuo?
**Resposta: D) Aprendizagem preguiçosa (como o KNN).**  
*Justificativa:* Modelos lazy learners não exigem retreinamento ao receber novos dados; basta inserir o novo registro na base de armazenamento.

---

### 47. Cenário: Você está trabalhando com um conjunto de dados de bioinformática onde o número de atributos (genes) é muito maior que o número de amostras (pacientes) e os dados não são linearmente separáveis. Qual técnica é recomendada?
**Resposta: C) SVM com truque de Kernel (Kernel trick).**  
*Justificativa:* O SVM é teoricamente robusto para problemas com alta dimensionalidade ($p \gg n$) e o truque do kernel lida com a não-linearidade sem explodir o custo computacional.
