package controller;

import dao.ArquivoLivro;
import model.Livro;
import view.VisaoLivro;

import java.util.ArrayList;

public class ControleLivro {

    private ArquivoLivro arquivoLivro;
    private VisaoLivro visaoLivro;

    public ControleLivro(ArquivoLivro arquivoLivro) {
        this.arquivoLivro = arquivoLivro;
        this.visaoLivro = new VisaoLivro();
    }

    public void handleMenu() {
        int opcao;
        do {
            opcao = visaoLivro.menu();
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

    private void criar() {
        Livro l = visaoLivro.leLivro();
        try {
            dao.ArquivoCategoria arquivoCategoria = new dao.ArquivoCategoria();
            model.Categoria categoria = null;
            try {
                categoria = arquivoCategoria.read(l.getIdCategoria());
            } catch (Exception ex) {
                categoria = null;
            }
            if (categoria == null) {
                System.out.println("Categoria informada não existe. Criação de livro cancelada.");
                return;
            }
            int id = arquivoLivro.create(l);
            System.out.println("Livro criado com ID: " + id);
        } catch (Exception e) {
            System.err.println("Erro ao criar livro.");
        }
    }

    private void listar() {
        try {
            var livros = arquivoLivro.readAll();
            livros.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Erro ao listar livros.");
        }
    }

    private void buscar() {
        int id = visaoLivro.lerId();
        try {
            Livro l = null;
            try {
                l = arquivoLivro.read(id);
            } catch (Exception ex) {
                l = null;
            }
            if (l != null) System.out.println(l);
            else System.out.println("Livro não encontrado.");
        } catch (Exception e) {
            System.err.println("Erro ao buscar livro.");
        }
    }

    private void atualizar() {
        int id = visaoLivro.lerId();
        try {
            Livro l = arquivoLivro.read(id);
            if (l != null) {
                visaoLivro.atualizarDados(l);
                arquivoLivro.update(l);
                System.out.println("Livro atualizado com sucesso.");
            } else {
                System.out.println("Livro não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar livro.");
            e.printStackTrace();
        }
    }

    private void excluir() {
        int id = visaoLivro.lerId();
        try {
            if (arquivoLivro.delete(id))
                System.out.println("Livro removido com sucesso.");
            else
                System.out.println("Livro não encontrado.");
        } catch (Exception e) {
            System.err.println("Erro ao excluir livro.");
            e.printStackTrace();
        }
    }
}
