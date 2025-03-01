/*
 * Leia duas strings sendo que a primeira é o nome de uma página web e a segunda
 * , seu endereço. Por exemplo, Pontifícia Universidade Católica de Minas Gerais
 *  e www.pucminas.br. Em seguida, mostre na tela o número de vogais 
 * (sem e com acento), consoantes e dos padrões <br> e <table> que aparecem no 
 * código dessa página. A entrada padrão é composta por várias linhas. Cada uma 
 * contém várias strings sendo que a primeira é um endereço web e as demais o 
 * nome dessa página web. A última linha da entrada padrão contém a palavra 
 * ``FIM''.
    A saída padrão contém várias linhas sendo que cada uma apresenta o número 
    de ocorrência (valor $x_{i}$ entre parênteses) de cada caractere ou string 
    solicitado. Cada linha de saída será da seguinte forma: a(x 1) e(x 2) i(x 3)
    o(x 4) u(x 5) á(x 6) é(x 7) í(x 8) ó(x 9) ú(x 10) à(x 11) è(x 12) ì(x 13) 
    ò(x 14) ù(x 15) ã(x 16) õ(x 17) â(x 18) ê(x 19) î(x 20) ô(x 21) û(x 22) 
    consoante(x 23) <br>(x 24) <table>(x 25) nomepágina(x 26).
 */
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Main {
    static String fetchHTML(String urlString) throws Exception {
        URL url = new URL(urlString);
        InputStream is = url.openStream();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        
        return os.toString("UTF-8"); 
    }

    static int countOccurrencesRegex(String text, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    static void readHTML(String html, String pageName) {
        String vowels = "aeiouáéíóúàèìòùãõâêîôû";
        String consonants = "bcdfghjklmnpqrstvwxyz";
        Map<String, Integer> counts = new LinkedHashMap<>();

        for (char c : vowels.toCharArray()) {
            counts.put(String.valueOf(c), 0);
        }
        counts.put("consoante", 0);
        counts.put("<br>", 0);
        counts.put("<table>", 0);
        
        for (char c : html.toLowerCase().toCharArray()) {
            if(counts.containsKey(String.valueOf(c))) {
                counts.put(String.valueOf(c), counts.get(String.valueOf(c)) + 1);
            } else if(consonants.contains(String.valueOf(c))) {
                counts.put("consoante", counts.get("consoante") + 1);
            }
        }

        counts.put("<br>", countOccurrencesRegex(html, "<br>"));
        counts.put("<table>", countOccurrencesRegex(html, "<table>"));
    
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            result.append(entry.getKey()).append("(").append(entry.getValue()).append(") ");
        }
        System.out.println(result.append(pageName).toString().trim());
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String s;
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            if (s.equals("FIM")) break;
            String url = sc.nextLine();
            readHTML(fetchHTML(url), s);
        }
        sc.close(); 
    }
}

