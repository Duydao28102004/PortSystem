import java.util.Scanner;

public class User {
    static void portManager(int userPortID) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Choose a tab:");
                System.out.println("1. Port");
                System.out.println("2. Container");
                System.out.println("3. Vehicle");
                System.out.println("4. Trip");
                System.out.println("5. Statistic operation");
                System.out.println("0. Exit the program");
                System.out.print("Enter tab choice: ");
                int tabChoice = scanner.nextInt();

                if (tabChoice == 0) {
                    System.out.println("Goodbye!");
                    break;
                }

                switch (tabChoice) {
                    case 1:
                        handlePortTabManager(scanner, userPortID);
                        break;
                    case 2:
                        handleContainerTabManager(scanner, userPortID);
                        break;
                    case 3:
                        handleVehicleTabManager(scanner, userPortID);
                        break;
                    case 4:
                        handleTripTabManager(scanner, userPortID);
                        break;
                    case 5:
                        handleStatisticOperationTab(scanner);
                        break;
                    default:
                        System.out.println("Invalid tab choice. Please try again.");
                        break;
                }
            }
            scanner.close();
    }
    static void admin() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose a tab:");
            System.out.println("1. Port");
            System.out.println("2. Container");
            System.out.println("3. Vehicle");
            System.out.println("4. Trip");
            System.out.println("5. Statistic operation");
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
                case 4:
                    handleTripTab(scanner);
                    break;
                case 5:
                    handleStatisticOperationTab(scanner);
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
                    PortCRUD.createPort();
                    break;
                case 2:
                    PortCRUD.printPorts();
                    break;
                case 3:
                    PortCRUD.updatePorts();
                    break;
                case 4:
                    PortCRUD.deletePort();
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
    private static void handlePortTabManager(Scanner scanner, int userPortID) {
        int userChoice = 1;

        while (userChoice != 0) {
            System.out.println("Port commands:");
            System.out.println("1. Update port");
            System.out.println("0. Back to main menu");
            System.out.print("Choose your command: ");
            userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    PortCRUD.updateSpecificPort(userPortID);
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
                    ContainerCRUD.createContainer();
                    break;
                case 2:
                    ContainerCRUD.printContainers();
                    break;
                case 3:
                    ContainerCRUD.updateContainer();
                    break;
                case 4:
                    ContainerCRUD.deleteContainer();
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
    private static void handleContainerTabManager(Scanner scanner, int userPortID) {
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
                    ContainerCRUD.createContainerInPort(userPortID);
                    break;
                case 2:
                    ContainerCRUD.printContainersWithPortID(userPortID);
                    break;
                case 3:
                    ContainerCRUD.updateContainersInSpecificPort(userPortID);
                    break;
                case 4:
                    ContainerCRUD.deleteContainerInSpecificPort(userPortID);
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
            System.out.println("5. Refuel Vehicle");
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
                case 5:
                    VehicleCRUD.refuelVehicle();
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
    private static void handleVehicleTabManager(Scanner scanner, int userPortID) {
        int userChoice = 1;

        while (userChoice != 0) {
            System.out.println("Vehicle commands:");
            System.out.println("1. Create vehicle");
            System.out.println("2. Print all vehicles");
            System.out.println("3. Update vehicle");
            System.out.println("4. Delete vehicle");
            System.out.println("5. Refuel Vehicle");
            System.out.println("0. Back to main menu");
            System.out.print("Choose your command: ");
            userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    VehicleCRUD.createVehicleInPort(userPortID);
                    break;
                case 2:
                    VehicleCRUD.printVehiclesWithPortID(userPortID);
                    break;
                case 3:
                    VehicleCRUD.updateVehicleInPort(userPortID);
                    break;
                case 4:
                    VehicleCRUD.deleteVehicleInPort(userPortID);
                    break;
                case 5:
                    VehicleCRUD.refuelVehicle();
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

    private static void handleTripTab(Scanner scanner) {
        int userChoice = 1;

        while (userChoice != 0) {
            System.out.println("Trip commands:");
            System.out.println("1. Create Trip");
            System.out.println("2. Print all trip");
            System.out.println("3. Delete trip");
            System.out.println("4. Approve trip");
            System.out.println("0. Back to main menu");
            System.out.print("Choose your command: ");
            userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    TripCRUD.createTrip(0);
                    break;
                case 2:
                    TripCRUD.printAllTrip();
                    break;
                case 3:
                    TripCRUD.deleteTrip();
                    break;
                case 4:
                    TripCRUD.approvedTrip();
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
    private static void handleTripTabManager(Scanner scanner, int userPortID) {
        int userChoice = 1;

        while (userChoice != 0) {
            System.out.println("Trip commands:");
            System.out.println("1. Create Trip");
            System.out.println("2. Print all trip");
            System.out.println("0. Back to main menu");
            System.out.print("Choose your command: ");
            userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    TripCRUD.createTrip(userPortID);
                    break;
                case 2:
                    TripCRUD.printTripsFromStartingPort(userPortID);
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
    private static void handleStatisticOperationTab(Scanner scanner) {
        int userChoice = 1;

        while (userChoice != 0) {
            System.out.println("Statistic operation commands:");
            System.out.println("1. Get fuel in a day");
            System.out.println("2. Calculate how much weight of each type of all containers");
            System.out.println("3. List all the ships in a port");
            System.out.println("4. List all the trips in a given day");
            System.out.println("5. List all the trips from day A to day B");
            System.out.println("0. Back to main menu");
            System.out.print("Choose your command: ");
            userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    StatisticsOperations.getFuelInDay();
                    break;
                case 2:
                    StatisticsOperations.calculateWeightTypeOfAllContainers();
                    break;
                case 3:
                    StatisticsOperations.listShipInPort();
                    break;
                case 4:
                    StatisticsOperations.listTripInDay();
                    break;
                case 5:
                    StatisticsOperations.listTripFromDays();
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
