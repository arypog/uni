/*
 * Crie um método iterativo que recebe uma string como parâmetro e retorna true 
 * se a string é uma senha válida, ou false caso contrário. Uma senha é 
 * considerada válida se contém pelo menos 8 caracteres, incluindo pelo menos 
 * uma letra maiúscula, uma letra minúscula, um número e um caractere especial 
 * (por exemplo, !, @, #, etc.). Na saída padrão, para cada linha de entrada, 
 * escreva uma linha de saída com SIM/NÃO indicando se a senha é válida. 
 * Por exemplo, se a entrada for Senha123!s, a saída deve ser SIM.
 */
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

class Main {
    static boolean isValidPassword(String s) {
        int n = s.length();
        if (n < 8) return false;

        String listOfSpecialCh = "<,>.?/`~!@#$%^&*()-_=+{[}]|\\" ;
        int nSp = listOfSpecialCh.length();

        boolean hasLowCaseCh = false;
        boolean hasUpCaseCh = false;
        boolean hasNumber = false;
        boolean hasSpecialCh = false;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c >= 'A' && c <= 'Z') hasUpCaseCh = true;
            if (c >= 'a' && c <= 'z') hasLowCaseCh = true;
            if (c >= '0' && c <= '9') hasNumber = true;
            for (int j = 0; j < nSp; j++) {
                if (listOfSpecialCh.charAt(j) == c){
                    hasSpecialCh = true;
                    break;
                }
            }

        }
        return (hasLowCaseCh && hasUpCaseCh && hasNumber && hasSpecialCh);
    }


    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in); 
        String s;
        // https://byte-tools.com/en/ascii/code/0xc3/
        // https://byte-tools.com/en/ascii/code/0x83/
        String nao = new String(new byte[]{0x4E, (byte) 0xC3, (byte) 0x83,  0x4F}, StandardCharsets.UTF_8);
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            if (s.equals("FIM")) break;
            System.out.println(isValidPassword(s) ? "SIM" : nao);
        }
        sc.close();
    }
}
