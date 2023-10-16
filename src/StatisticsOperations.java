import java.util.List;
import java.util.Scanner;

public class StatisticsOperations {
    // print fuel has been used in a day
    static void getFuelInDay() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a day you want to get total fuel: ");
            String date = scanner.next();
            // check if date format is correct
            if (date.length() == 10) {
                System.out.println("Total fuel consumption in " + date + " is: " + TripCRUD.calculateTotalFuelConsumptionOnDepartureDay(date));
                break;
            }
            System.out.println("Invalid date format, please try again");
        }
    }
    //  print all trips between 2 days
    static void listTripFromDays() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter starting day(YYYY-MM-DD): ");
            String startingDate = scanner.next();
            System.out.print("Enter end day(YYYY-MM-DD): ");
            String endingDate = scanner.next();
            // check if date format is correct
            if (startingDate.length() == 10 && endingDate.length() == 10) {
                TripCRUD.listTripsInDateRange(startingDate,endingDate);
                break;
            }
            System.out.println("Invalid date format, please try again");
        }
    }
    // print all trips in a day
    static void listTripInDay() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a day that you want to print all trip(YYYY-MM-DD): ");
            String date = scanner.next();
            // check if date format is correct
            if (date.length() == 10) {
                TripCRUD.listTripsOnGivenDay(date);
                break;
            }
            System.out.println("Invalid date format, please try again");
        }
    }
    // print total weight of each type of containers
    static void calculateWeightTypeOfAllContainers() {
        // read all containers from file
        List<Port> ports = ContainerCRUD.readContainer();
        double weight1 = 0;
        double weight2 = 0;
        double weight3 = 0;
        double weight4 = 0;
        double weight5 = 0;
        // loop through all ports
        for (Port port : ports) {
            // loop through all containers in each port
            for (Container container: port.getContainers()) {
                // check type of container and add weight to corresponding variable
                switch (container.getType()) {
                    case 1:
                        weight1 = weight1 + container.getWeight();
                        break;
                    case 2:
                        weight2 = weight2 + container.getWeight();
                        break;
                    case 3:
                        weight3 = weight3 + container.getWeight();
                        break;
                    case 4:
                        weight4 = weight4 + container.getWeight();
                        break;
                    case 5:
                        weight5 = weight5 + container.getWeight();
                        break;
                }
            }
        }
        // print out total weight of each type of containers
        System.out.println("All dry storage container weight: " + weight1);
        System.out.println("All open top container weight: " + weight2);
        System.out.println("All open side container weight: " + weight3);
        System.out.println("All refrigerated container weight: " + weight4);
        System.out.println("All liquid container weight: " + weight5);

    }
    // print total ship in a port
    static void listShipInPort() {
        Scanner scanner = new Scanner(System.in);
        // read all vehicles from file
        List<Port> ports = VehicleCRUD.readVehicle();
        PortCRUD.printPorts();
        System.out.print("Select port ID to print ship out: ");
        int portID = scanner.nextInt();
        Port selectedPort = ports.get(portID - 1);
        List<Vehicle> vehiclesInPort = selectedPort.getVehicles();
        // loop through all vehicles in selected port
        int checker = 0;
        for (Vehicle vehicle : vehiclesInPort) {
            if (vehicle.getType() == 1) {
                System.out.println(vehicle.getVehicle_number() + ". " + vehicle.getVehicleName());
                checker++;
            }
        }
        // check if there is no ship in selected port
        if (checker == 0) {
            System.out.println("There is no ship in this port");
        }

    }
}
