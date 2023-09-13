import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        int userChoice = 1;
//        Scanner scanner = new Scanner(System.in);
//
//        while (userChoice != 0) {
//            System.out.println("Port command list");
//            System.out.println("1. Create port");
//            System.out.println("2. Print all ports");
//            System.out.println("3. Update port");
//            System.out.println("4. Delete port");
//            System.out.println("5. Create container");
//            System.out.println("6. Print all containers");
//            System.out.println("7. Update container");
//            System.out.println("8. Delete container");
//            System.out.println("0. Exit the program");
//            System.out.print("Choose your command: ");
//            userChoice = scanner.nextInt();
//            System.out.println("   *********   ");
//
//            switch (userChoice) {
//                case 1:
//                    portCRUD.createPort();
//                    break;
//                case 2:
//                    portCRUD.printPorts();
//                    break;
//                case 3:
//                    portCRUD.updatePorts();
//                    break;
//                case 4:
//                    portCRUD.deletePort();
//                    break;
//                case 5:
//                    containerCRUD.createContainer();
//                    break;
//                case 6:
//                    containerCRUD.printSelectedContainers();
//                    break;
//                case 7:
//                    containerCRUD.updateContainer();
//                    break;
//                case 8:
//                    containerCRUD.printSelectedContainers();
//                    containerCRUD.deleteContainer();
//                    break;
//                case 0:
//                    System.out.println("Goodbye!");
//                    break;
//                default:
//                    System.out.println("You entered a wrong command code!");
//                    break;
//            }
//
//            if (userChoice != 0) {
//                System.out.print("Do you want to continue? (1 for Yes, 0 for No): ");
//                int continueChoice = scanner.nextInt();
//                if (continueChoice != 1) {
//                    userChoice = 0;
//                    System.out.println("Goodbye!");
//                }
//            }
//        }
//        scanner.close();
        VehicleCRUD.createVehicle();
    }
}