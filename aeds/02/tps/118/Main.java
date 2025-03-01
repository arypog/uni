import java.util.Scanner;

public class Main {
    static final int SPACE = 32;
    static final int DEL = 127;

    static char[] cipherR(char[] a, int k, int i, int n) {
        if (i >= n) return a;

        char c = a[i];
        if(c >= SPACE && c <= DEL) a[i] = (char) (c + k);
        else a[i] = (char) c;

        return cipherR(a, k, i+1, n);
    }

    public static String cipher(String s, int k) {
        char[] arr = s.toCharArray();
        arr = cipherR(arr, k, 0, arr.length);
        return new String(arr);
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String str;

        while (true) {
            str = sc.nextLine();
            if (str.equals("FIM"))
                break;
            System.out.println(cipher(str, 3));
        }
        sc.close();
    }
}
