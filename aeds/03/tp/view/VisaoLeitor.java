package view;

import model.Leitor;
import java.util.Scanner;

public class VisaoLeitor {
    public void atualizarDados(model.Leitor l) {
        System.out.println("\n-- ATUALIZAR LEITOR --");
        System.out.print("Novo nome (atual: " + l.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) l.setNome(nome);
        System.out.print("Novo e-mail (atual: " + l.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) l.setEmail(email);
        System.out.print("Novo telefone (atual: " + l.getTelefone() + "): ");
        String telefone = scanner.nextLine();
        if (!telefone.isEmpty()) l.setTelefone(telefone);
    }
    public String lerEmail() {
        System.out.print("E-mail do leitor: ");
        return scanner.nextLine();
    }

    public int lerId() {
        System.out.print("ID do leitor: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public Leitor lerDados() {
        return leLeitor();
    }
    private Scanner scanner;

    public VisaoLeitor() {
        this.scanner = new Scanner(System.in, "UTF-8");
    }

    public int menu() {
        System.out.println("\n=== MENU LEITORES ===");
        System.out.println("(1) Cadastrar novo leitor");
        System.out.println("(2) Listar leitores");
        System.out.println("(3) Buscar leitor por Email");
        System.out.println("(4) Atualizar leitor");
        System.out.println("(5) Excluir leitor");
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

    public Leitor leLeitor() {
        System.out.println("\n-- NOVO LEITOR --");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        return new Leitor(nome, email, telefone);
    }

    public void mostraLeitor(Leitor l) {
        System.out.println("\n-- DADOS DO LEITOR --");
        System.out.println(l);
    }
}

