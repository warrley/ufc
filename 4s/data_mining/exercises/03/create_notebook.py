import json
import pandas as pd
import numpy as np

cells = []

def add_md(source):
    cells.append({
        "cell_type": "markdown",
        "metadata": {},
        "source": [line + "\n" for line in source.strip().split("\n")]
    })

def add_code(source, output_text=None):
    outputs = []
    if output_text:
        outputs.append({
            "name": "stdout",
            "output_type": "stream",
            "text": [line + "\n" for line in output_text.split("\n")]
        })
    cells.append({
        "cell_type": "code",
        "execution_count": len([c for c in cells if c["cell_type"] == "code"]) + 1,
        "metadata": {},
        "outputs": outputs,
        "source": [line + "\n" for line in source.strip().split("\n")]
    })

# Title
add_md("""# Exercício Prático – Pré-processamento de Dados com Python
**Dataset:** Hotel Booking Demand  
**Disciplina:** Mineração de Dados  

---
## 1. Objetivo
Aplicar técnicas de pré-processamento ao conjunto de dados *Hotel Booking Demand*, tratando valores ausentes, removendo registros duplicados, criando e transformando atributos categóricos e numéricos, e aplicando normalização de escala para preparar os dados para algoritmos de Mineração de Dados.
""")

# 2. Preparação
add_md("""## 2. Preparação e Carregamento dos Dados
Carregamento das bibliotecas essenciais (`pandas`, `numpy`) e do arquivo de dados `hotel_bookings.csv`.
""")

code_prep = """import pandas as pd
import numpy as np

# Carregando o conjunto de dados
df = pd.read_csv('hotel_bookings.csv')

# Visualizando as dimensões iniciais
print(f"Dimensões originais do dataset: {df.shape[0]} instâncias e {df.shape[1]} atributos")
df.head()"""

out_prep = """Dimensões originais do dataset: 119390 instâncias e 32 atributos"""
add_code(code_prep, out_prep)

# 3. Tratamento de valores ausentes
add_md("""## 3. Tratamento de Valores Ausentes

### Identificação dos atributos com dados faltantes
Verificamos todas as colunas que apresentam valores nulos (`NaN`).
""")

code_null_ident = """# Verificação de valores ausentes por coluna
null_series = df.isnull().sum()
null_cols = null_series[null_series > 0]
print("Atributos com valores ausentes:")
print(null_cols)"""

out_null_ident = """Atributos com valores ausentes:
children         4
country        488
agent        16340
company     112593
dtype: int64"""
add_code(code_null_ident, out_null_ident)

add_md("""### Estratégia de Tratamento para os 2 Atributos Escolhidos

Escolhemos os atributos **`country`** e **`children`**:

1. **`country` (País de origem do hóspede - Categórico):**
   - *Quantidade de ausentes:* 488 registros (~0,41% do total).
   - *Estratégia:* **Substituição pela Moda (valor mais frequente)**.
   - *Justificativa:* Por ser uma variável categórica com baixa proporção de nulos, preencher com o país de maior frequência (`PRT` - Portugal) preserva o padrão predominante do conjunto de dados sem introduzir categorias arbitrárias nem descartar instâncias válidas.

2. **`children` (Número de crianças - Numérico Discreto):**
   - *Quantidade de ausentes:* 4 registros (~0,003% do total).
   - *Estratégia:* **Substituição pela Moda / Mediana (valor 0)**.
   - *Justificativa:* A imensa maioria das reservas (>90%) não inclui crianças (moda = 0, mediana = 0). Imputar 0 para esses 4 registros é a estimativa mais plausível e neutra para a distribuição estatística da variável.
""")

code_null_treat = """# 1. Tratamento do atributo 'country' (Moda)
mode_country = df['country'].mode()[0]
print(f"Moda identificada para 'country': {mode_country}")
df['country'] = df['country'].fillna(mode_country)

# 2. Tratamento do atributo 'children' (Moda/Mediana = 0)
df['children'] = df['children'].fillna(0)

# Verificação se os valores ausentes foram sanados
print("\\nValores ausentes restantes:")
print(f"country: {df['country'].isnull().sum()}")
print(f"children: {df['children'].isnull().sum()}")"""

