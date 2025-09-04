import pandas as pd
from sklearn.tree import DecisionTreeClassifier, plot_tree
import matplotlib.pyplot as plt


df = pd.read_csv("datasets/restaurante.csv", sep=";")

mapa_cliente = {"Nenhum": 0, "Algum": 1, "Cheio": 2}
df["Cliente"] = df["Cliente"].map(mapa_cliente)

X = df.drop("Conclusao", axis=1)
y = df["Conclusao"]

X = pd.get_dummies(X)

modelo = DecisionTreeClassifier(criterion="entropy", random_state=1)
modelo.fit(X, y)

plt.figure(figsize=(16, 8))
plot_tree(
    modelo,
    feature_names=X.columns,
    class_names=modelo.classes_,
    filled=True,
    rounded=True,
)
plt.show()
