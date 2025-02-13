import java.util.Scanner;

// javac -d bin Main.java && java -cp bin Main < pub.in > my.out
public class Main {
    public static boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        for (; i <= j; i++, j--)
            if (s.charAt(i) != s.charAt(j)) return false;
        return true;
    }    

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            String str = sc.nextLine();
            if (str.equals("FIM")) break;
            if (isPalindrome(str)) System.out.println("SIM");
            else System.out.println("NAO");
        }
    }
}