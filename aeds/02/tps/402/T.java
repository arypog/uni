import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

class Show{
  public String show_id;
  public String title;
  public int release_year;

  public Show( String show_id, String title,int release_year){
    this.show_id = show_id;
    this.title = title;
    this.release_year = release_year;
  }

  public static void leiaShow(Show[] show) throws IOException, ParseException{
    BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream("/tmp/disneyplus.csv"), StandardCharsets.UTF_8));
    file.readLine();
    String linha = "";
    while((linha = file.readLine()) != null){
      String[] divisao= linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
      for (int i = 0; i < divisao.length; i++) {
        divisao[i] = divisao[i].replaceAll("^\"|\"$", "").trim();

        if (divisao[i].isEmpty() && linha.contains(",,")) {
          divisao[i] = ""; 
        }
      }

      int index = Integer.parseInt(divisao[0].substring(1));
      --index;

      int ano = Integer.parseInt(divisao[7]);
      show[index] = new Show(divisao[0], divisao[2], ano);
    }
    file.close();
  }
}

class No{
  int elemento;
  No2 raiz;
  No dir,esq;

  public No(){
    this.elemento = 0;
    this.dir = null;
    this.esq = null;
  }

  public No(int x){
    this.elemento = x;
    this.dir = null;
    this.esq = null;
  }
}

class No2 {
  String elemento;
  No2 esq,dir;

  No2(){
    this.elemento = null;
    this.dir = null;
    this.esq = null;  
  }

  No2(String x){
    this.elemento = x;
    this.dir = null;
    this.esq = null;  
  }
}

class Arvore{
  No raiz;
  public Arvore(){
    this.raiz = null;
  }

  public void construir(){
    inserir(7);
    inserir(3);
    inserir(11);
    inserir(1);
    inserir(5);
    inserir(9);
    inserir(13);
    inserir(0);
    inserir(2);
    inserir(4);
    inserir(6);
    inserir(8);
    inserir(10);
    inserir(12);
    inserir(14);
  }

  public void inserir(int x){
    raiz = inserir(x, raiz);
  }

  private No inserir(int x, No i){
    if(i == null){
      i = new No(x);
    }else if(x < i.elemento){
      i.esq = inserir(x, i.esq);
    }else if(x > i.elemento){
      i.dir = inserir(x, i.dir);
    }
    return i;
  }

  public void inserir(Show i){
    this.raiz = inserir(i,raiz);
  }

  private No inserir(Show i, No n){
    if(n == null){
      n = new No(i.release_year % 15);
      n.raiz = new No2(i.title);
    }else if(i.release_year % 15 < n.elemento){
      n.esq = inserir(i, n.esq);
    }else if(i.release_year % 15 > n.elemento){
      n.dir = inserir(i, n.dir);
    }else if(i.release_year % 15 == n.elemento){
      inserirNo2(i.title, n);
    }
    return n;
  }

  private void inserirNo2(String nome, No i){
    i.raiz = inserirNo2(nome, i.raiz);
  }

  private No2 inserirNo2(String x, No2 i){
    if(i == null){
      i = new No2(x);
    }else if(x.compareTo(i.elemento) < 0){
      i.esq = inserirNo2(x, i.esq);
    }else if(x.compareTo(i.elemento) > 0){
      i.dir = inserirNo2(x, i.dir);
    }
    return i;
  }

  public boolean caminharPre(String x) {
    System.out.print("raiz ");
    return caminharPre(x, raiz);
  }

  private boolean caminharPre(String x, No i) {
    if (i == null) {
      return false;
    }


    if (pesquisar(x, i.raiz)) {
      return true;
    }


    System.out.print(" ESQ ");
    if (caminharPre(x, i.esq)) {
      return true;
    }


    System.out.print(" DIR ");
    return caminharPre(x, i.dir);
  }

  private boolean pesquisar(String x, No2 i) {
    if (i == null) {
      return false;
    }

    if (x.equals(i.elemento)) {

      return true;
    }

    if (x.compareTo(i.elemento) < 0) {
      System.out.print("esq ");
      return pesquisar(x, i.esq);
    } else {
      System.out.print("dir ");
      return pesquisar(x, i.dir);
    }
  }
}

public class T{
  static Show[] show = new Show[1368];

  public static void main(String[] args) throws IOException, ParseException {
    Scanner sc = new Scanner(System.in);
    Show.leiaShow(show);
    String linha;

    Arvore arvore = new Arvore();
    arvore.construir();
    
    while (!(linha = sc.nextLine()).equals("FIM")) {
      int index = Integer.parseInt(linha.substring(1))-1;
      arvore.inserir(show[index]);
    }

    while (!(linha = sc.nextLine()).equals("FIM")) {
      System.out.println(arvore.caminharPre(linha) ? " SIM" : " NAO");
    }

    sc.close();
  }
}
