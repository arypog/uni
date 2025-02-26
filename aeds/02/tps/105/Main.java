import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;

class Main {
    public static String and(ArrayList<Boolean> l) {
        for (Boolean i : l)
            if (!i)
                return String.valueOf(false);
        return String.valueOf(true);
    }

    public static String or(ArrayList<Boolean> l) {
        for (boolean i : l)
            if (i)
                return String.valueOf(true);
        return String.valueOf(false);
    }

    public static String not(ArrayList<Boolean> l) {
        return (l.get(0)) ? String.valueOf(false) : String.valueOf(true);
    }

    /**
     * A - A = 0
     * B - A = 1
     * C - A = 2
     */
    public static String convertLetterToBoolStr(String letter, boolean[] values) {
        return String.valueOf(values[letter.charAt(0) - 'A']);
    }

    public static boolean evaluate(String expression, boolean[] values) {
        String[] tokens = expression.split("\\s+");

        Stack<String> operatorsAndOperands = new Stack<>();

        for (String token : tokens) {
            switch (token) {
                case ")":
                    ArrayList<Boolean> currentOperands = new ArrayList<>();
                    while (!operatorsAndOperands.isEmpty()) {
                        if (operatorsAndOperands.peek().equals("(")) {
                            operatorsAndOperands.pop();
                            break;
                        }
        
                        currentOperands.add(Boolean.parseBoolean(operatorsAndOperands.pop()));
                    }

                    switch (operatorsAndOperands.pop()) {
                        case "and":
                            operatorsAndOperands.push(and(currentOperands));
                            break;

                        case "or":
                            operatorsAndOperands.push(or(currentOperands));
                            break;

                        case "not":
                            operatorsAndOperands.push(not(currentOperands));

                            break;
                    }
                    break;

                default:
                    if (token.matches("[A-C]"))
                        operatorsAndOperands.push(convertLetterToBoolStr(token, values));
                    else
                        operatorsAndOperands.push(token); // pushes the (
                    break;
            }
        }

        return Boolean.parseBoolean(operatorsAndOperands.pop());

    }

    public static void evaluateExpression(String statemment) {
        String[] parts = statemment.split("\\s+", 2);

        if (parts.length == 2) {

            int n = Integer.parseInt(parts[0]);
            boolean[] boolVals = new boolean[n];
            for (int i = 0; i < n; i++) {
                boolVals[i] = (statemment.charAt(i * 2 + 2) == '1');
            }

            String expression = parts[1].replaceAll("[0-9]", "")
                    .replace(",", " ")
                    .replace("(", " ( ")
                    .replace(")", " ) ")
                    .trim();

            if (evaluate(expression, boolVals))
                System.out.println("1");
            else
                System.out.println("0");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s;
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            if (s.equals("FIM")) break;
            evaluateExpression(s);
        }
        sc.close();
    }
}