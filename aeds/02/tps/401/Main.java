import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

class Show {
  public String id, type, title, director, date_added, duration;
  public int release_year;
  private String[] cast, listed_in;
  private String country, rating, description;
  private int size = 12;

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
    Node left, right;

    Node(Show show) {
        this.show = show;
        this.left = this.right = null;
    }
}
class BinarySearchTree {
    Node root;

    BinarySearchTree() {
        this.root = null;
    }

    public void insert(Show newShow) {
        root = insertRec(root, newShow);
    }

    private Node insertRec(Node root, Show newShow) {
        if (root == null) {
            root = new Node(newShow);
            return root;
        }

        if (newShow.title.compareTo(root.show.title) < 0) {
            root.left = insertRec(root.left, newShow);
        }
        else if (newShow.title.compareTo(root.show.title) > 0) {
            root.right = insertRec(root.right, newShow);
        }
        return root;
    }

    public void search(String title) {
        StringBuilder path = new StringBuilder();
        boolean found = searchRec(root, title, path);

        System.out.println("=>raiz " + path.toString().trim() + (found ? " SIM" : " NAO"));
    }

    private boolean searchRec(Node root, String title, StringBuilder path) {
        if (root == null) {
            return false;
        }
        if (title.compareTo(root.show.title) < 0) {
            path.append(" esq");
            return searchRec(root.left, title, path);
        }
        else if (title.compareTo(root.show.title) > 0) {
            path.append(" dir");
            return searchRec(root.right, title, path);
        }

        return true;
    }

    public void inOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(Node root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.println(root.show.title);
            inOrderRec(root.right);
        }
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

    BinarySearchTree bst = new BinarySearchTree();
    String line;

    while (!(line = sc.nextLine()).equals("FIM")) {
      int id = Integer.parseInt(line.substring(1));
      bst.insert(showMap.get(id));
    }

    while (!(line = sc.nextLine()).equals("FIM")) {
      bst.search(line);
    }

    sc.close();
  }
}


