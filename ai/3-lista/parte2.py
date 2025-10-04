import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

sns.set(style="whitegrid")

df = pd.read_csv("datasets/titanic/train.csv")
print(df.columns)

print(df.head())
print(df.info())
print(df.describe())

df["Age"] = df["Age"].fillna(df["Age"].median())
df["Fare"] = df["Fare"].fillna(df["Fare"].median())
df["Sex"] = df["Sex"].map({"male": 0, "female": 1})
df["FamilySize"] = df["SibSp"] + df["Parch"] + 1
df["AgeGroup"] = pd.cut(
    df["Age"],
    bins=[0, 12, 18, 35, 60, 80],
    labels=["Child", "Teen", "Adult", "MiddleAge", "Senior"],
)

df = pd.get_dummies(df, columns=["Embarked"], drop_first=True)

print("\nTaxa de sobrevivência por sexo:")
print(df.groupby("Sex")["Survived"].mean())

print("\nTaxa de sobrevivência por classe:")
print(df.groupby("Pclass")["Survived"].mean())

print("\nTaxa de sobrevivência por faixa etária:")
print(df.groupby("AgeGroup", observed=True)["Survived"].mean())

print("\nTaxa de sobrevivência por tamanho da família:")
print(df.groupby("FamilySize")["Survived"].mean())


# Sobrevivência por sexo
plt.figure(figsize=(6, 4))
sns.barplot(x="Sex", y="Survived", data=df)
plt.title("Taxa de Sobrevivência por Sexo")
plt.ylabel("Taxa de Sobrevivência")
plt.xlabel("Sexo (0=Homem, 1=Mulher)")
plt.show()

# Sobrevivência por classe
plt.figure(figsize=(6, 4))
sns.barplot(x="Pclass", y="Survived", data=df)
plt.title("Taxa de Sobrevivência por Classe")
plt.ylabel("Taxa de Sobrevivência")
plt.xlabel("Classe (1=Primeira, 3=Terceira)")
plt.show()

# Sobrevivência por faixa etária
plt.figure(figsize=(8, 5))
sns.barplot(
    x="AgeGroup",
    y="Survived",
    data=df,
    order=["Child", "Teen", "Adult", "MiddleAge", "Senior"],
)
plt.title("Taxa de Sobrevivência por Faixa Etária")
plt.ylabel("Taxa de Sobrevivência")
plt.xlabel("Faixa Etária")
plt.show()

# Sobrevivência por tamanho da família
plt.figure(figsize=(8, 5))
sns.barplot(x="FamilySize", y="Survived", data=df)
plt.title("Taxa de Sobrevivência por Tamanho da Família")
plt.ylabel("Taxa de Sobrevivência")
plt.xlabel("Tamanho da Família")
plt.show()


def survival_rule(row):
    if row["Sex"] == 1:  # feminino
        return 1
    elif row["Sex"] == 0 and row["Age"] < 14:  # meninos
        return 1
    elif row["Pclass"] == 1:  # 1ª classe
        return 1
    else:
        return 0


df["PredictedSurvival"] = df.apply(survival_rule, axis=1)
print("\nTaxa de acerto da regra simples:")
accuracy = (df["Survived"] == df["PredictedSurvival"]).mean()
print(f"{accuracy*100:.2f}%")
