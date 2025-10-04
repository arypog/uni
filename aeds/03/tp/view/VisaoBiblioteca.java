package view;

import java.util.Scanner;

public class VisaoBiblioteca {
    private Scanner scanner;

    public VisaoBiblioteca() {
        this.scanner = new Scanner(System.in, "UTF-8");
    }

    public int menuPrincipal() {
        System.out.println("\n=== SISTEMA DE BIBLIOTECA ===");
        System.out.println("(1) Gerenciar Categorias");
        System.out.println("(2) Gerenciar Livros");
        System.out.println("(3) Gerenciar Leitores");
        System.out.println("(4) Gerenciar Empréstimos (Retiras)");
        System.out.println("\n(S) Sair");
        System.out.print("Opção: ");

        String opcao = scanner.nextLine().toUpperCase();
        if (opcao.equals("S")) return 0;

        try {
            return Integer.parseInt(opcao);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
