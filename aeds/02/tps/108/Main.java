import java.util.Scanner;

public class Main {
    public static int sumR(String s, int i, int n) {
        if (i >= n) return 0;

        int sum = 0;
        sum = (int) (s.charAt(i) - '0') + sumR(s, i+1, n);

        return sum; 
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String str;
        while (true) {
            str = sc.nextLine();
            if (str.equals("FIM")) break;
            System.out.println(sumR(str, 0, str.length()));
        }
        sc.close();
    }
}