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
  Show elem;
  Node next;

  Node(Show elem) {
    this.elem = elem;
    this.next = null;
  }
}

class MyList {
  private Node begin;

  public void inserirInicio(Show show) {
    Node novo = new Node(show);
    novo.next = begin;
    begin = novo;
  }

  public void inserirFim(Show show) {
    Node novo = new Node(show);
    if (begin == null) {
      begin = novo;
    } else {
      Node tmp = begin;
      while (tmp.next != null)
        tmp = tmp.next;
      tmp.next = novo;
    }
  }

  public void inserir(Show show, int pos) {
    if (pos == 0) {
      inserirInicio(show);
    } else {
      Node novo = new Node(show);
      Node tmp = begin;
      for (int i = 0; i < pos - 1 && tmp != null; i++) {
        tmp = tmp.next;
      }
      if (tmp != null) {
        novo.next = tmp.next;
        tmp.next = novo;
      }
    }
  }

  public Show removerInicio() {
    if (begin == null)
      return null;
    Show removido = begin.elem;
    begin = begin.next;
    return removido;
  }

  public Show removerFim() {
    if (begin == null)
      return null;
    if (begin.next == null) {
      Show removido = begin.elem;
      begin = null;
      return removido;
    }
    Node tmp = begin;
    while (tmp.next.next != null)
      tmp = tmp.next;
    Show removido = tmp.next.elem;
    tmp.next = null;
    return removido;
  }

  public Show remover(int pos) {
    if (pos == 0)
      return removerInicio();
    Node tmp = begin;
    for (int i = 0; i < pos - 1 && tmp != null; i++)
      tmp = tmp.next;
    if (tmp == null || tmp.next == null)
      return null;
    Show removido = tmp.next.elem;
    tmp.next = tmp.next.next;
    return removido;
  }

  public void print() {
    Node tmp = begin;
    while (tmp != null) {
      tmp.elem.printShow();
      tmp = tmp.next;
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

    MyList list = new MyList();
    String line;

    while (!(line = sc.nextLine()).equals("FIM")) {
      int id = Integer.parseInt(line.substring(1));
      list.inserirFim(showMap.get(id));
    }

    int n = Integer.parseInt(sc.nextLine());
    for (int i = 0; i < n; i++) {
      line = sc.nextLine();
      String[] parts = line.split(" ");
      String cmd = parts[0];

      switch (cmd) {
        case "II":
          list.inserirInicio(showMap.get(Integer.parseInt(parts[1].substring(1))));
          break;
        case "IF":
          list.inserirFim(showMap.get(Integer.parseInt(parts[1].substring(1))));
          break;
        case "I*":
          list.inserir(showMap.get(Integer.parseInt(parts[2].substring(1))), Integer.parseInt(parts[1]));
          break;
        case "RI": {
          Show r = list.removerInicio();
          if (r != null) System.out.println("(R) " + r.title);
          break;
        }
        case "RF": {
          Show r = list.removerFim();
          if (r != null) System.out.println("(R) " + r.title);
          break;
        }
        case "R*": {
          Show r = list.remover(Integer.parseInt(parts[1]));
          if (r != null) System.out.println("(R) " + r.title);
          break;
        }
      }
    }

    list.print();
    sc.close();
  }
}

