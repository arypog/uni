package dao;

import base.Arquivo;
import index.IndexEntry;
import index.ArvoreBMais;
import model.Leitor;

public class ArquivoLeitor extends Arquivo<Leitor> {

    private ArvoreBMais<IndexEntry> indiceEmail;

    public ArquivoLeitor() throws Exception {
        super("dados/leitores.db", Leitor.class.getConstructor());
        indiceEmail = new ArvoreBMais<>(
            IndexEntry.class.getConstructor(),
            5,
            "dados/leitores.email.idx.db"
        );
    }

    @Override
    public int create(Leitor obj) throws Exception {
        int id = super.create(obj);
        indiceEmail.create(new IndexEntry(obj.getEmail().hashCode(), id));
        return id;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Leitor l = super.read(id);
        if (l != null) {
            indiceEmail.delete(new IndexEntry(l.getEmail().hashCode(), id));
            return super.delete(id);
        }
        return false;
    }

    @Override
    public boolean update(Leitor obj) throws Exception {
        return super.update(obj);
    }

    
    public Leitor readByEmail(String email) throws Exception {
        try {
            var pares = indiceEmail.read(new IndexEntry(email.hashCode(), -1));
            for (var p : pares) {
                Leitor l = super.read(p.getNum2());
                if (l != null && l.getEmail().equalsIgnoreCase(email))
                    return l;
            }
        } catch (Exception e) {
            
        }

        file.seek(4);
        while (file.getFilePointer() < file.length()) {
            char lapide = (char) file.readByte();
            short tam = file.readShort();
            byte[] ba = new byte[tam];
            file.readFully(ba);
            if (lapide == ' ') {
                Leitor l = constructor.newInstance();
                l.fromByteArray(ba);
                if (l.getEmail().equalsIgnoreCase(email))
                    return l;
            }
        }
        return null;
    }
}
