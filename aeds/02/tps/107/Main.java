import java.util.Scanner;

public class Main {
    public static String inverse(String s) {
        char[] arr =  new char[s.length()];
        int n = arr.length;
        for (int i = 0; i < n/2 + 1 ; i++) {
            arr[i] = s.charAt(n - 1 - i);
            arr[n - i - 1] = s.charAt(i);
        }

        return new String(arr);
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String str;
        while (true) {
            str = sc.nextLine();
            if (str.equals("FIM")) break;
            System.out.println(inverse(str));
        }
        sc.close();
    }
}