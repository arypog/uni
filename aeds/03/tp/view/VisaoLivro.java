package view;

import model.Livro;
import java.util.Scanner;

public class VisaoLivro {
    private Scanner scanner;

    public VisaoLivro() {
        this.scanner = new Scanner(System.in, "UTF-8");
    }

    public int menu() {
        System.out.println("\n=== MENU LIVROS ===");
        System.out.println("(1) Cadastrar novo livro");
        System.out.println("(2) Listar livros");
        System.out.println("(3) Buscar livro por ID");
        System.out.println("(4) Atualizar livro");
        System.out.println("(5) Excluir livro");
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

    public Livro leLivro() {
        System.out.println("\n-- NOVO LIVRO --");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("Preço (R$): ");
        float preco = Float.parseFloat(scanner.nextLine());

        System.out.print("Data de publicação (timestamp ou 0 para atual): ");
        long dataPub;
        try {
            dataPub = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            dataPub = System.currentTimeMillis();
        }

        System.out.print("ID da categoria: ");
        int idCat = Integer.parseInt(scanner.nextLine());

        return new Livro(titulo, preco, dataPub, autor, idCat);
    }

    // Para compatibilidade com ControleLivro
    public Livro lerDados() {
        return leLivro();
    }

    public int lerId() {
        System.out.print("ID do livro: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void atualizarDados(Livro l) {
        System.out.println("\n-- ATUALIZAR LIVRO --");
        System.out.print("Novo título (atual: " + l.getTitulo() + "): ");
        String titulo = scanner.nextLine();
        if (!titulo.isEmpty()) l.setTitulo(titulo);
        System.out.print("Novo autor (atual: " + l.getAutor() + "): ");
        String autor = scanner.nextLine();
        if (!autor.isEmpty()) l.setAutor(autor);
        System.out.print("Novo preço (atual: " + l.getPreco() + "): ");
        String precoStr = scanner.nextLine();
        if (!precoStr.isEmpty()) l.setPreco(Float.parseFloat(precoStr));
        System.out.print("Nova data de publicação (atual: " + l.getDataPublicacao() + "): ");
        String dataStr = scanner.nextLine();
        if (!dataStr.isEmpty()) {
            // Aceita dd/mm/aaaa ou timestamp
            long novaData = l.getDataPublicacao();
            if (dataStr.matches("\\d{2}/\\d{2}/\\d{4}")) {
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date d = sdf.parse(dataStr);
                    novaData = d.getTime();
                } catch (Exception e) {
                    System.out.println("Formato de data inválido. Mantendo data atual.");
                }
            } else {
                try {
                    novaData = Long.parseLong(dataStr);
                } catch (NumberFormatException e) {
                    System.out.println("Formato de data inválido. Mantendo data atual.");
                }
            }
            l.setDataPublicacao(novaData);
        }
        System.out.print("Nova categoria (atual: " + l.getIdCategoria() + "): ");
        String catStr = scanner.nextLine();
        if (!catStr.isEmpty()) l.setIdCategoria(Integer.parseInt(catStr));
    }

    public void mostraLivro(Livro l) {
    System.out.println("\n-- DADOS DO LIVRO --");
    System.out.println("ID: " + l.getId());
    System.out.println(l);
    }
}
