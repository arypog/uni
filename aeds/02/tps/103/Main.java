import java.util.Scanner;

// javac -d ../bin Main.java && java -cp ../bin Main 
public class Main {
    public static String cipher(String s, int k) {
        char[] arr = s.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (c >= 0x20 && c <= 0x7F) {
                arr[i] = (char) (c + k);
            }
        }

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