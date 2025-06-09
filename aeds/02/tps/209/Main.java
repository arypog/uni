import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Show {
  public   String   id;
  public   String   type;
  public   String   title;
  public   String   director;
  private  String[] cast;
  private  String   country;
//  private  Date     date_added; // nao vai acontecer
  private  String   date_added;
  private  int      release_year;
  private  String   rating;
  private  String   duration;
  private  String[] listed_in;
  private  String   description;
  private  int      size = 12;

  void setShow(String[] parts) {

    this.id           = (parts[0].isEmpty()) ? "NaN" : parts[0];
    this.type         = (parts[1].isEmpty()) ? "NaN" : parts[1];
    this.title        = (parts[2].isEmpty()) ? "NaN" : parts[2];
    this.director     = (parts[3].isEmpty()) ? "NaN" : parts[3];
    this.cast         = (parts[4].isEmpty()) ? new String[] {"NaN"} : sort(parts[4].split(", "));
    this.country      = (parts[5].isEmpty()) ? "NaN" : parts[5];
    this.date_added   = (parts[6].isEmpty()) ? "March 1, 1900" : parts[6];
    this.release_year = (parts[7].isEmpty()) ? -1 : Integer.parseInt(parts[7]);
    this.rating       = (parts[8].isEmpty()) ? "NaN" : parts[8];
    this.duration     = (parts[9].isEmpty()) ? "NaN" : parts[9];
    this.listed_in    = (parts[10].isEmpty()) ? new String[] {"NaN"} : parts[10].split(",");
    this.description  = (parts[11].isEmpty()) ? "NaN" : parts[11];
  }

  void printShow() {
    System.out.print(
      "=> "  + this.id +
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
    for (int i = 0; i < parts.length; i++) {
      for (int j = 0; j < parts.length - 1; j++) {
        if (parts[j].compareTo(parts[j + 1]) > 0) {
          String tmp = parts[j]; 
          parts[j] = parts[j + 1];
          parts[j + 1] = tmp;
        }
      }
    }
    return parts;
  }

  void readCSVLineRegex(String line) {
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
      }
      else if (ch == '\"') {
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

public class Main {
  static void sort2(ArrayList<Show> parts) {
    int n = parts.size();
    for (int i = 0; i < n - 1; i++) {
      int min = i;
      for (int j = i + 1; j < n; j++) {
        if (parts.get(j).title.compareTo(parts.get(min).title) < 0) {
          min = j;
        }
      }
      Show temp = parts.get(i);
      parts.set(i, parts.get(min));
      parts.set(min, temp);
    }
  }

  static void sort3(ArrayList<Show> parts) {
    int n = parts.size();
    for (int i = 1; i < n; i++) {
      Show key = parts.get(i);
      int j = i - 1;

      while (j >= 0) {
        Show current = parts.get(j);
        int cmp = current.type.compareTo(key.type);

        if (cmp > 0 || (cmp == 0 && current.title.compareTo(key.title) > 0)) {
          parts.set(j + 1, current);
          j--;
        } else {
          break;
        }
      }

      parts.set(j + 1, key);
    }
  }

  static void heapfy(ArrayList<Show> kaka, int n, int i) {
    int max = i;
    int l = 2 * i + 1;
    int r = 2 * i + 2;

    if (l < n && isGreater(kaka.get(l), kaka.get(max))) {
      max = l;
    }

    if (r < n && isGreater(kaka.get(r), kaka.get(max))) {
      max = r;
    }

    if (max != i) {
      Show tmp = kaka.get(i);
      kaka.set(i, kaka.get(max));
      kaka.set(max, tmp);
      heapfy(kaka, n, max);
    }
  }

  static boolean isGreater(Show a, Show b) {
    int cmp = a.director.compareTo(b.director);
    if (cmp > 0) return true;
    if (cmp < 0) return false;

    return a.title.compareTo(b.title) > 0;
  }

  static void heapSort(ArrayList<Show> kaka) {
    int n = kaka.size();
    for (int i = n/2 - 1; i >= 0; i--)
      heapfy(kaka, n, i);

    for (int i = n - 1; i > 0; i--) {
      Show tmp = kaka.get(0);
      kaka.set(0, kaka.get(i));
      kaka.set(i, tmp);

      heapfy(kaka, i, 0);
    }
  }

  public static void main(String args[]) throws FileNotFoundException {
//    File f = new File("201/test.csv");
    //File f = new File("201/disneyplus.csv");
    File f = new File("/tmp/disneyplus.csv");
    Scanner sc = new Scanner(f);
    Show[] shows = new Show[1369];

    int i = 0;
    for (; i < shows.length; i++) {
      shows[i] = new Show();
    }
    i = 0;
    sc.nextLine();
    while(sc.hasNextLine()) {
      String line = sc.nextLine();
      shows[i].readCSVLine(line);
      i++;
    }
    sc.close();
    
    String inputId;
    Scanner scid = new Scanner(System.in);

    // lol
    ArrayList<Show> lol = new ArrayList<Show>();
    while (scid.hasNextLine()) {
      inputId = scid.nextLine();
      if (inputId.equals("FIM")) break;

      for (Show show : shows) {
        if (show.id.equals(inputId)) {
          lol.add(show);
          break;
        }
      }
    }

    heapSort(lol); 

    for (Show show : lol) {
      show.printShow();
    } 

    sc.close();
    scid.close();
  }
}
