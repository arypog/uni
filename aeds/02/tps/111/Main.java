import java.util.Scanner;

public class Main {
    public static int getLengthOfLongestSubstring(String s) {
        return 1;
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String str;
        while (true) {
            str = sc.nextLine();
            if (str.equals("FIM")) break;
            System.out.println(getLengthOfLongestSubstring(str));
        }
        sc.close();
    }
}
