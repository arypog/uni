import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sklearn.metrics import classification_report, accuracy_score
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.naive_bayes import GaussianNB
from skopt import BayesSearchCV
import numpy as np

# ===== 1) Carregar dados =====
train = pd.read_csv("train.csv")

# Seleção de features simples (pode adicionar mais se desejar)
cols = ["Pclass", "Sex", "Age", "SibSp", "Parch", "Fare", "Embarked"]
df = train[cols + ["Survived"]].copy()

# Tratar valores faltantes
df["Age"].fillna(df["Age"].median(), inplace=True)
df["Fare"].fillna(df["Fare"].median(), inplace=True)
df["Embarked"].fillna(df["Embarked"].mode()[0], inplace=True)

# Encoding de variáveis categóricas
df = pd.get_dummies(df, columns=["Sex", "Embarked"], drop_first=True)

X = df.drop("Survived", axis=1)
y = df["Survived"]

X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.3, random_state=42, stratify=y
)

# ===== 2) Modelos básicos =====
models = {
    "DecisionTree": DecisionTreeClassifier(random_state=42),
    "RandomForest": RandomForestClassifier(random_state=42),
    "NaiveBayes": GaussianNB(),
}

for name, model in models.items():
    model.fit(X_train, y_train)
    y_pred = model.predict(X_test)
    print(f"\n--- {name} ---")
    print("Acurácia:", accuracy_score(y_test, y_pred))
    print(classification_report(y_test, y_pred))

# ===== 3) Otimização Bayesiana para RandomForest =====
param_grid = {
    "n_estimators": (50, 300),
    "max_depth": (2, 20),
    "min_samples_split": (2, 10),
}

bayes_cv = BayesSearchCV(
    estimator=RandomForestClassifier(random_state=42),
    search_spaces=param_grid,
    n_iter=20,
    cv=3,
    n_jobs=-1,
    random_state=42,
)
bayes_cv.fit(X_train, y_train)

print("\nMelhores hiperparâmetros (RandomForest):", bayes_cv.best_params_)
print("Acurácia otimizada:", accuracy_score(y_test, bayes_cv.predict(X_test)))
