import java.util.Scanner;

public class Main {
    public static int countWords(String s) {
        int k = 1, n = s.length();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == ' ') k++;
        }
        return k; 
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String str;
        while (true) {
            str = sc.nextLine();
            if (str.equals("FIM")) break;
            System.out.println(countWords(str));
        }
        sc.close();
    }
}
