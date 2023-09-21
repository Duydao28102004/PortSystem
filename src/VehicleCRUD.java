import java.io.*;
import java.util.*;


public class VehicleCRUD {
    //    create vehicle
    static void createVehicle() {
        List<Port> ports = readVehicle();
        Scanner scanner = new Scanner(System.in);
        int vehicleID = autoGenerateVehicleID();
        // ask user for vehicle information before create it
        System.out.print("Enter vehicle name: ");
        String vehicleName = scanner.next();
        System.out.print("Enter vehicle fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        System.out.print("Enter vehicle carrying capacity:");
        double carryingCapacity = scanner.nextDouble();
        System.out.println("Choose vehicle type:");
        System.out.println("1. SHIP");
        System.out.println("2. BASIC_TRUCK");
        System.out.println("3. REEFER_TRUCK");
        System.out.println("4. LIQUID_TRUCK");
        System.out.print("Enter vehicle type (1 -> 4): ");
        int type = scanner.nextInt();
        // print all available ports with free slot for container
        System.out.println("Available ports:");
        int checker = 0;
        if (type == 2 || type == 3 || type == 4) {
            for (Port port : ports) {
                int truckCount = 0;
                for (Vehicle vehicle : port.getVehicles()) {
                    if (vehicle.getType() == 2 || vehicle.getType() == 3 || vehicle.getType() == 4) {
                        truckCount++;
                    }
                }
                if (truckCount < port.getLandingCap()) {
                    System.out.println("Port ID: " + port.getP_number() + "-" + port.getPortName());
                    checker++;
                }
            }
            if (checker == 0) {
                // print notification and exit when the system can't find available port for container
                System.out.println("No available ports for vehicle.");
                return;
            }
        } else if (type == 1) {
            for (Port port : ports) {
                System.out.println("Port ID: " + port.getP_number() + "-" + port.getPortName());
            }
        }
        // enter port ID where user want to store the container
        System.out.print("Enter port ID where vehicle is located: ");
        int tempPortID = scanner.nextInt();
        int portID = tempPortID - 1;
        Vehicle vehicle = new Vehicle(vehicleID, vehicleName, 0.0, fuelCapacity, carryingCapacity, 0, type);
        ports.get(portID).addVehicle(vehicle);
        writeVehiclesBackToFile(ports);
    }

    // create vehicle in specific port
    static void createVehicleInPort(int PortID) {
        List<Port> ports = readVehicle();
        Scanner scanner = new Scanner(System.in);
        int vehicleID = autoGenerateVehicleID();
        // ask user for vehicle information before create it
        System.out.print("Enter vehicle name: ");
        String vehicleName = scanner.next();
        System.out.print("Enter vehicle fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        System.out.print("Enter vehicle carrying capacity:");
        double carryingCapacity = scanner.nextDouble();
        System.out.println("Choose vehicle type:");
        System.out.println("1. SHIP");
        System.out.println("2. BASIC_TRUCK");
        System.out.println("3. REEFER_TRUCK");
        System.out.println("4. LIQUID_TRUCK");
        System.out.print("Enter vehicle type (1 -> 4): ");
        int type = scanner.nextInt();
        int checker = 0;
        if (type == 2 || type == 3 || type == 4) {
                int truckCount = 0;
                for (Vehicle vehicle : ports.get(PortID - 1).getVehicles()) {
                    if (vehicle.getType() == 2 || vehicle.getType() == 3 || vehicle.getType() == 4) {
                        truckCount++;
                    }
                }
                if (truckCount < ports.get(PortID - 1).getLandingCap()) {
                    checker++;
                }
            if (checker == 0) {
                // print notification and exit when the system can't find available port for container
                System.out.println("No available ports for vehicle.");
                return;
            }
        }
        // enter port ID where user want to store the container
        Vehicle vehicle = new Vehicle(vehicleID, vehicleName, 0.0, fuelCapacity, carryingCapacity, 0, type);
        ports.get(PortID - 1).addVehicle(vehicle);
        writeVehiclesBackToFile(ports);
    }
    // auto generate vehicle ID
    static int autoGenerateVehicleID() {
        int vehicleID = 1;
        // Read the file to find the highest ID
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/vehicle_data.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    int id = Integer.parseInt(parts[0]);
                    if (id >= vehicleID) {
                        vehicleID = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            // If the file doesn't exist, ignore the error and return the default ID
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return vehicleID;
    }
    // this method is used to print a vehicle list
    static void printListOfVehicle(List<Port> ports) {
        List<Vehicle> allVehicles = new ArrayList<>();
        // Get all vehicles from all ports
        for (Port port : ports) {
            allVehicles.addAll(port.getVehicles());
        }
        // Sort the vehicles by ID
        Collections.sort(allVehicles, Comparator.comparingInt(Vehicle::getVehicle_number));
        // Print the list of vehicles
        for (Vehicle vehicle : allVehicles) {
            System.out.println("Vehicle ID: " + vehicle.getVehicle_number());
            System.out.println("Vehicle Name: " + vehicle.getVehicleName());
            System.out.println("Fuel Capacity: " + vehicle.getFuelCapacity());
            System.out.println("Carrying Capacity: " + vehicle.getCarryingCapacity());
            System.out.println("Type: " + vehicle.getVehicleTypeName());
            // Find the port where the vehicle is located
            for (Port port : ports) {
                if (port.getVehicles().contains(vehicle)) {
                    System.out.println("Located at Port ID: " + port.getP_number() + "-" + port.getPortName());
                    break; // Stop searching for the port once found
                }
            }
            System.out.println("----------------------------------");
        }
    }
    // print all vehicle
    static void printAllVehicle() {
        List<Port> ports = readVehicle();
        printListOfVehicle(ports);
    }
    // print vehicle in specific port with port ID
    static void printVehiclesWithPortID(int PortID) {
        List<Port> ports = readVehicle();
        for (Vehicle vehicle : ports.get(PortID - 1).getVehicles()) {
            System.out.println("Vehicle ID: " + vehicle.getVehicle_number());
            System.out.println("Vehicle Name: " + vehicle.getVehicleName());
            System.out.println("Fuel Capacity: " + vehicle.getFuelCapacity());
            System.out.println("Carrying Capacity: " + vehicle.getCarryingCapacity());
            System.out.println("Type: " + vehicle.getVehicleTypeName());
            System.out.println("----------------------------------");
        }
    }
    // print vehicle in specific port
    static void printVehiclesInPort(Port selectedPort) {
        List<Vehicle> vehicles = selectedPort.getVehicles();

        // check if there is no vehicle in selected port
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found in port " + selectedPort.getPortName());
            return;
        }

        // print all vehicles in selected port
        System.out.println("Vehicles in Port " + selectedPort.getPortName() + ":");
        for (Vehicle vehicle : vehicles) {
            System.out.println("Vehicle ID: " + vehicle.getVehicle_number());
            System.out.println("Vehicle Name: " + vehicle.getVehicleName());
            System.out.println("Fuel Capacity: " + vehicle.getFuelCapacity());
            System.out.println("Carrying Capacity: " + vehicle.getCarryingCapacity());
            System.out.println("Type: " + vehicle.getVehicleTypeName());
            System.out.println("----------------------------------");
        }
    }

    // update vehicle
    static void updateVehicle() {
        List<Port> ports = readVehicle();
        Scanner scanner = new Scanner(System.in);
        printAllVehicle();
        // ask user for vehicle ID they want to update
        System.out.print("Enter the ID of the vehicle you want to update: ");
        int vehicleID = scanner.nextInt();
        updateVehicleWithGivenInfo(ports, vehicleID);
    }

    // update vehicle in specific port with given port ID
    static void updateVehicleInPort(int PortID) {
        List<Port> ports = readVehicle();
        Scanner scanner = new Scanner(System.in);

        printVehiclesInPort(ports.get(PortID - 1));

        // ask user for vehicle ID they want to update
        System.out.print("Enter the ID of the vehicle you want to update: ");
        int vehicleID = scanner.nextInt();
        updateVehicleWithGivenInfo(ports, vehicleID);
    }
    // update vehicle with given vehicle ID
    static void updateVehicleWithGivenInfo(List<Port> ports, int vehicleID) {
        Scanner scanner = new Scanner(System.in);
        // Find the vehicle with the given ID
        Vehicle targetVehicle = null;
        // loop through all ports and vehicles to find the vehicle with given ID
        for (Port port : ports) {
            for (Vehicle vehicle : port.getVehicles()) {
                if (vehicle.getVehicle_number() == vehicleID) {
                    targetVehicle = vehicle;
                    break;
                }
            }
            if (targetVehicle != null) {
                break;
            }
        }

        // Check if the vehicle is found
        if (targetVehicle == null) {
            System.out.println("Vehicle with ID " + vehicleID + " not found.");
            return;
        }

        // Update vehicle details
        System.out.print("Enter updated vehicle name (type 'skip' to keep the current name): ");
        String updatedName = scanner.next();
        if (!updatedName.equals("skip")) {
            targetVehicle.setVehicleName(updatedName);
        }

        System.out.print("Enter updated vehicle fuel capacity (type 'skip' to keep the current value): ");
        String fuelCapacityInput = scanner.next();
        if (!fuelCapacityInput.equals("skip")) {
            double updatedFuelCapacity = Double.parseDouble(fuelCapacityInput);
            targetVehicle.setFuelCapacity(updatedFuelCapacity);
        }

        System.out.print("Enter updated vehicle carrying capacity (type 'skip' to keep the current value): ");
        String carryingCapacityInput = scanner.next();
        if (!carryingCapacityInput.equals("skip")) {
            double updatedCarryingCapacity = Double.parseDouble(carryingCapacityInput);
            targetVehicle.setCarryingCapacity(updatedCarryingCapacity);
        }
        System.out.println("1. SHIP");
        System.out.println("2. BASIC_TRUCK");
        System.out.println("3. REEFER_TRUCK");
        System.out.println("4. LIQUID_TRUCK");
        System.out.print("Enter updated vehicle type (1 -> 4) (type 'skip' to keep the current value): ");
        String typeInput = scanner.next();
        if (!typeInput.equals("skip")) {
            int updatedType = Integer.parseInt(typeInput);
            targetVehicle.setType(updatedType);
        }

        // Update the vehicle in the list
        writeVehiclesBackToFile(ports);
        System.out.println("Vehicle with ID " + vehicleID + " has been updated.");
    }

    // read vehicle from file
    static List<Port> readVehicle() {
        List<Port> ports = ContainerCRUD.readContainer();
        // Read the file vehicle_data.txt
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/vehicle_data.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Divide the line into parts and put it in variables
                String[] parts = line.split(" ");
                if (parts.length >= 7) {
                    int vehicle_number = Integer.parseInt(parts[0]);
                    int portID = Integer.parseInt(parts[1]) - 1;
                    String vehicleName = parts[2];
                    double currentFuel = Double.parseDouble(parts[3]);
                    double fuelCapacity = Double.parseDouble(parts[4]);
                    double carryingCapacity = Double.parseDouble(parts[5]);
                    int totalContainer = Integer.parseInt(parts[6]);
                    int type = Integer.parseInt(parts[7]);
                    // Create a new vehicle and add it to the list
                    Vehicle vehicle = new Vehicle(vehicle_number,vehicleName,currentFuel,fuelCapacity,carryingCapacity,totalContainer,type);
                    ports.get(portID).addVehicle(vehicle);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
            return ports;
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return ports;
    }
    // delete vehicle
    static void deleteVehicle() {
        List<Port> ports = readVehicle();
        Scanner scanner = new Scanner(System.in);

        // Print the list of vehicles
        printListOfVehicle(ports);

        System.out.print("Enter the ID of the vehicle you want to delete: ");
        int vehicleIDToDelete = scanner.nextInt();
        deleteVehicleWithGivenInfo(ports, vehicleIDToDelete);
    }

    // delete vehicle in specific port with given port ID
    static void deleteVehicleInPort(int PortID) {
        List<Port> ports = readVehicle();
        Scanner scanner = new Scanner(System.in);
        printVehiclesInPort(ports.get(PortID -1));
        System.out.print("Enter the ID of the vehicle you want to delete: ");
        int vehicleIDToDelete = scanner.nextInt();
        deleteVehicleWithGivenInfo(ports, vehicleIDToDelete);
    }

    // delete vehicle with given vehicle ID
    static void deleteVehicleWithGivenInfo(List<Port> ports, int vehicleIDToDelete) {
        // Find the vehicle with the given ID and delete it
        boolean vehicleFound = false;
        for (Port port : ports) {
            for (Vehicle vehicle : port.getVehicles()) {
                if (vehicle.getVehicle_number() == vehicleIDToDelete) {
                    port.getVehicles().remove(vehicle);
                    vehicleFound = true;
                    break;
                }
            }
            if (vehicleFound) {
                break;
            }
        }

        // Check if the vehicle is found
        if (!vehicleFound) {
            //226
            System.out.println("Vehicle with ID " + vehicleIDToDelete + " not found.");
        } else {
            // Update the data file after deleting the vehicle
            writeVehiclesBackToFile(ports);
            System.out.println("Vehicle with ID " + vehicleIDToDelete + " has been deleted.");
        }
    }

    // write vehicle back to file
    static void writeVehiclesBackToFile(List<Port> ports) {
        try {
            // Create a writer to vehicle_data.txt file
            FileWriter fileWriter = new FileWriter("resources/vehicle_data.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            // Loop through all ports and vehicles
            for (Port port : ports) {
                List<Vehicle> vehicles = port.getVehicles();
                for (Vehicle vehicle : vehicles) {
                    bufferedWriter.write(String.valueOf(vehicle.getVehicle_number()));
                    bufferedWriter.write(" ");
                    bufferedWriter.write(String.valueOf(port.getP_number()));
                    bufferedWriter.write(" ");
                    bufferedWriter.write(vehicle.getVehicleName());
                    bufferedWriter.write(" ");
                    bufferedWriter.write(String.valueOf(vehicle.getCurrentFuel()));
                    bufferedWriter.write(" ");
                    bufferedWriter.write(String.valueOf(vehicle.getFuelCapacity()));
                    bufferedWriter.write(" ");
                    bufferedWriter.write(String.valueOf(vehicle.getCarryingCapacity()));
                    bufferedWriter.write(" ");
                    bufferedWriter.write(String.valueOf(vehicle.getTotalContainer()));
                    bufferedWriter.write(" ");
                    bufferedWriter.write(String.valueOf(vehicle.getType()));
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
            // print notification when the file is written successfully
            System.out.println("Data have been saved");
        } catch (IOException e) {
            // print notification when the file is written unsuccessfully
            System.err.println("An error occurred while writing the file: " + e.getMessage());
        }
    }

    // refuel vehicle
    static void refuelVehicle() {
        List<Port> ports = readVehicle();
        Scanner scanner = new Scanner(System.in);
        printAllVehicle();
        System.out.print("Enter the ID of the vehicle you want to refuel: ");
        int vehicleID = scanner.nextInt();

        // Find the vehicle with the given ID
        Vehicle targetVehicle = null;
        for (Port port : ports) {
            for (Vehicle vehicle : port.getVehicles()) {
                if (vehicle.getVehicle_number() == vehicleID) {
                    targetVehicle = vehicle;
                    break;
                }
            }
            if (targetVehicle != null) {
                break;
            }
        }

        if (targetVehicle == null) {
            System.out.println("Vehicle with ID " + vehicleID + " not found.");
            return;
        }

        // Prompt the user for the amount to refuel
        System.out.println("Current vehicle fuel: " + targetVehicle.getCurrentFuel());
        System.out.println("Max fuel capacity: " + targetVehicle.getFuelCapacity());
        while (true) {
            System.out.print("Enter fuel you want to add: ");
            double addingFuel = scanner.nextDouble();
            if (addingFuel <= targetVehicle.getFuelCapacity() - targetVehicle.getCurrentFuel()) {
                targetVehicle.setCurrentFuel(targetVehicle.getCurrentFuel() + addingFuel);
                break;
            }
            System.out.println("invalid fuel, please try again");
        }

        // Update the data file with the new fuel level
        writeVehiclesBackToFile(ports);
    }
}
