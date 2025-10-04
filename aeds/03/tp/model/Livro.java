package model;

import base.Registro;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Livro implements Registro {

    private int id;
    private String titulo;
    private float preco;
    private long dataPublicacao;
    private String autor;
    private int idCategoria;
    private boolean alugado;

    public Livro() {
        this.id = -1;
        this.titulo = "";
        this.preco = 0f;
        this.dataPublicacao = System.currentTimeMillis();
        this.autor = "";
        this.idCategoria = -1;
        this.alugado = false;
    }

    public Livro(String titulo, float preco, long dataPublicacao, String autor, int idCategoria) {
        this();
        this.titulo = titulo;
        this.preco = preco;
        this.dataPublicacao = dataPublicacao;
        this.autor = autor;
        this.idCategoria = idCategoria;
    }

    @Override
    public int getId() { return id; }

    @Override
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public float getPreco() { return preco; }
    public void setPreco(float preco) { this.preco = preco; }

    public long getDataPublicacao() { return dataPublicacao; }
    public void setDataPublicacao(long dataPublicacao) { this.dataPublicacao = dataPublicacao; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public boolean isAlugado() { return alugado; }
    public void setAlugado(boolean alugado) { this.alugado = alugado; }


    private String formatarData(long timestamp) {
        if (timestamp == 0) return "N/A";
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(timestamp));
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);
        dos.writeUTF(titulo);
        dos.writeFloat(preco);
        dos.writeLong(dataPublicacao);
        dos.writeUTF(autor);
        dos.writeInt(idCategoria);
        dos.writeBoolean(alugado);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.titulo = dis.readUTF();
        this.preco = dis.readFloat();
        this.dataPublicacao = dis.readLong();
        this.autor = dis.readUTF();
        this.idCategoria = dis.readInt();
        this.alugado = dis.readBoolean();
    }

    @Override
    public String toString() {
        return "\nID: " + id +
               "\nTÍTULO: " + titulo +
               "\nAUTOR: " + autor +
               "\nPREÇO: R$" + preco +
               "\nDATA DE PUBLICAÇÃO: " + formatarData(dataPublicacao) +
               "\nCATEGORIA ID: " + idCategoria +
               "\nALUGADO: " + (alugado ? "Sim" : "Não") + 
               "\n---------------------------";
    }
}
