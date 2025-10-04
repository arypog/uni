package view;

import model.Retira;
import java.util.Scanner;

public class VisaoRetira {

    public int lerId() {
        System.out.print("ID do empréstimo: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    private Scanner scanner;

    public VisaoRetira() {
        this.scanner = new Scanner(System.in, "UTF-8");
    }

    public int menu() {
        System.out.println("\n=== MENU RETIRAS ===");
        System.out.println("(1) Registrar empréstimo");
        System.out.println("(2) Listar empréstimos");
        System.out.println("(3) Buscar empréstimo por ID");
        System.out.println("(4) Excluir registro de empréstimo");
        System.out.println("\n(V) Voltar");
        System.out.print("Opção: ");

        String opcao = scanner.nextLine().toUpperCase();
        if (opcao.equals("V")) return 0;
        try {
            return Integer.parseInt(opcao);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public Retira leRetira() {
        System.out.println("\n-- NOVO EMPRÉSTIMO --");
        System.out.print("ID do livro: ");
        int idLivro = Integer.parseInt(scanner.nextLine());
        System.out.print("ID do leitor: ");
        int idLeitor = Integer.parseInt(scanner.nextLine());
        long dataEmprestimo = System.currentTimeMillis();
        return new Retira(idLivro, idLeitor, dataEmprestimo);
    }

    public void mostraRetira(Retira r) {
        System.out.println("\n-- DADOS DO EMPRÉSTIMO --");
        System.out.println(r);
    }
}