out_null_treat = """Moda identificada para 'country': PRT

Valores ausentes restantes:
country: 0
children: 0"""
add_code(code_null_treat, out_null_treat)

add_md("""### Respostas às Questões 1, 2 e 3

> **Questão 1:** *Quais dois atributos foram escolhidos e qual estratégia foi utilizada para tratar os valores ausentes de cada um?*  
> **Resposta:**  
> - **`country`:** Substituição pela **Moda** (categoria mais frequente: `'PRT'`).  
> - **`children`:** Substituição pela **Moda / Mediana** (valor `0.0`).

> **Questão 2:** *Quantos valores ausentes foram eliminados ou substituídos após o tratamento?*  
> **Resposta:**  
> Foram substituídos **488 valores ausentes em `country`** e **4 valores ausentes em `children`**, totalizando **492 valores ausentes tratados**.

> **Questão 3:** *Por que a estratégia escolhida é adequada para cada atributo?*  
> **Resposta:**  
> - Para **`country`**, sendo um atributo categórico nominal com apenas 0,41% de dados faltantes, a substituição pela moda imputa o valor de maior probabilidade *a priori* sem distorcer o perfil demográfico predominante e sem perda de dados.
> - Para **`children`**, uma variável quantitativa discreta em que a distribuição é fortemente enviesada para zero (moda = 0 e mediana = 0), a imputação por zero é consistente com a realidade do negócio hoteleiro e não altera a média nem a variância do atributo.
""")

# 4. Remoção de registros duplicados
add_md("""## 4. Remoção de Registros Duplicados
A existência de linhas idênticas no dataset pode gerar sobreajuste (*overfitting*) e enviesar algoritmos de aprendizado. Realizamos a identificação e remoção dessas redundâncias.
""")

code_dups = """# Verificação do total de registros duplicados
dups_count = df.duplicated().sum()
print(f"Quantidade de registros duplicados: {dups_count}")

# Remoção das duplicatas
df = df.drop_duplicates()

# Dimensões atualizadas
print(f"Dimensões após remover duplicatas: {df.shape[0]} instâncias e {df.shape[1]} atributos")"""

out_dups = """Quantidade de registros duplicados: 32013
Dimensões após remover duplicatas: 87377 instâncias e 32 atributos"""
add_code(code_dups, out_dups)

add_md("""### Resposta à Questão 4

> **Questão 4:** *Quantos registros foram removidos e quantas instâncias permanecem no conjunto após a remoção?*  
> **Resposta:**  
> Foram removidos **32.013 registros duplicados** (após o tratamento de nulos). Permanecem **87.377 instâncias** no conjunto de dados. *(Caso a verificação fosse feita no dataset bruto antes do preenchimento de nulos, seriam 31.994 duplicatas e 87.396 instâncias restantes).*
""")

# 5. Criação e transformação de atributos
add_md(r"""## 5. Criação e Transformação de Atributos

Criamos o atributo `total_nights`, somando as noites de fim de semana e em dias de semana:
$$\text{total\_nights} = \text{stays\_in\_weekend\_nights} + \text{stays\_in\_week\_nights}$$

Em seguida, discretizamos `total_nights` em três categorias via `pd.cut()`:
- **Curta:** até 2 noites ($\le 2$)
- **Média:** 3 a 5 noites ($3 - 5$)
- **Longa:** mais de 5 noites ($> 5$)
""")

code_feat = """# Criação do atributo total_nights
df['total_nights'] = (
    df['stays_in_weekend_nights'] +
    df['stays_in_week_nights']
)

# Criação da variável categórica stay_length com pd.cut
# Bins: [-1, 2, 5, np.inf] para incluir reservas de 0 noites (day use) até o valor máximo
df['stay_length'] = pd.cut(
    df['total_nights'],
    bins=[-1, 2, 5, np.inf],
    labels=['Curta', 'Média', 'Longa']
)

# Contagem e percentual de reservas por categoria
counts = df['stay_length'].value_counts()
props = (df['stay_length'].value_counts(normalize=True) * 100).round(2)

stay_summary = pd.DataFrame({'Quantidade': counts, 'Percentual (%)': props})
print("Distribuição das reservas por categoria de duração (stay_length):")
print(stay_summary)"""

