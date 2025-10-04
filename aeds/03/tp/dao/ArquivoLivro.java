package dao;

import base.Arquivo;
import index.IndexEntry;
import index.ArvoreBMais;
import model.Livro;

import java.util.ArrayList;
import java.util.Comparator;

public class ArquivoLivro extends Arquivo<Livro> {

    private ArvoreBMais<IndexEntry> indiceCategoria;

    public ArquivoLivro() throws Exception {
        super("dados/livros.db", Livro.class.getConstructor());
        indiceCategoria = new ArvoreBMais<>(
            IndexEntry.class.getConstructor(),
            5,
            "dados/livros.categoria.idx.db"
        );
    }

    @Override
    public int create(Livro obj) throws Exception {
        int id = super.create(obj);
        indiceCategoria.create(new IndexEntry(obj.getIdCategoria(), id));
        return id;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Livro l = super.read(id);
        if (l != null) {
            indiceCategoria.delete(new IndexEntry(l.getIdCategoria(), id));
            return super.delete(id);
        }
        return false;
    }

    @Override
    public boolean update(Livro obj) throws Exception {
        return super.update(obj);
    }


    public ArrayList<Livro> readByCategoria(int idCategoria) throws Exception {
        ArrayList<Livro> livros = new ArrayList<>();

        try {
            ArrayList<IndexEntry> pares = indiceCategoria.read(new IndexEntry(idCategoria, -1));
            for (IndexEntry p : pares) {
                Livro l = super.read(p.getNum2());
                if (l != null) livros.add(l);
            }
        } catch (Exception e) {
           
        }

        if (livros.isEmpty()) {
            file.seek(4);
            while (file.getFilePointer() < file.length()) {
                char lapide = (char) file.readByte();
                short tam = file.readShort();
                byte[] ba = new byte[tam];
                file.readFully(ba);
                if (lapide == ' ') {
                    Livro l = constructor.newInstance();
                    l.fromByteArray(ba);
                    if (l.getIdCategoria() == idCategoria)
                        livros.add(l);
                }
            }
        }

        livros.sort(Comparator.comparing(Livro::getTitulo));
        return livros;
    }
}
