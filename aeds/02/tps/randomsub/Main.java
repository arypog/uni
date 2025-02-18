import java.util.Scanner;
import java.util.Random;

class LCG {
    private static long seed;

    public static void setSeed(long s) {
        seed = s;
    }

    public static int nextInt() {
        seed = (seed * 1664525 + 1013904223) % 4294967296L;
        return (int) seed;
    }
}

// javac -d ../bin Main.java && java -cp ../bin Main
public class Main {
    public static String subs(String s, char a, char b) {
        char arr[] = s.toCharArray();
        for (int i = 0; i < arr.length; i++)
            if (arr[i] == a) arr[i] = b;
        return new String(arr);
    }    

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        Random g = new Random();
        g.setSeed(4);

        //LCG g = new LCG();
        //g.setSeed(42);

        String str;
        str = sc.nextLine();
        while(!str.equals("FIM")) {
            char a = (char)  ('a' + (Math.abs(g.nextInt()) % 26));
            char b = (char)  ('a' + (Math.abs(g.nextInt()) % 26));

            System.out.println(subs(str, a, b));

            str = sc.nextLine();
        }
        sc.close();
    }
}