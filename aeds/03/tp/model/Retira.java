package model;

import base.Registro;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Retira implements Registro {

    private int id;
    private int idLivro;
    private int idLeitor;
    private long dataEmprestimo;

    public Retira() {
        this.id = -1;
        this.idLivro = -1;
        this.idLeitor = -1;
        this.dataEmprestimo = System.currentTimeMillis();
    }

    public Retira(int idLivro, int idLeitor, long dataEmprestimo) {
        this();
        this.idLivro = idLivro;
        this.idLeitor = idLeitor;
        this.dataEmprestimo = dataEmprestimo;
    }

    @Override
    public int getId() { return id; }

    @Override
    public void setId(int id) { this.id = id; }

    public int getIdLivro() { return idLivro; }
    public void setIdLivro(int idLivro) { this.idLivro = idLivro; }

    public int getIdLeitor() { return idLeitor; }
    public void setIdLeitor(int idLeitor) { this.idLeitor = idLeitor; }

    public long getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(long dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    private String formatarData(long timestamp) {
        if (timestamp == 0) return "N/A";
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(timestamp));
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);
        dos.writeInt(idLivro);
        dos.writeInt(idLeitor);
        dos.writeLong(dataEmprestimo);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.idLivro = dis.readInt();
        this.idLeitor = dis.readInt();
        this.dataEmprestimo = dis.readLong();
    }

    @Override
    public String toString() {
        return "\nID: " + id +
               "\nLIVRO ID: " + idLivro +
               "\nLEITOR ID: " + idLeitor +
               "\nDATA EMPRÉSTIMO: " + formatarData(dataEmprestimo) +
               "\n---------------------------";
    }
}
