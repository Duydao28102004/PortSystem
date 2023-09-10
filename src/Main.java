import java.util.Scanner;

public abstract class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int userChoice;

        System.out.println("Choose an option:");
        System.out.println("1. Port Operations");
        System.out.println("2. Container Operations");
        System.out.println("0. Exit the program");
        System.out.print("Enter your choice: ");
        userChoice = scanner.nextInt();

        if (userChoice == 1) {
            handlePortOperations();
        } else if (userChoice == 2) {
            handleContainerOperation(); // Instantiate and run containerCRUD
        } else if (userChoice == 0) {
            System.out.println("Exit the program");
        } else {
            System.out.println("Invalid choice. Please enter 0, 1, or 2.");
        }

        scanner.close();
    }

    private static void handlePortOperations() {
        Scanner scanner = new Scanner(System.in);
        int userChoice;

        while (true) {
            System.out.println("Port command list");
            System.out.println("1. Create port");
            System.out.println("2. Print all ports");
            System.out.println("3. Update port");
            System.out.println("4. Delete port");
            System.out.println("0. Exit Port Operations");
            System.out.print("Choose your command: ");
            userChoice = scanner.nextInt();

            if (userChoice == 0) {
                System.out.println("Exiting Port Operations");
                break; // Exit the loop and return to the main menu
            } else if (userChoice == 1) {
                portCRUD.createPort();
            } else if (userChoice == 2) {
                portCRUD.printPorts();
            } else if (userChoice == 3) {
                portCRUD.updatePorts();
            } else if (userChoice == 4) {
                portCRUD.deletePort();
            } else {
                System.out.println("You entered a wrong command code!");
            }
        }

        scanner.close();
    }

    public static void handleContainerOperation(){
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("1. Create Container");
            System.out.println("2. Read Containers");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Stop the process");
            System.out.print("Enter a number to choose: ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                containerCRUD.createContainer();
            } else if (choice.equals("2")) {
                containerCRUD.readContainer();
            } else if (choice.equals("3")) {
                containerCRUD.updateContainer();
            } else if (choice.equals("4")) {
                containerCRUD.deleteContainer();
            } else if (choice.equals("5")) {
                System.out.println("End the process");
            }
            else {
                System.out.println("Invalid choice");
                System.out.println("Press any key to continue");
                scanner.nextLine();
                handleContainerOperation();
            }
        } catch (Exception ex) {
            System.out.print("Error");
        }
    }

}
