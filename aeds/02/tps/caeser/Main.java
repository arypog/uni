import java.util.Scanner;

// javac -d ../bin Main.java && java -cp ../bin Main 
public class Main {
    public static String cipher(String s, int k) {
        char arr[] = s.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];

            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                char base = (c >= 'a') ? 'a' : 'A';
                arr[i] = (char) (base + (c - base + k) % 26);
            } // else if
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
    }
}