import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

class Show {
  public String id, type, title, director, date_added, duration;
  public int release_year;
  public String[] cast, listed_in;
  public String country, rating, description;
  public int size = 12;

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

class DNode {
    Show show;
    DNode prev, next;

    DNode(Show show) {
        this.show = show;
        this.prev = this.next = null;
    }
}

class DoublyLinkedList {
    public DNode head;

    public void append(Show show) {
        DNode newNode = new DNode(show);
        if (head == null) {
            head = newNode;
            return;
        }

        DNode last = head;
        while (last.next != null) {
            last = last.next;
        }

        last.next = newNode;
        newNode.prev = last;
    }

    public void quickSort() {
        DNode last = lastNode(head);
        quickSortRec(head, last);
    }

    public DNode lastNode(DNode node) {
        while (node != null && node.next != null) {
            node = node.next;
        }
        return node;
    }

    public void swap(DNode a, DNode b) {
        Show temp = a.show;
        a.show = b.show;
        b.show = temp;
    }

    public int monthToNumber(String month) {
        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };

        for (int i = 0; i < 12; i++) {
            if (months[i].equalsIgnoreCase(month)) {
                return i + 1;
            }
        }
        return -1;
    }

    public int parseDate(String date) {
        if (date == null || date.equals("NaN")) return -1;

        String[] parts = date.split(" ");
        if (parts.length != 3) return -1;

        try {
            String month = parts[0];
            int day = Integer.parseInt(parts[1].replace(",", ""));
            int year = Integer.parseInt(parts[2]);

            int monthNum = monthToNumber(month);
            if (monthNum == -1) return -1;

            return year * 10000 + monthNum * 100 + day;
        } catch (Exception e) {
            return -1;
        }
    }

    public int compareUwU(Show a, Show b) {
        int dateA = parseDate(a.date_added);
        int dateB = parseDate(b.date_added);

        if (dateA != dateB) {
            return Integer.compare(dateA, dateB);
        }

        return a.title.compareTo(b.title);
    }

    public DNode partition(DNode low, DNode high) {
        Show pivot = high.show;
        DNode i = low.prev;

        for (DNode j = low; j != high; j = j.next) {
            if (compareUwU(j.show, pivot) < 0) {
                i = (i == null) ? low : i.next;
                swap(i, j);
            }
        }

        i = (i == null) ? low : i.next;
        swap(i, high);
        return i;
    }

    public void quickSortRec(DNode low, DNode high) {
        if (high != null && low != high && low != high.next) {
            DNode p = partition(low, high);
            quickSortRec(low, p.prev);
            quickSortRec(p.next, high);
        }
    }

    public void print() {
        for (DNode current = head; current != null; current = current.next) {
            current.show.printShow();
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

    DoublyLinkedList list = new DoublyLinkedList();
    String line;

    while (!(line = sc.nextLine()).equals("FIM")) {
      int id = Integer.parseInt(line.substring(1));
      list.append(showMap.get(id));
    }

    list.quickSort();

    list.print();
    sc.close();
  }
}

