import java.util.Scanner;

public class Main {
    static String inverseR(String s, char[] arr, int i, int n) {
        if(i >= n) return new String(arr);

        arr[i] = s.charAt(n - 1 - i);

        return inverseR(s, arr, i+1, n);
    }
    public static String inverse(String s) {
        char[] arr =  new char[s.length()];
        int n = arr.length;

        return inverseR(s, arr, 0, n);
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

