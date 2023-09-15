import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose a tab:");
            System.out.println("1. Port");
            System.out.println("2. Container");
            System.out.println("3. Vehicle");
            System.out.println("0. Exit the program");
            System.out.print("Enter tab choice: ");
            int tabChoice = scanner.nextInt();

            if (tabChoice == 0) {
                System.out.println("Goodbye!");
                break;
            }

            switch (tabChoice) {
                case 1:
                    handlePortTab(scanner);
                    break;
                case 2:
                    handleContainerTab(scanner);
                    break;
                case 3:
                    handleVehicleTab(scanner);
                    break;
                default:
                    System.out.println("Invalid tab choice. Please choose 1 for Port or 2 for Container.");
                    break;
            }
        }

        scanner.close();
    }

    private static void handlePortTab(Scanner scanner) {
        int userChoice = 1;

        while (userChoice != 0) {
            System.out.println("Port commands:");
            System.out.println("1. Create port");
            System.out.println("2. Print all ports");
            System.out.println("3. Update port");
            System.out.println("4. Delete port");
            System.out.println("0. Back to main menu");
            System.out.print("Choose your command: ");
            userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    portCRUD.createPort();
                    break;
                case 2:
                    portCRUD.printPorts();
                    break;
                case 3:
                    portCRUD.updatePorts();
                    break;
                case 4:
                    portCRUD.deletePort();
                    break;
                case 0:
                    System.out.println("Returning to main menu.");
                    break;
                default:
                    System.out.println("You entered a wrong command code!");
                    break;
            }
        }
    }

    private static void handleContainerTab(Scanner scanner) {
        int userChoice = 1;

        while (userChoice != 0) {
            System.out.println("Container commands:");
            System.out.println("1. Create container");
            System.out.println("2. Print all containers");
            System.out.println("3. Update container");
            System.out.println("4. Delete container");
            System.out.println("0. Back to main menu");
            System.out.print("Choose your command: ");
            userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    containerCRUD.createContainer();
                    break;
                case 2:
                    containerCRUD.printContainers();
                    break;
                case 3:
                    containerCRUD.updateContainer();
                    break;
                case 4:
                    containerCRUD.deleteContainer();
                    break;
                case 0:
                    System.out.println("Returning to main menu.");
                    break;
                default:
                    System.out.println("You entered a wrong command code!");
                    break;
            }
        }
    }

    private static void handleVehicleTab(Scanner scanner) {
        int userChoice = 1;

        while (userChoice != 0) {
            System.out.println("Vehicle commands:");
            System.out.println("1. Create vehicle");
            System.out.println("2. Print all vehicles");
            System.out.println("3. Update vehicle");
            System.out.println("4. Delete vehicle");
            System.out.println("0. Back to main menu");
            System.out.print("Choose your command: ");
            userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    VehicleCRUD.createVehicle();
                    break;
                case 2:
                    VehicleCRUD.printAllVehicle();
                    break;
                case 3:
                    VehicleCRUD.updateVehicle();
                    break;
                case 4:
                    VehicleCRUD.deleteVehicle();
                    break;
                case 0:
                    System.out.println("Returning to main menu.");
                    break;
                default:
                    System.out.println("You entered a wrong command code!");
                    break;
            }
        }
    }
}
