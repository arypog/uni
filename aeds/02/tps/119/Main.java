import java.util.Scanner;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;

class Main {
    public static boolean and(ArrayList<Boolean> operands) {
        for (Boolean operand : operands)
            if (!operand)
                return false;
        return true;
    }

    public static boolean or(ArrayList<Boolean> operands) {
        for (Boolean operand : operands)
            if (operand)
                return true;
        return false;
    }

    public static boolean not(boolean operand) {
        return !operand;
    }

    public static String convertLetterToBoolStr(String letter, boolean[] values) {
        return String.valueOf(values[letter.charAt(0) - 'A']);
    }

    public static boolean evaluateRecursive(Deque<String> tokens) {
        if (tokens.isEmpty()) {
            return false;
        }

        String token = tokens.poll();
        System.out.println(token);

        switch (token) {
            case "and":
            case "or":
            case "not":
                ArrayList<Boolean> operands = new ArrayList<>();
                if (!tokens.isEmpty() && tokens.peek().equals("(")) {
                    tokens.poll(); // removes the ( 
                    while (!tokens.isEmpty() && !tokens.peek().equals(")")) {
                        operands.add(evaluateRecursive(tokens));
                    }
                    tokens.poll(); 
                }

                switch (token) {
                    case "and":
                        return and(operands);
                    case "or":
                        return or(operands);
                    case "not":
                        return not(operands.get(0));
                    default:
                        return false;
                }
        
            default:
                if (token.equals("true"))
                    return true;
                else 
                    return false;
        }
    }

    public static void evaluateExpression(String statement) {
        String[] parts = statement.split("\\s+", 2);

        if (parts.length == 2) {
            int n = Integer.parseInt(parts[0]);
            boolean[] boolVals = new boolean[n];
            for (int i = 0; i < n; i++) {
                boolVals[i] = (statement.charAt(i * 2 + 2) == '1');
            }

            String expression = parts[1]
                    .replaceAll("[0-9]", "")
                    .replace(",", " ") 
                    .replace("(", " ( ")
                    .replace(")", " ) ") 
                    .trim();

            String[] tokensArray = expression.split("\\s+");
            Deque<String> tokens = new ArrayDeque<>();

            for (String token : tokensArray) {
                if (token.matches("[A-C]")) {
                    tokens.add(convertLetterToBoolStr(token, boolVals));
                } else {
                    tokens.add(token);
                }
            }

            boolean result = evaluateRecursive(tokens);
            System.out.println(result ? "1" : "0");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s;
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            if (s.equals("FIM"))
                break;
            evaluateExpression(s);
        }
        sc.close();
    }
}