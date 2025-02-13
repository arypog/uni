import java.util.Scanner;

// javac -d bin MainR.java && java -cp bin MainR < pub.in > my.out
public class MainR {
    public static boolean isPalindromeRc(String s, int i, int j) {
        if (i >= j) return true;
        if (s.charAt(i) != s.charAt(j)) return false;

        return isPalindromeRc(s, i+1, j-1);
    }

    public static boolean isPalindrome(String s) {
        return isPalindromeRc(s, 0, s.length() - 1);
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