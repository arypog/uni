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

class HashReserve {
    Show[] table;
    int primarySize, reserveSize, totalSize, reserveIndex;

    public HashReserve() {
        this(21, 9);
    }

    public HashReserve(int primarySize, int reserveSize) {
        this.primarySize = primarySize;
        this.reserveSize = reserveSize;
        this.totalSize = primarySize + reserveSize;
        this.table = new Show[totalSize];
        this.reserveIndex = 0;
    }

    public int hash(String title) {
        int sum = 0;
        for (int i = 0; i < title.length(); i++) {
            sum += title.charAt(i);
        }
        return sum % primarySize;
    }

    public boolean insert(Show show) {
        if (show == null) return false;

        int pos = hash(show.title.trim());
        if (table[pos] == null) {
            table[pos] = show;
            return true;
        } else if (reserveIndex < reserveSize) {
            table[primarySize + reserveIndex] = show;
            reserveIndex++;
            return true;
        }

        return false;
    }

    public void search(String title) {
        int pos = hash(title);
        if (table[pos] != null && table[pos].title.equals(title)) {
            System.out.println(" (Posicao: " + pos + ") SIM");
            return;
        }

        for (int i = primarySize; i < totalSize; i++) {
            if (table[i] != null && table[i].title.equals(title)) {
                System.out.println(" (Posicao: " + i + ") SIM");
                return;
            }
        }

        System.out.println(" (Posicao: " + pos + ") NAO");
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

    HashReserve hashTable = new HashReserve();
    String line;

    while (!(line = sc.nextLine()).equals("FIM")) {
      int id = Integer.parseInt(line.substring(1));
      hashTable.insert(showMap.get(id));
    }

    while (!(line = sc.nextLine()).equals("FIM")) {
      hashTable.search(line);
    }

    sc.close();
  }
}

