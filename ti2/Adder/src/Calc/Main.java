package Calc;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println(Operations.AddTwoIntegers(sc.nextInt(), sc.nextInt()));
		sc.close();
	}
}
