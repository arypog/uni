import java.util.Scanner;

public class Main {
    static boolean isVowelR(String s, int k, int i, int n) {
        if (i >= n)
            return (k == n);

        String vowels = "aeiouAEIOU ";
        int lenVow = vowels.length();

        for (int j = 0; j < lenVow; j++) {
            if (s.charAt(i) == vowels.charAt(j)) {
                k++;
                break;
            }
        }

        return isVowelR(s, k, i + 1, n);
    }

    public static boolean isVowel(String s) {
        return isVowelR(s, 0, 0, s.length());
    }

    static boolean isConsonantR(String s, int k, int i, int n) {
        if (i >= n)
            return (k == n);

        String vowels = "aeiouAEIOU";
        int lenVow = vowels.length();

        char c = s.charAt(i);
        if (!(c >= 'A' && c <= 'z') && c != ' ')
            return false;
        for (int j = 0; j < lenVow; j++) {
            if (c == vowels.charAt(j)) {
                k--;
                break;
            }
        }

        return isConsonantR(s, k, i + 1, n);
    }

    public static boolean isConsonant(String s) {
        return isConsonantR(s, s.length(), 0, s.length());
    }

    static boolean isNumberR(String s, int i, int n) {
        if (i >= n)
            return true;

        return (s.charAt(i) >= '0' && s.charAt(i) <= '9') && isNumberR(s, i + 1, n);
    }

    public static boolean isNumber(String s) {
        return isNumberR(s, 0, s.length());
    }

    static boolean isRealNumberR(String s, int c, int i, int n) {
        if (i >= n)
            return true;

        char ch = s.charAt(i);

        if (ch == ',' || ch == '.')
            c++;
        else if (!(ch >= '0' && ch <= '9') || c > 1) {
            return false;
        }
        return isRealNumberR(s, c, i + 1, n);

    }

    public static boolean isRealNumber(String s) {
        return isRealNumberR(s, 0, 0, s.length());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str;
        str = sc.nextLine();
        while (!str.equals("FIM")) {
            System.out.print(isVowel(str) ? "SIM " : "NAO ");
            System.out.print(isConsonant(str) ? "SIM " : "NAO ");
            System.out.print(isNumber(str) ? "SIM " : "NAO ");
            System.out.print(isRealNumber(str) ? "SIM" : "NAO");
            System.out.println();
            str = sc.nextLine();
        }
        sc.close();
    }
}