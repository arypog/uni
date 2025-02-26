import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class Main {
    public static char toLower(char c) {
        return (c >= 'A' && c <= 'Z') ? (char) (c + 32) : c;
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
            System.out.println(isAnagram(str) ? "SIM" : nao);
        }
        sc.close();
    }
}
