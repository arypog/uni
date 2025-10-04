package controller;

import dao.ArquivoRetira;
import model.Retira;
import view.VisaoRetira;

import java.util.ArrayList;

public class ControleRetira {

    private ArquivoRetira arquivoRetira;
    private VisaoRetira visaoRetira;

    public ControleRetira(ArquivoRetira arquivoRetira) {
        this.arquivoRetira = arquivoRetira;
        this.visaoRetira = new VisaoRetira();
    }

    public void handleMenu() {
        int opcao;
        do {
            opcao = visaoRetira.menu();
            switch (opcao) {
                case 1 -> criar();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> excluir();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void criar() {
        Retira r = visaoRetira.leRetira();
        try {
            dao.ArquivoLeitor arquivoLeitor = new dao.ArquivoLeitor();
            dao.ArquivoLivro arquivoLivro = new dao.ArquivoLivro();
            model.Leitor leitor = null;
            model.Livro livro = null;
            try {
                leitor = arquivoLeitor.read(r.getIdLeitor());
            } catch (Exception ex) {
                leitor = null;
            }
            if (leitor == null) {
                System.out.println("Leitor informado não existe. Empréstimo cancelado.");
                return;
            }
            try {
                livro = arquivoLivro.read(r.getIdLivro());
            } catch (Exception ex) {
                livro = null;
            }
            if (livro == null) {
                System.out.println("Livro informado não existe. Empréstimo cancelado.");
                return;
            }
            if (livro.isAlugado()) {
                System.out.println("Livro não está disponível para empréstimo.");
                return;
            }
            
            livro.setAlugado(true);
            arquivoLivro.update(livro);
            int id = arquivoRetira.create(r);
            System.out.println("Empréstimo registrado com ID: " + id);
        } catch (Exception e) {
            System.err.println("Erro ao registrar empréstimo.");
        }
    }

    private void listar() {
        try {
            var retiras = arquivoRetira.readAll();
            retiras.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Erro ao listar empréstimos.");
        }
    }

    private void buscar() {
        int id = visaoRetira.lerId();
        try {
            Retira r = arquivoRetira.read(id);
            if (r != null) System.out.println(r);
            else System.out.println("Empréstimo não encontrado.");
        } catch (Exception e) {
            System.err.println("Erro ao buscar empréstimo.");
            e.printStackTrace();
        }
    }

    private void excluir() {
        int id = visaoRetira.lerId();
        try {
            model.Retira r = null;
            try {
                r = arquivoRetira.read(id);
            } catch (Exception ex) {
                r = null;
            }
            if (r == null) {
                System.out.println("Empréstimo não encontrado.");
                return;
            }
            dao.ArquivoLivro arquivoLivro = new dao.ArquivoLivro();
            model.Livro livro = null;
            try {
                livro = arquivoLivro.read(r.getIdLivro());
            } catch (Exception ex) {
                livro = null;
            }
            if (livro != null) {
                livro.setAlugado(false);
                arquivoLivro.update(livro);
            }
            if (arquivoRetira.delete(id))
                System.out.println("Empréstimo removido com sucesso.");
            else
                System.out.println("Erro ao remover empréstimo.");
        } catch (Exception e) {
            System.err.println("Erro ao excluir empréstimo.");
        }
    }
}
