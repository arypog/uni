
import dao.PowerpuffGirlDAO;
import model.PowerpuffGirl;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final PowerpuffGirlDAO dao = new PowerpuffGirlDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int option;

        do {
            showMenu();
            option = readOption();

            switch (option) {
	            case 1:
	                listGirls();
	                break;
	            case 2:
	                addGirl();
	                break;
	            case 3:
	                updateGirl();
	                break;
	            case 4:
	                deleteGirl();
	                break;
	            case 5:
	                System.out.println("Exiting... See you!");
	                break;
	            default:
	                System.out.println("Invalid option. Try again!");
            }
        } while (option != 5);
    }

    private static void showMenu() {
        System.out.println("\n=== Powerpuff Girls CRUD ===");
        System.out.println("1 - List all");
        System.out.println("2 - Add new");
        System.out.println("3 - Update");
        System.out.println("4 - Delete");
        System.out.println("5 - Exit");
        System.out.print("Choose an option: ");
    }

    private static int readOption() {
        return scanner.hasNextInt() ? scanner.nextInt() : 0;
    }

    private static void listGirls() {
        List<PowerpuffGirl> girls = dao.getAllGirls();
        System.out.println("\n=== List of Powerpuff Girls ===");
        for (PowerpuffGirl g : girls) {
            System.out.println(g.getId() + " - " + g.getName() + " | Superpower: " + g.getSuperpower() + " | Age: " + g.getAge());
        }
    }

    private static void addGirl() {
        scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Superpower: ");
        String superpower = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();

        PowerpuffGirl girl = new PowerpuffGirl(name, superpower, age);
        dao.addGirl(girl);
        System.out.println("Powerpuff Girl added successfully!");
    }

    private static void updateGirl() {
        System.out.print("ID of the Powerpuff Girl to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("New name: ");
        String name = scanner.nextLine();
        System.out.print("New superpower: ");
        String superpower = scanner.nextLine();
        System.out.print("New age: ");
        int age = scanner.nextInt();

        PowerpuffGirl girl = new PowerpuffGirl(id, name, superpower, age);
        dao.updateGirl(girl);
        System.out.println("Powerpuff Girl updated successfully!");
    }

    private static void deleteGirl() {
        System.out.print("ID of the Powerpuff Girl to delete: ");
        int id = scanner.nextInt();
        dao.deleteGirl(id);
        System.out.println("Powerpuff Girl deleted successfully!");
    }
}
