"""
Exercício Prático – Pré-processamento de Dados com Python
Dataset: Hotel Booking Demand
Disciplina: Mineração de Dados
"""

import pandas as pd
import numpy as np

def main():
    print("=" * 70)
    print("1 & 2. PREPARAÇÃO E CARREGAMENTO DOS DADOS")
    print("=" * 70)
    
    # 2. Carregamento do dataset
    df = pd.read_csv('hotel_bookings.csv')
    print(f"Dimensões originais: {df.shape[0]} instâncias e {df.shape[1]} atributos")
    
    # 3. Tratamento de valores ausentes
    print("\n" + "=" * 70)
    print("3. TRATAMENTO DE VALORES AUSENTES")
    print("=" * 70)
    
    null_counts = df.isnull().sum()
    print("Atributos com valores ausentes no dataset original:")
    print(null_counts[null_counts > 0])
    
    # Atributos escolhidos:
    # 1. country: categórico, 488 ausentes -> Imputação pela Moda ('PRT')
    # 2. children: numérico discreto, 4 ausentes -> Imputação pela Moda/Mediana (0)
    mode_country = df['country'].mode()[0]
    print(f"\nModa calculada para 'country': {mode_country}")
    df['country'] = df['country'].fillna(mode_country)
    df['children'] = df['children'].fillna(0)
    
    print("\nVerificação de valores ausentes após tratamento:")
    print(f"- country: {df['country'].isnull().sum()} nulos")
    print(f"- children: {df['children'].isnull().sum()} nulos")
    
    # 4. Remoção de registros duplicados
    print("\n" + "=" * 70)
    print("4. REMOÇÃO DE REGISTROS DUPLICADOS")
    print("=" * 70)
    
    dups_count = df.duplicated().sum()
    print(f"Quantidade de registros duplicados encontrados: {dups_count}")
    
    df = df.drop_duplicates()
    print(f"Dimensões após remover duplicatas: {df.shape[0]} instâncias e {df.shape[1]} atributos")
    
    # 5. Criação e transformação de atributos
    print("\n" + "=" * 70)
    print("5. CRIAÇÃO E TRANSFORMAÇÃO DE ATRIBUTOS")
    print("=" * 70)
    
    # Criação de total_nights
    df['total_nights'] = (
        df['stays_in_weekend_nights'] +
        df['stays_in_week_nights']
    )
    
    # Discretização de total_nights em stay_length
    # Curta: até 2 noites (<= 2)
    # Média: 3 a 5 noites (3 a 5)
    # Longa: mais de 5 noites (> 5)
    df['stay_length'] = pd.cut(
        df['total_nights'],
        bins=[-1, 2, 5, np.inf],
        labels=['Curta', 'Média', 'Longa']
    )
    
    print("\nContagem e proporção de reservas por stay_length:")
    counts = df['stay_length'].value_counts()
    props = (df['stay_length'].value_counts(normalize=True) * 100).round(2)
    stay_summary = pd.DataFrame({'Quantidade': counts, 'Percentual (%)': props})
    print(stay_summary)
    
    print("\nComparação de ADR por categoria de stay_length:")
    adr_comparison = df.groupby('stay_length', observed=False)['adr'].agg(
        Contagem='count',
        Media='mean',
        Mediana='median',
        Desvio_Padrao='std',
        Minimo='min',
        Maximo='max'
    ).round(2)
    print(adr_comparison)
    
    # 6. Normalização (Min-Max Scaling)
    print("\n" + "=" * 70)
    print("6. NORMALIZAÇÃO (MIN-MAX SCALING)")
    print("=" * 70)
    
    adr_min = df['adr'].min()
    adr_max = df['adr'].max()
    df['adr_normalized'] = (df['adr'] - adr_min) / (adr_max - adr_min)
    
    print(f"ADR Mínimo original: {adr_min}")
    print(f"ADR Máximo original: {adr_max}")
    print(f"Menor valor de adr_normalized: {df['adr_normalized'].min():.6f}")
    print(f"Maior valor de adr_normalized: {df['adr_normalized'].max():.6f}")
    print(f"Média de adr_normalized: {df['adr_normalized'].mean():.6f}")
    
    # 7. Comparação final
    print("\n" + "=" * 70)
    print("7. COMPARAÇÃO FINAL (ANTES vs DEPOIS)")
    print("=" * 70)
    
    comp_df = pd.DataFrame({
        "Característica": [
            "Número de instâncias",
            "Número de atributos",
            "Valores ausentes nos atributos escolhidos (country + children)",
            "Registros duplicados"
        ],
        "Antes": [119390, 32, 492, 32013],
        "Depois": [
            df.shape[0],
            df.shape[1],
            int(df['country'].isnull().sum() + df['children'].isnull().sum()),
            int(df.duplicated().sum())
        ]
    })
    print(comp_df.to_string(index=False))
    print("\n" + "=" * 70)
    print("Pré-processamento concluído com sucesso!")
    print("=" * 70)

if __name__ == '__main__':
    main()
