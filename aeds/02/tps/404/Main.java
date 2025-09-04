import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

class Show {
  public String id, type, title, director, date_added, duration;
  public int release_year;
  String[] cast, listed_in;
  String country, rating, description;
  int size = 12;

  Show(String line) {
    this.readCSVLine(line);
  }

  void setShow(String[] parts) {
    this.id           = (parts[0].isEmpty()) ? "NaN" : parts[0];
    this.type         = (parts[1].isEmpty()) ? "NaN" : parts[1];
    this.title        = (parts[2].isEmpty()) ? "NaN" : parts[2];
    this.director     = (parts[3].isEmpty()) ? "NaN" : parts[3];
    this.cast         = (parts[4].isEmpty()) ? new String[]{"NaN"} : sort(parts[4].split(", "));
    this.country      = (parts[5].isEmpty()) ? "NaN" : parts[5];
    this.date_added   = (parts[6].isEmpty()) ? "March 1, 1900" : parts[6];
    this.release_year = (parts[7].isEmpty()) ? -1 : Integer.parseInt(parts[7]);
    this.rating       = (parts[8].isEmpty()) ? "NaN" : parts[8];
    this.duration     = (parts[9].isEmpty()) ? "NaN" : parts[9];
    this.listed_in    = (parts[10].isEmpty()) ? new String[]{"NaN"} : parts[10].split(",");
    this.description  = (parts[11].isEmpty()) ? "NaN" : parts[11];
  }

  void printShow() {
    System.out.print(
      "=> " + this.id +
      " ## " + this.title +
      " ## " + this.type +
      " ## " + this.director +
      " ## " + Arrays.toString(this.cast).replaceAll("  ", " ") +
      " ## " + this.country +
      " ## " + this.date_added +
      " ## " + this.release_year +
      " ## " + this.rating +
      " ## " + this.duration +
      " ## " + Arrays.toString(this.listed_in).replaceAll("  ", " ") +
      " ##\n"
    );
  }

  String[] sort(String[] parts) {
    Arrays.sort(parts);
    return parts;
  }

  String[] parseCSVLine(String line) {
    int j = 0, k = 0;
    char[] buffer = new char[1000];
    char[] array = line.toCharArray();
    String[] parts = new String[this.size];
    boolean inQuotes = false;

    for (int i = 0; i < array.length; i++) {
      char ch = array[i];
      if (inQuotes) {
        if (ch == '\"' && i + 1 < array.length && array[i + 1] == ',') {
          inQuotes = false;
        } else if (ch != '\"') {
          buffer[j++] = ch;
        }
      } else if (ch == ',') {
        parts[k++] = new String(buffer, 0, j);
        j = 0;
      } else if (ch == '\"') {
        inQuotes = true;
      } else {
        buffer[j++] = ch;
      }
    }
    parts[k] = new String(buffer, 0, j);
    return parts;
  }

  void readCSVLine(String line) {
    String[] parts = parseCSVLine(line);
    setShow(parts);
  }
}

class Node {
  Show show;
  Node left, right, parent;
  boolean isRed; // true = RED, false = BLACK

  Node(Show show) {
    this.show = show;
    this.isRed = true;
  }
}

class RedBlackTree {
  final Node NIL = new Node(null);
  Node root;

  RedBlackTree() {
    NIL.isRed = false;
    NIL.left = NIL.right = NIL.parent = NIL;
    root = NIL;
  }

  public void insert(Show show) {
    Node newNode = new Node(show);
    newNode.left = newNode.right = newNode.parent = NIL;

    Node parent = NIL;
    Node current = root;

    while (current != NIL) {
      parent = current;
      if (newNode.show.title.compareTo(current.show.title) < 0)
      current = current.left;
      else
      current = current.right;
    }

    newNode.parent = parent;

    if (parent == NIL)
    root = newNode;
    else if (newNode.show.title.compareTo(parent.show.title) < 0)
    parent.left = newNode;
    else
    parent.right = newNode;

    fixInsert(newNode);
  }

  void fixInsert(Node z) {
    while (z.parent.isRed) {
      if (z.parent == z.parent.parent.left) {
        Node y = z.parent.parent.right;
        if (y.isRed) {
          // Case 1: Uncle is red
          z.parent.isRed = false;
          y.isRed = false;
          z.parent.parent.isRed = true;
          z = z.parent.parent;
        } else {
          if (z == z.parent.right) {
            // Case 2: Triangle
            z = z.parent;
            leftRotate(z);
          }
          // Case 3: Line
          z.parent.isRed = false;
          z.parent.parent.isRed = true;
          rightRotate(z.parent.parent);
        }
      } else {
        Node y = z.parent.parent.left;
        if (y.isRed) {
          z.parent.isRed = false;
          y.isRed = false;
          z.parent.parent.isRed = true;
          z = z.parent.parent;
        } else {
          if (z == z.parent.left) {
            z = z.parent;
            rightRotate(z);
          }
          z.parent.isRed = false;
          z.parent.parent.isRed = true;
          leftRotate(z.parent.parent);
        }
      }
    }
    root.isRed = false;
  }

  void leftRotate(Node x) {
    Node y = x.right;
    x.right = y.left;
    if (y.left != NIL)
    y.left.parent = x;
    y.parent = x.parent;
    if (x.parent == NIL)
    root = y;
    else if (x == x.parent.left)
    x.parent.left = y;
    else
    x.parent.right = y;
    y.left = x;
    x.parent = y;
  }

  void rightRotate(Node y) {
    Node x = y.left;
    y.left = x.right;
    if (x.right != NIL)
    x.right.parent = y;
    x.parent = y.parent;
    if (y.parent == NIL)
    root = x;
    else if (y == y.parent.right)
    y.parent.right = x;
    else
    y.parent.left = x;
    x.right = y;
    y.parent = x;
  }

  public void search(String title) {
    StringBuilder path = new StringBuilder();
    boolean found = searchRec(root, title, path);
    System.out.println("=>raiz" + path.toString() + (found ? " SIM" : " NAO"));
  }

  boolean searchRec(Node node, String title, StringBuilder path) {
    if (node == NIL) return false;

    int cmp = title.compareTo(node.show.title);
    if (cmp < 0) {
      path.append(" esq");
      return searchRec(node.left, title, path);
    } else if (cmp > 0) {
      path.append(" dir");
      return searchRec(node.right, title, path);
    }
    return true;
  }
}

public class Main {
  public static LinkedHashMap<Integer, Show> loadCsvFile(String path) throws FileNotFoundException {
    File f = new File(path);
    Scanner sc = new Scanner(f);
    LinkedHashMap<Integer, Show> shows = new LinkedHashMap<>();
    sc.nextLine(); // skip header

    for (int i = 1; sc.hasNextLine(); i++) {
      Show show = new Show(sc.nextLine());
      shows.put(i, show);
    }

    sc.close();
    return shows;
  }

  public static void main(String[] args) throws FileNotFoundException {
    Scanner sc = new Scanner(System.in);
    LinkedHashMap<Integer, Show> showMap = loadCsvFile("/tmp/disneyplus.csv");

    RedBlackTree tree = new RedBlackTree();
    String line;

    while (!(line = sc.nextLine()).equals("FIM")) {
      int id = Integer.parseInt(line.substring(1));
      tree.insert(showMap.get(id));
    }

    while (!(line = sc.nextLine()).equals("FIM")) {
      tree.search(line);
    }

    sc.close();
  }
}
