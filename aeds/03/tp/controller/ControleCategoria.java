package controller;

import dao.ArquivoCategoria;
import model.Categoria;
import view.VisaoCategoria;

import java.util.ArrayList;

public class ControleCategoria {

    private ArquivoCategoria arquivoCategoria;
    private VisaoCategoria visaoCategoria;

    public ControleCategoria(ArquivoCategoria arquivoCategoria) {
        this.arquivoCategoria = arquivoCategoria;
        this.visaoCategoria = new VisaoCategoria();
    }

    public void handleMenu() {
        int opcao;
        do {
            opcao = visaoCategoria.menu();
            switch (opcao) {
                case 1 -> criar();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> atualizar();
                case 5 -> excluir();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    private void atualizar() {
        int id = visaoCategoria.lerId();
        try {
            Categoria c = null;
            try {
                c = arquivoCategoria.read(id);
            } catch (Exception ex) {
                System.out.println("Categoria não encontrada ou já excluída.");
                return;
            }
            System.out.print("Novo nome (atual: " + c.getNome() + "): ");
            try (java.util.Scanner sc = new java.util.Scanner(System.in)) {
                String novoNome = sc.nextLine();
                if (!novoNome.isEmpty()) c.setNome(novoNome);
            }
            if (arquivoCategoria.update(c))
                System.out.println("Categoria atualizada com sucesso.");
            else
                System.out.println("Não é possível atualizar: registro excluído.");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar categoria.");
            e.printStackTrace();
        }
    }

    private void criar() {
        Categoria c = visaoCategoria.leCategoria();
        try {
            int id = arquivoCategoria.create(c);
            System.out.println("Categoria criada com ID: " + id);
        } catch (Exception e) {
            System.err.println("Erro ao criar categoria.");
            e.printStackTrace();
        }
    }
    // quero que liste todas as categorias
    private void listar() {
        try {
            ArrayList<Categoria> todas = arquivoCategoria.readAll();
            todas.sort((a, b) -> a.getNome().compareToIgnoreCase(b.getNome()));
            todas.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Erro ao listar categorias.");
            e.printStackTrace();
        }
    }

    private void buscar() {
        int id = visaoCategoria.lerId();
        try {
            Categoria c = arquivoCategoria.read(id);
            if (c != null) System.out.println(c);
            else System.out.println("Categoria não encontrada.");
        } catch (Exception e) {
            System.err.println("Erro ao buscar categoria.");
            e.printStackTrace();
        }
    }

    private void excluir() {
        int id = visaoCategoria.lerId();
        try {
            boolean ok = arquivoCategoria.delete(id);
            if (ok)
                System.out.println("Categoria removida com sucesso.");
            else
                System.out.println("Categoria não encontrada ou já excluída.");
        } catch (Exception e) {
            System.err.println("Erro ao excluir categoria.");
            e.printStackTrace();
        }
    }
}
