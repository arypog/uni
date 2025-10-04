package view;

import model.Categoria;
import java.util.Scanner;

public class VisaoCategoria {

    public int lerId() {
        System.out.print("ID da categoria: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    private Scanner scanner;

    public VisaoCategoria() {
        this.scanner = new Scanner(System.in, "UTF-8");
    }

    public int menu() {
        System.out.println("\n=== MENU CATEGORIAS ===");
        System.out.println("(1) Cadastrar nova categoria");
        System.out.println("(2) Listar categorias");
        System.out.println("(3) Buscar categoria por ID");
        System.out.println("(4) Atualizar categoria");
        System.out.println("(5) Excluir categoria");
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

    public Categoria leCategoria() {
        System.out.println("\n-- NOVA CATEGORIA --");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        return new Categoria(nome);
    }

    public void mostraCategoria(Categoria c) {
        System.out.println("\n-- DADOS DA CATEGORIA --");
        System.out.println(c);
    }
}
