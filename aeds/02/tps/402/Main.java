import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Show {
  public String id, title;
  public int releaseYear;
  int fieldCount = 12;

  Show(String line) {
    this.parseLine(line);
  }

  void setShowData(String[] fields) {
    this.id = (fields[0].isEmpty()) ? "NaN" : fields[0];
    this.title = (fields[2].isEmpty()) ? "NaN" : fields[2];
    this.releaseYear = (!fields[7].isEmpty() && fields[7].matches("\\d+")) ? Integer.parseInt(fields[7]) : 0;
  }

  String[] parseCSVLine(String line) {
    char[] chars = line.toCharArray();
    String[] fields = new String[this.fieldCount];

    int bufferIndex = 0, fieldIndex = 0;
    char[] buffer = new char[1000];
    boolean inQuotes = false;

    for (int i = 0; i < chars.length; i++) {
      char ch = chars[i];
      if (inQuotes) {
        if (ch == '\"' && i + 1 < chars.length && chars[i + 1] == ',') {
          inQuotes = false;
        } else if (ch != '\"') {
          buffer[bufferIndex++] = ch;
        }
      } else if (ch == ',') {
        fields[fieldIndex++] = new String(buffer, 0, bufferIndex);
        bufferIndex = 0;
      } else if (ch == '\"') {
        inQuotes = true;
      } else {
        buffer[bufferIndex++] = ch;
      }
    }
    fields[fieldIndex] = new String(buffer, 0, bufferIndex);
    return fields;
  }

  void parseLine(String line) {
    String[] fields = parseCSVLine(line);
    setShowData(fields);
  }
}

class Node {
  int value;
  NodeB root;
  Node left, right;

  public Node() {
    this.value = 0;
    this.left = null;
    this.right = null;
  }

  public Node(int value) {
    this.value = value;
    this.left = null;
    this.right = null;
  }
}

class NodeB {
  String value;
  NodeB left, right;

  NodeB() {
    this.value = null;
    this.left = null;
    this.right = null;
  }

  NodeB(String value) {
    this.value = value;
    this.left = null;
    this.right = null;
  }
}

class Tree3D {
  Node root;

  public Tree3D() {
    this.root = null;

    insert(7);
    insert(3);
    insert(11);
    insert(1);
    insert(5);
    insert(9);
    insert(13);
    insert(0);
    insert(2);
    insert(4);
    insert(6);
    insert(8);
    insert(10);
    insert(12);
    insert(14);
  }

  public void insert(int x) {
    root = insert(x, root);
  }

  private Node insert(int x, Node node) {
    if (node == null) {
      node = new Node(x);
    } else if (x < node.value) {
      node.left = insert(x, node.left);
    } else if (x > node.value) {
      node.right = insert(x, node.right);
    }
    return node;
  }

  public void insert(Show show) {
    this.root = insert(show, root);
  }

  private Node insert(Show show, Node node) {
    int key = show.releaseYear % 15;

    if (node == null) {
      node = new Node(key);
      node.root = new NodeB(show.title);
    } else if (key < node.value) {
      node.left = insert(show, node.left);
    } else if (key > node.value) {
      node.right = insert(show, node.right);
    } else {
      insertBTree(show.title, node);
    }
    return node;
  }

  private void insertBTree(String title, Node node) {
    node.root = insertBTree(title, node.root);
  }

  private NodeB insertBTree(String title, NodeB node) {
    if (node == null) {
      node = new NodeB(title);
    } else if (title.compareTo(node.value) < 0) {
      node.left = insertBTree(title, node.left);
    } else if (title.compareTo(node.value) > 0) {
      node.right = insertBTree(title, node.right);
    }
    return node;
  }

  public boolean preOrderSearch(String title) {
    System.out.print("raiz ");
    return preOrderSearch(title, root);
  }

  private boolean preOrderSearch(String title, Node node) {
    if (node == null) return false;

    if (searchB(title, node.root)) return true;

    System.out.print(" ESQ ");
    if (preOrderSearch(title, node.left)) return true;

    System.out.print(" DIR ");
    return preOrderSearch(title, node.right);
  }

  private boolean searchB(String title, NodeB node) {
    if (node == null) return false;
    if (title.equals(node.value)) return true;

    if (title.compareTo(node.value) < 0) {
      System.out.print("esq ");
      return searchB(title, node.left);
    } else {
      System.out.print("dir ");
      return searchB(title, node.right);
    }
  }
}

public class Main {
  public static Show[] loadShows(String path) throws FileNotFoundException {
    File file = new File(path);
    Scanner scanner = new Scanner(file);
    Show[] shows = new Show[1368];
    scanner.nextLine(); // Skip header

    for (int i = 0; scanner.hasNextLine(); i++) {
      Show show = new Show(scanner.nextLine());
      shows[i] = show;
    }

    scanner.close();
    return shows;
  }

  public static void main(String[] args) throws FileNotFoundException {
    Scanner scanner = new Scanner(System.in);
    Show[] showList = loadShows("/tmp/disneyplus.csv");

    Tree3D tree = new Tree3D();

    String input;
    while (!(input = scanner.nextLine()).equals("FIM")) {
      int id = Integer.parseInt(input.substring(1)) - 1;
      tree.insert(showList[id]);
    }

    while (!(input = scanner.nextLine()).equals("FIM")) {
      System.out.println(tree.preOrderSearch(input) ? " SIM" : " NAO");
    }

    scanner.close();
  }
}

