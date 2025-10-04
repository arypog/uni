package controller;

import view.VisaoBiblioteca;
import dao.*;

public class ControleBiblioteca {

    private VisaoBiblioteca visaoBiblioteca;
    private ArquivoCategoria arquivoCategoria;
    private ArquivoLivro arquivoLivro;
    private ArquivoLeitor arquivoLeitor;
    private ArquivoRetira arquivoRetira;

    public ControleBiblioteca() {
        try {
            this.visaoBiblioteca = new VisaoBiblioteca();
            this.arquivoCategoria = new ArquivoCategoria();
            this.arquivoLivro = new ArquivoLivro();
            this.arquivoLeitor = new ArquivoLeitor();
            this.arquivoRetira = new ArquivoRetira();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar o sistema de biblioteca.");
            e.printStackTrace();
        }
    }

    public void iniciar() {
        int opcao;
        do {
            opcao = visaoBiblioteca.menuPrincipal();
            switch (opcao) {
                case 1 -> new ControleCategoria(arquivoCategoria).handleMenu();
                case 2 -> new ControleLivro(arquivoLivro).handleMenu();
                case 3 -> new ControleLeitor(arquivoLeitor).handleMenu();
                case 4 -> new ControleRetira(arquivoRetira).handleMenu();
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
