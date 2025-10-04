import math
from graphviz import Digraph


class Node:
    def __init__(self):
        self.value = None
        self.next = None
        self.childs = None


class DecisionTreeClassifier:
    def __init__(self, X, feature_names, labels):
        self.X = X
        self.feature_names = feature_names
        self.labels = labels
        self.labelCategories = list(set(labels))
        self.node = None
        self.entropy = self._get_entropy([x for x in range(len(self.labels))])

    def _get_entropy(self, x_ids):
        labels = [self.labels[i] for i in x_ids]
        label_count = [labels.count(x) for x in self.labelCategories]
        entropy = sum(
            [
                -count / len(x_ids) * math.log(count / len(x_ids), 2) if count else 0
                for count in label_count
            ]
        )
        return entropy

    def _get_information_gain(self, x_ids, feature_id):
        info_gain = self._get_entropy(x_ids)
        x_features = [self.X[x][feature_id] for x in x_ids]
        feature_vals = list(set(x_features))
        feature_vals_count = [x_features.count(x) for x in feature_vals]
        feature_vals_id = [
            [x_ids[i] for i, x in enumerate(x_features) if x == y] for y in feature_vals
        ]
        info_gain -= sum(
            [
                val_counts / len(x_ids) * self._get_entropy(val_ids)
                for val_counts, val_ids in zip(feature_vals_count, feature_vals_id)
            ]
        )
        return info_gain

    def _get_feature_max_information_gain(self, x_ids, feature_ids):
        features_entropy = [
            self._get_information_gain(x_ids, feature_id) for feature_id in feature_ids
        ]
        max_id = feature_ids[features_entropy.index(max(features_entropy))]
        return self.feature_names[max_id], max_id

    def id3(self):
        x_ids = [x for x in range(len(self.X))]
        feature_ids = [x for x in range(len(self.feature_names))]
        self.node = self._id3_recv(x_ids, feature_ids, self.node)

    def _id3_recv(self, x_ids, feature_ids, node):
        if not node:
            node = Node()
        labels_in_features = [self.labels[x] for x in x_ids]
        if len(set(labels_in_features)) == 1:
            node.value = self.labels[x_ids[0]]
            return node
        if len(feature_ids) == 0:
            node.value = max(set(labels_in_features), key=labels_in_features.count)
            return node
        best_feature_name, best_feature_id = self._get_feature_max_information_gain(
            x_ids, feature_ids
        )
        node.value = best_feature_name
        node.childs = []
        feature_values = list(set([self.X[x][best_feature_id] for x in x_ids]))
        for value in feature_values:
            child = Node()
            child.value = value
            node.childs.append(child)
            child_x_ids = [x for x in x_ids if self.X[x][best_feature_id] == value]
            if not child_x_ids:
                child.next = max(set(labels_in_features), key=labels_in_features.count)
            else:
                if feature_ids and best_feature_id in feature_ids:
                    to_remove = feature_ids.index(best_feature_id)
                    feature_ids.pop(to_remove)
                child.next = self._id3_recv(child_x_ids, feature_ids, child.next)
        return node

    def export_tree_graphviz(self, filename="id3_tree"):
        dot = Digraph(format="png")
        dot.attr(
            "node",
            shape="box",
            style="filled,rounded",
            color="lightblue2",
            fontname="Helvetica",
            fontsize="10",
        )

        def add_nodes_edges(node, parent_name=None, edge_label=""):
            if not node:
                return
            node_id = str(id(node))
            dot.node(node_id, label=str(node.value))

            if parent_name:
                dot.edge(parent_name, node_id, label=edge_label)

            if node.childs:
                for child in node.childs:
                    child_id = str(id(child))
                    dot.node(
                        child_id,
                        label=str(child.value),
                        style="filled,rounded",
                        color="mistyrose",
                    )
                    dot.edge(node_id, child_id, label="")
                    if isinstance(child.next, Node):
                        add_nodes_edges(child.next, child_id)
                    else:
                        # Leaf
                        leaf_id = str(id(child)) + "_leaf"
                        dot.node(
                            leaf_id,
                            label=f"good_waves = '{child.next}'",
                            style="filled,rounded",
                            color="palegreen",
                        )
                        dot.edge(child_id, leaf_id)

        add_nodes_edges(self.node)
        dot.render(filename, cleanup=True)
        print(f"Tree exported to {filename}.png")


if __name__ == "__main__":
    import numpy as np
    import pandas as pd

    data = {
        "wind_direction": ["N", "S", "E", "W"],
        "tide": ["Low", "High"],
        "swell_forecasting": ["small", "medium", "large"],
        "good_waves": ["Yes", "No"],
    }

    data_df = pd.DataFrame(columns=data.keys())
    np.random.seed(42)
    for i in range(100):
        data_df.loc[i, "wind_direction"] = np.random.choice(data["wind_direction"])
        data_df.loc[i, "tide"] = np.random.choice(data["tide"])
        data_df.loc[i, "swell_forecasting"] = np.random.choice(
            data["swell_forecasting"]
        )
        data_df.loc[i, "good_waves"] = np.random.choice(data["good_waves"])

    X = np.array(data_df.drop("good_waves", axis=1))
    y = np.array(data_df["good_waves"])
    feature_names = list(data_df.keys())[:3]

    tree_clf = DecisionTreeClassifier(X=X, feature_names=feature_names, labels=y)
    print("System entropy {:.4f}".format(tree_clf.entropy))
    tree_clf.id3()
    tree_clf.export_tree_graphviz("id3_clean_tree")