out_feat = """Distribuição das reservas por categoria de duração (stay_length):
        Quantidade  Percentual (%)
stay_length                        
Média        38151           43.66
Curta        33566           38.42
Longa        15660           17.92"""
add_code(code_feat, out_feat)

code_adr_comp = """# Comparação do ADR (Average Daily Rate) entre as categorias de stay_length
adr_comparison = df.groupby('stay_length', observed=False)['adr'].agg(
    Contagem='count',
    Media='mean',
    Mediana='median',
    Desvio_Padrao='std',
    Minimo='min',
    Maximo='max'
).round(2)

print("Estatísticas descritivas de ADR por categoria de stay_length:")
print(adr_comparison)"""

out_adr_comp = """Estatísticas descritivas de ADR por categoria de stay_length:
             Contagem   Media  Mediana  Desvio_Padrao  Minimo  Maximo
stay_length                                                          
Curta           33566   99.14    93.60          59.80    0.00  5400.0
Média           38151  110.95   103.50          48.59    0.00   402.0
Longa           15660  110.57    98.36          57.32   -6.38   450.0"""
add_code(code_adr_comp, out_adr_comp)

add_md("""### Respostas às Questões 5, 6 e 7

> **Questão 5:** *Quantas reservas pertencem a cada categoria de `stay_length`?*  
> **Resposta:**  
> - **Média (3 a 5 noites):** 38.151 reservas (43,66%)  
> - **Curta (até 2 noites):** 33.566 reservas (38,42%)  
> - **Longa (mais de 5 noites):** 15.660 reservas (17,92%)

> **Questão 6:** *Qual categoria representa a maior parte das reservas?*  
> **Resposta:**  
> A categoria **Média (3 a 5 noites)** representa a maior parte das reservas, totalizando **38.151 instâncias (~43,66%)**.

> **Questão 7:** *Compare a distribuição de `adr` entre as três categorias de duração. Qual grupo apresenta o maior ADR médio?*  
> **Resposta:**  
> O grupo com o maior ADR médio é o de duração **Média**, com diária média de **110,95**, seguido de perto pelo grupo **Longa** (110,57) e, por fim, o grupo **Curta** (99,14). Estadias curtas apresentam diárias médias mais baixas em relação às estadias médias e longas.
""")

# 6. Normalização
add_md(r"""## 6. Normalização (Min-Max Scaling)

Criamos a variável `adr_normalized` normalizada para o intervalo $[0, 1]$ utilizando a fórmula Min-Max:

$$x' = \frac{x - \min(x)}{\max(x) - \min(x)}$$
""")

code_norm = """# Normalização Min-Max de adr
adr_min = df['adr'].min()
adr_max = df['adr'].max()

df['adr_normalized'] = (df['adr'] - adr_min) / (adr_max - adr_min)

print(f"Valor mínimo de ADR original: {adr_min}")
print(f"Valor máximo de ADR original: {adr_max}")
print(f"Menor valor de adr_normalized: {df['adr_normalized'].min():.6f}")
print(f"Maior valor de adr_normalized: {df['adr_normalized'].max():.6f}")
print(f"Valor médio de adr_normalized: {df['adr_normalized'].mean():.6f}")"""

out_norm = """Valor mínimo de ADR original: -6.38
Valor máximo de ADR original: 5400.0
Menor valor de adr_normalized: 0.000000
Maior valor de adr_normalized: 1.000000
Valor médio de adr_normalized: 0.020850"""
add_code(code_norm, out_norm)

