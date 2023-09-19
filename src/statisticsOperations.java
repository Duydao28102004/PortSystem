import java.util.List;
import java.util.Scanner;

public class StatisticsOperations {
    static void getFuelInDay() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a day you want to get total fuel: ");
            String date = scanner.next();
            if (date.length() == 10) {
                System.out.println("Total fuel consumption in " + date + " is: " + TripCRUD.calculateTotalFuelConsumptionOnDepartureDay(date));
                break;
            }
            System.out.println("Invalid date format, please try again");
        }
    }
    static void listTripFromDays() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter starting day: ");
            String startingDate = scanner.next();
            System.out.print("Enter end day: ");
            String endingDate = scanner.next();
            if (startingDate.length() == 10 && endingDate.length() == 10) {
                TripCRUD.listTripsInDateRange(startingDate,endingDate);
                break;
            }
            System.out.println("Invalid date format, please try again");
        }
    }
    static void listTripInDay() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a day that you want to print all trip: ");
            String date = scanner.next();
            if (date.length() == 10) {
                TripCRUD.listTripsOnGivenDay(date);
                break;
            }
            System.out.println("Invalid date format, please try again");
        }
    }
    static void calculateWeightTypeOfAllContainers() {
        List<Port> ports = ContainerCRUD.readContainer();
        double weight1 = 0;
        double weight2 = 0;
        double weight3 = 0;
        double weight4 = 0;
        double weight5 = 0;
        for (Port port : ports) {
            for (Container container: port.getContainers()) {
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
        System.out.println("All dry storage container weight: " + weight1);
        System.out.println("All open top container weight: " + weight2);
        System.out.println("All open side container weight: " + weight3);
        System.out.println("All refrigerated container weight: " + weight4);
        System.out.println("All liquid container weight: " + weight5);

    }
    static void listShipInPort() {
        Scanner scanner = new Scanner(System.in);
        List<Port> ports = VehicleCRUD.readVehicle();
        PortCRUD.printPorts();
        System.out.print("Select port ID to print ship out: ");
        int portID = scanner.nextInt();
        Port selectedPort = ports.get(portID - 1);
        List<Vehicle> vehiclesInPort = selectedPort.getVehicles();
        int checker = 0;
        for (Vehicle vehicle : vehiclesInPort) {
            if (vehicle.getType() == 1) {
                System.out.println(vehicle.getVehicle_number() + ". " + vehicle.getVehicleName());
                checker++;
            }
        }
        if (checker == 0) {
            System.out.println("There is no ship in this port");
        }

    }
}
