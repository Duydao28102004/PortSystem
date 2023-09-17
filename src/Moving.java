import java.util.ArrayList;
import java.util.Iterator;
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
        // Check if destination port available
        if (!isDestinationPortAvailable(destinationPort, selectedVehicle)) {
            System.out.println("The destination port do not have enough space");
            return;
        }
        // Get a list of containers in the starting port suitable for the type of vehicle
        List<Container> compatibleContainersInStartingPort = new ArrayList<>();

        for (Container container : startingPort.getContainers()) {
            if (isContainerCompatibleWithVehicle(container, selectedVehicle)) {
                compatibleContainersInStartingPort.add(container);
            }
        }

        if (compatibleContainersInStartingPort.isEmpty()) {
            System.out.println("There are no suitable containers for the selected vehicle.");
            return;
        }

        // Create a list to hold the selected containers
        List<Container> selectedContainers = new ArrayList<>();
        double totalWeight = 0;
        double remainingWeight = selectedVehicle.getCarryingCapacity();
        while (true) {
            containerCRUD.printContainersInPort(compatibleContainersInStartingPort);
            System.out.print("Enter container ID you want to load on the vehicle (0 to exit): ");
            int selectedContainerID = scanner.nextInt();

            if (selectedContainerID == 0) {
                break; // Exit the loop if the user enters 0
            }

            // Find the selected container by ID and move it to the selectedContainers list
            Container selectedContainer = null;
            Iterator<Container> iterator = compatibleContainersInStartingPort.iterator();
            while (iterator.hasNext()) {
                Container container = iterator.next();
                if (container.getC_number() == selectedContainerID) {
                    selectedContainer = container;
                    if (selectedContainer.getWeight() <= remainingWeight) {
                        iterator.remove(); // Remove the selected container from containersInStartingPort
                        selectedContainers.add(selectedContainer); // Add it to the selectedContainers list
                        totalWeight = totalWeight + selectedContainer.getWeight();// Update the total weight
                        remainingWeight = remainingWeight - selectedContainer.getWeight();
                        System.out.println("Current Total Weight: " + totalWeight);
                        System.out.println("Remaining Weight Capacity: " + (selectedVehicle.getCarryingCapacity() - totalWeight));
                        break;
                    }
                    System.out.println("The container is overweight");
                }
            }
            if (selectedContainer == null) {
                System.out.println("Container not found");
            }
        }
        double totalFuelConsumption = totalFuelConsumptionForTrip(selectedContainers,startingPort,destinationPort,selectedVehicle);
        System.out.println(selectedContainers.size());
        System.out.println(totalFuelConsumption);
        // Now you have a list of compatible containers in 'selectedContainers'
    }


    static boolean isContainerCompatibleWithVehicle(Container container, Vehicle vehicle) {
        int containerType = container.getType();
        int vehicleType = vehicle.getType();

        if (vehicleType == 1) {
            // Ships can carry all container types
            return true;
        } else if (vehicleType == 2) {
            // Basic trucks can carry dry storage, open top, and open side containers
            return containerType >= 1 && containerType <= 3;
        } else if (vehicleType == 3) {
            // Reefer trucks can carry refrigerated containers
            return containerType == 4;
        } else if (vehicleType == 4) {
            // Tanker trucks can carry liquid containers
            return containerType == 5;
        } else {
            // Unknown vehicle type, not compatible with any containers
            return false;
        }
    }

    static boolean isDestinationPortAvailable(Port destinationPort, Vehicle selectedVehicle) {
        int currentContainerNum = 0;
        for (Container container : destinationPort.getContainers()) {
            currentContainerNum++;
        }
        int remainingContainerSlot = destinationPort.getStoringCap() - currentContainerNum;
        if (remainingContainerSlot == 0) {
            return false;
        }
        if (selectedVehicle.getType() != 1) {
            int currentVehicleNum = 0;
            for (Vehicle vehicle : destinationPort.getVehicles()) {
                currentVehicleNum++;
            }
            int remainingVehicleSlot = destinationPort.getLandingCap() - currentVehicleNum;
            if (remainingVehicleSlot == 0) {
                return false;
            }
        }
        return true;
    }

    static double totalFuelConsumptionForTrip(List<Container> selectedContainers, Port startingPort, Port destinationPort, Vehicle selectedVehicle) {
        double fuelConsumptionPerKm = 0;

        // Initialize fuel consumption based on vehicle type
        if (selectedVehicle.getType() == 1) {
            // For ships (type 1), set an initial value
            fuelConsumptionPerKm = 1.0; // You can adjust this value as needed
        } else {
            // For other vehicle types (types 2 to 4), set a different initial value
            fuelConsumptionPerKm = 2.0; // You can adjust this value as needed
        }

        for (Container container : selectedContainers) {
            System.out.println(container.getContainerTypeName());
            switch (container.getType()) {
                case 1:
                    fuelConsumptionPerKm += 3.5;
                    break;
                case 2:
                    fuelConsumptionPerKm += 2.8;
                    break;
                case 3:
                    fuelConsumptionPerKm += 2.7;
                    break;
                case 4:
                    fuelConsumptionPerKm += 4.5;
                    break;
                case 5:
                    fuelConsumptionPerKm += 4.8;
                    break;
            }
        }

        double distanceBetweenPort = Math.sqrt(Math.pow((destinationPort.getLatitude() - startingPort.getLatitude()), 2) + Math.pow((destinationPort.getLongitude() - startingPort.getLongitude()), 2));
        return distanceBetweenPort * fuelConsumptionPerKm;
    }

    public static void main(String[] args) {
        createTrip();
    }
}