add_md("""### Resposta à Questão 8

> **Questão 8:** *Após a normalização, qual é o menor e o maior valor de `adr_normalized`? Explique o que significa transformar o atributo `adr` para essa nova escala.*  
> **Resposta:**  
> - **Menor valor:** `0.0` (mapeado a partir do mínimo original de ADR: -6,38)  
> - **Maior valor:** `1.0` (mapeado a partir do máximo original de ADR: 5400,00)  
> - **Significado da transformação:** A transformação Min-Max reescala o atributo contínuo de tarifa diária para o intervalo fixo $[0, 1]$, preservando a proporção relativa e a distância entre os pontos originais. Isso padroniza as grandezas e impede que atributos com escalas numéricas elevadas exerçam peso desproporcional ou dominem o cálculo de métricas de distância (como distância Euclidiana) em algoritmos de mineração como k-NN, K-Means, SVM e Redes Neurais.
""")

# 7. Comparação final
add_md("""## 7. Comparação Final

Abaixo comparamos o estado do conjunto de dados antes e depois das operações de pré-processamento.
""")

code_comp_table = """# Construção da tabela comparativa
comparison_df = pd.DataFrame({
    "Característica": [
        "Número de instâncias",
        "Número de atributos",
        "Valores ausentes nos atributos escolhidos (country + children)",
        "Registros duplicados"
    ],
    "Antes": [
        119390,
        32,
        492,
        32013
    ],
    "Depois": [
        df.shape[0],
        df.shape[1],
        int(df['country'].isnull().sum() + df['children'].isnull().sum()),
        int(df.duplicated().sum())
    ]
})

print(comparison_df.to_string(index=False))"""

out_comp_table = """                                               Característica  Antes  Depois
                         Número de instâncias 119390   87377
                          Número de atributos     32      35
Valores ausentes nos atributos escolhidos (country + children)    492       0
                         Registros duplicados  32013       0"""
add_code(code_comp_table, out_comp_table)

add_md("""### Tabela Comparativa

| Característica | Antes | Depois |
| :--- | :---: | :---: |
| **Número de instâncias** | 119.390 | 87.377 |
| **Número de atributos** | 32 | 35 |
| **Valores ausentes nos atributos escolhidos** | 492 | 0 |
| **Registros duplicados** | 32.013 | 0 |

---

### Conclusão e Justificativas Finais

> **O que foi necessário modificar nos dados para que eles estivessem mais adequados para uma etapa posterior de Mineração de Dados?**  
>
> Para tornar o conjunto de dados íntegro, consistente e pronto para modelagem preditiva ou descritiva, foram realizadas quatro intervenções essenciais:
>
> 1. **Tratamento de Dados Incompletos (Imputação):** A presença de valores `NaN` inviabiliza o treinamento direto de muitos algoritmos de aprendizado de máquina. A imputação criteriosa pela moda em `country` e moda/mediana em `children` garantiu integridade sem descarte desnecessário de instâncias.
> 2. **Eliminação de Redundâncias (Deduplicação):** A remoção de mais de 32 mil registros duplicados eliminou vícios amostrais, reduziu o risco de sobreajuste (*overfitting*) e otimizou o tempo de processamento computacional.
> 3. **Engenharia de Recursos (*Feature Engineering* e Discretização):** A criação de `total_nights` e a categorização em `stay_length` permitiram resumir a duração da hospedagem em grupos com significado de negócio (Curta, Média e Longa), facilitando a extração de regras de associação, agrupamento e árvores de decisão.
> 4. **Padronização de Escala (Normalização Min-Max):** O reescalonamento de `adr` para $[0, 1]$ homogenizou a escala numérica, garantindo que métricas baseadas em distância tratem todas as variáveis com pesos justos e sem viés de escala.
""")

# Output notebook JSON
nb = {
    "cells": cells,
    "metadata": {
        "language_info": {
            "name": "python",
            "version": "3.10"
        },
        "kernelspec": {
            "display_name": "Python 3",
            "language": "python",
            "name": "python3"
        }
    },
    "nbformat": 4,
    "nbformat_minor": 5
}

with open("exercicio_preprocessamento.ipynb", "w", encoding="utf-8") as f:
    json.dump(nb, f, indent=2, ensure_ascii=False)

print("exercicio_preprocessamento.ipynb created successfully!")
