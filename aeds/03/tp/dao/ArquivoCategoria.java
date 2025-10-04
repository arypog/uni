package dao;

import base.Arquivo;
import index.IndexEntry;
import index.ArvoreBMais;
import model.Categoria;

import java.util.ArrayList;
import java.util.Comparator;

public class ArquivoCategoria extends Arquivo<Categoria> {
    @Override
    public ArrayList<Categoria> readAll() throws Exception {
        ArrayList<Categoria> todas = new ArrayList<>();
        for (Categoria c : super.readAll()) {
            if (c != null) todas.add(c);
        }
        return todas;
    }

    private ArvoreBMais<IndexEntry> indiceNome;

    public ArquivoCategoria() throws Exception {
        super("dados/categorias.db", Categoria.class.getConstructor());
        indiceNome = new ArvoreBMais<>(
            IndexEntry.class.getConstructor(),
            5,
            "dados/categorias.nome.idx.db"
        );
    }

    @Override
    public int create(Categoria obj) throws Exception {
        int id = super.create(obj);
        if (obj != null && obj.getNome() != null && !obj.getNome().isEmpty()) {
            indiceNome.create(new IndexEntry(obj.getNome().hashCode(), id));
        }
        return id;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Categoria c = null;
        try {
            c = super.read(id);
        } catch (Exception e) {
            return false;
        }
        if (c != null) {
            indiceNome.delete(new IndexEntry(c.getNome().hashCode(), id));
            return super.delete(id);
        }
        return false;
    }

    @Override
    public boolean update(Categoria obj) throws Exception {
        try {
            Categoria atual = super.read(obj.getId());
            if (atual != null) {
                if (!atual.getNome().equals(obj.getNome())) {
                    indiceNome.delete(new IndexEntry(atual.getNome().hashCode(), obj.getId()));
                    indiceNome.create(new IndexEntry(obj.getNome().hashCode(), obj.getId()));
                }
                return super.update(obj);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public ArrayList<Categoria> readByNome(String nome) throws Exception {
        ArrayList<Categoria> cats = new ArrayList<>();
        try {
            ArrayList<IndexEntry> pares = indiceNome.read(new IndexEntry(nome.hashCode(), -1));
            for (IndexEntry p : pares) {
                Categoria c = null;
                try {
                    c = super.read(p.getNum2());
                } catch (Exception e) {
                    c = null;
                }
                if (c != null) cats.add(c);
            }
        } catch (Exception e) {
            file.seek(4);
            while (file.getFilePointer() < file.length()) {
                char lapide = (char) file.readByte();
                short tam = file.readShort();
                byte[] ba = new byte[tam];
                file.readFully(ba);
                if (lapide == ' ') {
                    Categoria c = constructor.newInstance();
                    c.fromByteArray(ba);
                    if (c.getNome().equalsIgnoreCase(nome))
                        cats.add(c);
                }
            }
        }
        cats.sort(Comparator.comparing(Categoria::getNome));
        return cats;
    }
}
