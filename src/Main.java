import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int userChoice = 1;
        Scanner scanner = new Scanner(System.in);
        while (userChoice != 0) {
            System.out.println("Port command list");
            System.out.println("1. Create port");
            System.out.println("2. Print all ports");
            System.out.println("3. Update port");
            System.out.println("4. Delete port");
            System.out.println("0. Exit the program");
            System.out.print("Choose your command: ");
            userChoice = scanner.nextInt();
            if (userChoice == 1) {
                portCRUD.createPort();
            } else if (userChoice == 2) {
                portCRUD.printPorts();
            } else if (userChoice == 3) {
                portCRUD.updatePorts();
            } else if (userChoice == 4) {
                portCRUD.deletePort();
            } else if (userChoice == 0) {
                System.out.println("goodbye!");
            } else {
                System.out.println("You enter wrong command code!");
            }
        }
        scanner.close();
    }
}