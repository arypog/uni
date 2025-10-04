package model;

import base.Registro;
import java.io.*;

public class Leitor implements Registro {

    private int id;
    private String nome;
    private String email;
    private String telefone;

    public Leitor() {
        this.id = -1;
        this.nome = "";
        this.email = "";
        this.telefone = "";
    }

    public Leitor(String nome, String email, String telefone) {
        this();
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    @Override
    public int getId() { return id; }

    @Override
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeUTF(email);
        dos.writeUTF(telefone);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.email = dis.readUTF();
        this.telefone = dis.readUTF();
    }

    @Override
    public String toString() {
        return "ID: " + id +
               "\nNOME: " + nome +
               "\nEMAIL: " + email +
               "\nTELEFONE: " + telefone + 
               "\n---------------------------";
    }
}
