/* ls - 
 * Crie um método iterativo que recebe uma string e retorna true se a mesma é 
 * composta somente por vogais. 
 * Crie outro método iterativo que recebe uma string e retorna true se a mesma 
 * é composta somente por consoantes. 
 * Crie um terceiro método iterativo que recebe uma string e retorna true se a mesma 
 * corresponde a um número inteiro. 
 * Crie um quarto método iterativo que recebe uma string e retorna true se a 
 * mesma corresponde a um número real. 
 * Na saída padrão, para cada linha de entrada, escreva outra de saída da seguinte forma 
 * X1 X2 X3 X4 onde cada X i é um booleano indicando se a é entrada é: composta 
 * somente por vogais (X1); composta somente somente por consoantes (X2); um 
 * número inteiro (X3); um número real (X4). Se X$i$ for verdadeiro, seu valor 
 * será SIM, caso contrário, NÃO.
 */

import java.util.Scanner;

public class Main {
    public static boolean isVowel(String s) {
        String vowels = "aeiouAEIOU ";
        int k = 0, n = s.length(), lenVow = vowels.length();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < lenVow; j++) {
                if (s.charAt(i) == vowels.charAt(j)) {
                    k++;
                    break;
                }
            }
        }

        return (k == n);
    }

    public static boolean isConsonant(String s) {
        String vowels = "aeiouAEIOU";
        int k = s.length(), n = s.length(), lenVow = vowels.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (!(c >= 'A' && c <= 'z') && c != ' ' ) return false; 
            for (int j = 0; j < lenVow; j++) {
                if (c == vowels.charAt(j)) {
                    k--;
                    break;
                }
            }
        }

        return (k == n);
    }

    public static boolean isNumber(String s) {
        int n = s.length();
        for (int i = 0; i < n; i++) {
            if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9')) return false;
        }
        return true;
    }

    public static boolean isRealNumber(String s) {
        int n = s.length();
        int c = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == ',' || s.charAt(i) == '.') {
                c++;
            } else if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9') || c > 1) 
                return false;

        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str; 
        str = sc.nextLine();
        while (!str.equals("FIM")) {
            System.out.print(isVowel(str) ? "SIM " : "NAO ");
            System.out.print(isConsonant(str)? "SIM " : "NAO ");
            System.out.print(isNumber(str)? "SIM " : "NAO ");
            System.out.print(isRealNumber(str)? "SIM" : "NAO");
            System.out.println();
            str = sc.nextLine();
        }
        sc.close();
    }
}
