import java.util.Scanner;

public class Main {

    /*
        s = "bbbbb"
        cc = 0
        for i in range(len(s)):
            has = []
            c = 0
            for j in range(i,len(s)):
                if s[j] in has:
                    break
                has.append(s[j])
                c+=1
            if c > cc: cc = c
            
        print(cc)
     */

    public static int getLengthOfLongestSubstring(String s) {
        int n = s.length();
        int cc = 0;
        for (int i = 0; i < n; i++ ) {
            boolean[] has = new boolean[256];
            int c = 0;
            for (int j = i; j < n; j++) {
                if(has[s.charAt(j)]) break;
                has[s.charAt(j)] = true;
                c++;
            }
            if (c > cc) cc = c;
        }

        return cc;
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String str;
        while (true) {
            str = sc.nextLine();
            if (str.equals("FIM"))
                break;
            System.out.println(getLengthOfLongestSubstring(str));
        }
        sc.close();
    }
}
