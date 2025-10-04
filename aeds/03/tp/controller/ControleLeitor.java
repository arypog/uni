package controller;

import dao.ArquivoLeitor;
import model.Leitor;
import view.VisaoLeitor;


public class ControleLeitor {

    private ArquivoLeitor arquivoLeitor;
    private VisaoLeitor visaoLeitor;

    public ControleLeitor(ArquivoLeitor arquivoLeitor) {
        this.arquivoLeitor = arquivoLeitor;
        this.visaoLeitor = new VisaoLeitor();
    }

    public void handleMenu() {
        int opcao;
        do {
            opcao = visaoLeitor.menu();
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
        Leitor l = visaoLeitor.leLeitor();
        try {
            int id = arquivoLeitor.create(l);
            System.out.println("Leitor criado com ID: " + id);
        } catch (Exception e) {
            System.err.println("Erro ao criar leitor.");
            e.printStackTrace();
        }
    }

    private void listar() {
        try {
            var leitores = arquivoLeitor.readAll();
            leitores.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Erro ao listar leitores.");
            e.printStackTrace();
        }
    }

    private void atualizar() {
        int id = visaoLeitor.lerId();
        try {
            Leitor l = arquivoLeitor.read(id);
            if (l != null) {
                visaoLeitor.atualizarDados(l);
                arquivoLeitor.update(l);
                System.out.println("Leitor atualizado com sucesso.");
            } else {
                System.out.println("Leitor não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar leitor.");
            e.printStackTrace();
        }
    }

    private void buscar() {
        String email = visaoLeitor.lerEmail();
        try {
            Leitor l = arquivoLeitor.readByEmail(email);
            if (l != null) System.out.println(l);
            else System.out.println("Leitor não encontrado.");
        } catch (Exception e) {
            System.err.println("Erro ao buscar leitor.");
            e.printStackTrace();
        }
    }

    private void excluir() {
        int id = visaoLeitor.lerId();
        try {
            if (arquivoLeitor.delete(id))
                System.out.println("Leitor removido com sucesso.");
            else
                System.out.println("Leitor não encontrado.");
        } catch (Exception e) {
            System.err.println("Erro ao excluir leitor.");
            e.printStackTrace();
        }
    }
}
