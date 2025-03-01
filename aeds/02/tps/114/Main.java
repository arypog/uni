/*
 * Faça um programa que leia um número inteiro n indicando o número de valores 
 * reais que devem ser lidos e salvos sequencialmente em um arquivo texto. Após 
 * a leitura dos valores, devemos fechar o arquivo. 
 * Em seguida, reabri-lo e 
 * fazer a leitura de trás para frente usando os métodos getFilePointer e seek 
 * da classe RandomAccessFile e mostre todos os valores lidos na tela. 
 * Nessa 
 * questão, você não pode usar, arrays, strings ou qualquer estrutura de dados. 
 * A entrada padrão é composta por um número inteiro n e mais n números reais. 
 * A saída padrão corresponde a n números reais mostrados um por linha de saída.
 */
import java.util.Scanner;
import java.io.RandomAccessFile;

class Main {
    static void writeFile(Scanner sc, int n) throws Exception {
        RandomAccessFile writer = new RandomAccessFile("file.file", "rw");
        for(int i = 0; i < n; i++) {
            writer.writeDouble(sc.nextDouble());;
        }
        writer.close();
    }

    static void readFile(Scanner sc, int n) throws Exception {
        RandomAccessFile reader = new RandomAccessFile("file.file", "r");
        long end = reader.length();
        for (int i = 0; i < n; i++) {
            reader.seek(end - (i + 1) * 8);
            double num = reader.readDouble();

            if (num == (long) num) System.out.println((long) num);
            else System.out.println(num);
        }
        reader.close();
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        writeFile(sc, n);        
        readFile(sc, n);
        sc.close();
    }
}