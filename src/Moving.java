import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Moving {
    static void createTrip() {
        List<Port> ports = VehicleCRUD.readVehicle();
        Scanner scanner = new Scanner(System.in);
        portCRUD.printPorts();
        System.out.print("Enter starting port ID: ");
        int startingPortID = scanner.nextInt();

        // Check if the starting port exists
        Port startingPort = containerCRUD.portExist(ports, startingPortID);
        if (startingPort == null) {
            System.out.println("Port with ID " + startingPortID + " was not found.");
            return;
        }

        System.out.print("Enter destination port ID: ");
        int destinationPortID = scanner.nextInt();

        // Check if the destination port exists
        Port destinationPort = containerCRUD.portExist(ports, destinationPortID);
        if (destinationPort == null) {
            System.out.println("Port with ID " + destinationPortID + " was not found.");
            return;
        }

        List<Vehicle> vehiclesInStartingPort = startingPort.getVehicles();
        if (vehiclesInStartingPort.isEmpty()) {
            System.out.println("No vehicles found in port " + startingPort.getPortName());
            return;
        }

        for (Vehicle vehicle : vehiclesInStartingPort) {
            System.out.println("Vehicle ID: " + vehicle.getVehicle_number());
            System.out.println("Vehicle Name: " + vehicle.getVehicleName());
            System.out.println("Fuel Capacity: " + vehicle.getFuelCapacity());
            System.out.println("Carrying Capacity: " + vehicle.getCarryingCapacity());
            System.out.println("Type: " + vehicle.getVehicleTypeName());
            System.out.println("----------------------------------");
        }

        System.out.print("Enter vehicle ID you want to use: ");
        int selectedVehicleID = scanner.nextInt();

        // Check if the selected vehicle exists
        if (selectedVehicleID < 1 || selectedVehicleID > vehiclesInStartingPort.size()) {
            System.out.println("Invalid vehicle ID.");
            return;
        }

        Vehicle selectedVehicle = vehiclesInStartingPort.get(selectedVehicleID - 1);

        // Get a list of containers in the starting port
        List<Container> containersInStartingPort = startingPort.getContainers();

        if (containersInStartingPort.isEmpty()) {
            System.out.println("No containers found in port " + startingPort.getPortName());
            return;
        }
        // Now you can use 'containersInStartingPort' for further processing
        containerCRUD.printContainersInPort(containersInStartingPort);
        // Create a list to hold the selected containers
        List<Container> selectedContainers = new ArrayList<>();



    }
    public static void main(String[] args) {
        createTrip();
    }
}
