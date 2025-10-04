package dao;

import base.Arquivo;
import index.IndexEntry;
import index.ArvoreBMais;
import model.Retira;

import java.util.ArrayList;
import java.util.Comparator;

public class ArquivoRetira extends Arquivo<Retira> {

    private ArvoreBMais<IndexEntry> indiceLeitor;

    public ArquivoRetira() throws Exception {
        super("dados/retiras.db", Retira.class.getConstructor());
        indiceLeitor = new ArvoreBMais<>(
            IndexEntry.class.getConstructor(),
            5,
            "dados/retiradas.leitor.idx.db"
        );
    }

    @Override
    public int create(Retira obj) throws Exception {
        int id = super.create(obj);
        indiceLeitor.create(new IndexEntry(obj.getIdLeitor(), id));
        return id;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Retira r = super.read(id);
        if (r != null) {
            indiceLeitor.delete(new IndexEntry(r.getIdLeitor(), id));
            return super.delete(id);
        }
        return false;
    }

    @Override
    public boolean update(Retira obj) throws Exception {
        return super.update(obj);
    }

    /** Lista todas as retiradas de um leitor */
    public ArrayList<Retira> readByLeitor(int idLeitor) throws Exception {
        ArrayList<Retira> retiradas = new ArrayList<>();

        try {
            ArrayList<IndexEntry> pares = indiceLeitor.read(new IndexEntry(idLeitor, -1));
            for (IndexEntry p : pares) {
                Retira r = super.read(p.getNum2());
                if (r != null) retiradas.add(r);
            }
        } catch (Exception e) {
            // fallback
        }

        if (retiradas.isEmpty()) {
            file.seek(4);
            while (file.getFilePointer() < file.length()) {
                char lapide = (char) file.readByte();
                short tam = file.readShort();
                byte[] ba = new byte[tam];
                file.readFully(ba);
                if (lapide == ' ') {
                    Retira r = constructor.newInstance();
                    r.fromByteArray(ba);
                    if (r.getIdLeitor() == idLeitor)
                        retiradas.add(r);
                }
            }
        }

        retiradas.sort(Comparator.comparing(Retira::getDataEmprestimo));
        return retiradas;
    }
}
