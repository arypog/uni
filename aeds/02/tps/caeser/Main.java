import java.util.Scanner;

// javac -d ../bin Main.java && java -cp ../bin Main 
public class Main {
    public static String cipher(String s, int k) {
        char[] arr = s.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];

            /* doideira isso ser errado
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                char base = (c >= 'a') ? 'a' : 'A';
                arr[i] = (char) (base + (c - base + k) % 26);
            } else if ((c >= 0xE0 && c <= 0xFF) || (c >= 0xC0 && c <= 0xDF)) {
                char base = (char) ((c >= 0xE0) ? 0xE0 : 0xC0);
                arr[i] = (char) (base + (c - base + k) % 26);
            } 
            */

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
            if (str.equals("FIM")) break;
            System.out.println(cipher(str, 3));
        }
        sc.close();
    }
}