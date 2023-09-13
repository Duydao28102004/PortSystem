import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class VehicleCRUD {
    static void createVehicle() {
        List<Port> ports = readVehicle();
        Scanner scanner = new Scanner(System.in);
        int vehicleID = autoGenerateVehicleID();
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
                System.out.println("No available ports for container storage.");
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
    static int autoGenerateVehicleID() {
        int vehicleID = 1;
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
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return vehicleID;
    }
    static List<Port> readVehicle() {
        List<Port> ports = containerCRUD.readContainer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/vehicle_data.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
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
    static void writeVehiclesBackToFile(List<Port> ports) {
        try {
            FileWriter fileWriter = new FileWriter("resources/vehicle_data.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
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
            System.out.println("Data have been saved");
        } catch (IOException e) {}
    }
}
