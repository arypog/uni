import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class Main {
    public static char toLower(char c) {
        return (c >= 'A' && c <= 'Z') ? (char) (c + 32) : c;
    }

    public static int sumChar(String s, int i, int n) {
        if (i>=n) return 0;
        int sum = toLower(s.charAt(i)) - 'a' + sumChar(s, i+1, n);
        return sum;
    }

    public static boolean isAnagram3(String s) {
        int n = s.length();
        int sumA = sumChar(s, 0, n/2 - 1);
        int sumB = sumChar(s, (n/2 + 2), n);
        return (sumA == sumB);
    }

    public static boolean isAnagram2(String s) {
        String[] parts = s.replaceAll("\\s+", "").split("-", 2);
        int n = parts[0].length();
        if (n != parts[1].length()) return false;

        /* 
         * 1.iter ('a' & 'c')
         * c[0] +1 (a "abc")
         * c[2] -1 (c "cba")
         * [1, 0, -1, ...]
         * 
         * 2.iter ('b' & 'b')
         * c[1] +1 (b "abc")
         * c[1] -1 (b "cba")
         * [1, 0, -1, ...] -> no change
         * 
         * 3.iter ('c' & 'a')
         * c[2] +1
         * c[0] -1
         * [0, 0, 0, ...] if all 0; pog anagram. pog
         */
        int[] count = new int[26];
        for (int i = 0; i < n; i++) {
            count[toLower(parts[0].charAt(i)) - 'a']++; // a - a = 0, b - a = 1, ...
            count[toLower(parts[1].charAt(i)) - 'a']--;
        }

        for (int c : count) {
            if (c != 0) return false;
        }

        return true;
        
    }

    public static boolean isAnagram(String s) {
        String[] parts = s.replaceAll("\\s+", "").split("-", 2);
        int n1 = parts[0].length(), n2 = parts[1].length();
        int k = 0;
        for (int i = 0; i < n1; i++) {
            char c1 = parts[0].charAt(i);
            for(int j = 0; j < n2; j++) {
                char c2 = parts[1].charAt(j);
                if (toLower(c1) == toLower(c2)) {
                    k++;
                    break;
                }
            }
        }

        return (k == n1);
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String str;
        while (true) {
            str = sc.nextLine();
            if (str.equals("FIM")) break;
            // https://byte-tools.com/en/ascii/code/0xc3/
            // https://byte-tools.com/en/ascii/code/0x83/
            String nao = new String(new byte[]{0x4E, (byte) 0xC3, (byte) 0x83,  0x4F}, StandardCharsets.UTF_8);
            System.out.println(isAnagram3(str) ? "SIM" : nao);
        }
        sc.close();
    }
}
