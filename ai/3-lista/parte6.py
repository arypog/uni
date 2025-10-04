import numpy as np
import pandas as pd

# Matriz de confusão
conf_matrix = np.array([[10, 4, 2, 1], [1, 15, 2, 0], [2, 3, 20, 5], [4, 1, 2, 50]])

classes = ["A", "B", "C", "D"]

results = []

for i, cls in enumerate(classes):
    TP = conf_matrix[i, i]
    FN = conf_matrix[i, :].sum() - TP
    FP = conf_matrix[:, i].sum() - TP
    TN = conf_matrix.sum() - (TP + FP + FN)

    precision = TP / (TP + FP) if (TP + FP) != 0 else 0
    recall = TP / (TP + FN) if (TP + FN) != 0 else 0
    f1_score = (
        2 * (precision * recall) / (precision + recall)
        if (precision + recall) != 0
        else 0
    )

    results.append(
        {
            "Classe": cls,
            "Precisao": round(precision, 3),
            "Recall": round(recall, 3),
            "F1Score": round(f1_score, 3),
            "TVP": TP,
            "TFN": FN,
            "TFP": FP,
            "TVN": TN,
        }
    )

df = pd.DataFrame(results)
print(df)
