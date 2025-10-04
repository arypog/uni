import pandas as pd
from mlxtend.frequent_patterns import apriori, association_rules

# Tabela da questão (10 transações)
data = [
    ["Não", "Sim", "Não", "Sim", "Sim", "Não", "Não"],
    ["Sim", "Não", "Sim", "Sim", "Sim", "Não", "Não"],
    ["Não", "Sim", "Não", "Sim", "Sim", "Não", "Não"],
    ["Sim", "Sim", "Não", "Sim", "Sim", "Não", "Não"],
    ["Não", "Não", "Sim", "Não", "Não", "Não", "Não"],
    ["Não", "Não", "Não", "Sim", "Não", "Não", "Não"],
    ["Não", "Não", "Não", "Sim", "Não", "Não", "Não"],
    ["Não", "Não", "Não", "Sim", "Não", "Não", "Sim"],
    ["Não", "Não", "Não", "Não", "Não", "Sim", "Sim"],
    ["Não", "Não", "Não", "Não", "Não", "Sim", "Não"],
]

cols = ["Leite", "Café", "Cerveja", "Pão", "Manteiga", "Arroz", "Feijão"]
df = pd.DataFrame(data, columns=cols)

# Converter "Sim"/"Não" em True/False
df_bool = df.replace({"Sim": True, "Não": False})

# Apriori com suporte mínimo de 0.3
frequent_itemsets = apriori(df_bool, min_support=0.3, use_colnames=True)
print("Itemsets frequentes:\n", frequent_itemsets)

# Contar por tamanho
print("\nItemSets tamanho 1:", sum(frequent_itemsets["itemsets"].apply(len) == 1))
print("ItemSets tamanho 2:", sum(frequent_itemsets["itemsets"].apply(len) == 2))
print("ItemSets tamanho 3:", sum(frequent_itemsets["itemsets"].apply(len) == 3))

# Regras de associação com confiança ≥ 0.8
rules = association_rules(frequent_itemsets, metric="confidence", min_threshold=0.8)
print(
    "\nRegras de associação (conf ≥ 0.8):\n",
    rules[["antecedents", "consequents", "support", "confidence", "lift"]],
)
print("Número de regras:", len(rules))

Consequente = [] 
suporte = [] 
confianca = [] 
lift = [] 
RegrasFinais = [] 
for resultado in saida: 
    s = resultado[1] 
    result_rules = resultado[2] 
    for result_rule in result_rules: 
        a = list(result_rule[0]) 
        b = list(result_rule[1]) 
        c = result_rule[2] l = result_rule[3] 
        if 'nan' in a or 'nan' in b: 
            continue
        if len(a) == 0 or len(b) == 0: 
            continue
        Antecedente.append(a) 
        Consequente.append(b) 
        suporte.append(s) 
        confianca.append(c) 
        lift.append(l) RegrasFinais = pd.DataFrame(
            {'Antecedente': Antecedente, 
             'Consequente': Consequente, 
             'suporte': suporte, 
             'confianca': confianca, 
             'lift': lift}
        )
