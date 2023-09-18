import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TripCRUD {
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
        int userChoice = 0;
        while (userChoice == 0) {
            System.out.println("Containers in port available to load in: ");
            containerCRUD.printContainersInPort(compatibleContainersInStartingPort);
            if (compatibleContainersInStartingPort.size() == 0) {
                System.out.println("There is no container remain");
            }
            int selectedContainerID;
            while (true) {
                System.out.print("Enter container ID: ");
                selectedContainerID = scanner.nextInt();
                if (selectedContainerID <= compatibleContainersInStartingPort.size()) {
                    break;
                }
                System.out.println("Invalid ID, please try again");
            }


            // Check if the selectedContainerID is valid
            Container selectedContainer = null;
            for (Container container : compatibleContainersInStartingPort) {
                if (compatibleContainersInStartingPort.get(selectedContainerID - 1) == container) {
                    selectedContainer = container;
                    break;
                }
            }

            if (selectedContainer != null) {
                // The selected container is valid
                if (selectedContainer.getWeight() < remainingWeight) {
                    selectedContainers.add(selectedContainer);
                    compatibleContainersInStartingPort.remove(selectedContainer);
                    totalWeight = totalWeight + selectedContainer.getWeight();
                    remainingWeight = remainingWeight - selectedContainer.getWeight();
                    System.out.println("Current weight: " + totalWeight);
                    System.out.println("Remaining weight: " + remainingWeight);
                } else {
                    System.out.println("The selected container is overweight");
                }
            } else {
                System.out.println("Invalid container ID. Please enter a valid ID.");
            }

            System.out.print("Do you want to add more containers? (0 for yes and 1 for no): ");
            userChoice = scanner.nextInt();
        }

        double totalFuelConsumption = totalFuelConsumptionForTrip(selectedContainers,startingPort,destinationPort,selectedVehicle);
        System.out.println("Fuel consumption for this trip: " + totalFuelConsumption);
        int tripID = autoGenerateTripID();
        System.out.print("Enter departure date (yyyy-MM-dd): ");
        String departureDate = scanner.next();

        System.out.print("Enter arrival date (yyyy-MM-dd): ");
        String arrivalDate= scanner.next();

        Trip trip = new Trip(tripID, selectedVehicle, startingPort, destinationPort, selectedContainers, departureDate, arrivalDate, totalFuelConsumption, 1);
        writeTripToFile(trip);
    }

    static int autoGenerateTripID() {
        int tripID = 1;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/trip_data.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    int id = Integer.parseInt(parts[0]);
                    if (id >= tripID) {
                        tripID = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return tripID;
    }
//    static void approvedTrip() {
//        if (selectedVehicle.getCurrentFuel() < totalFuelConsumption) {
//            System.out.println("Not enough fuel");
//            System.out.println("Current fuel: " + selectedVehicle.getCurrentFuel());
//            System.out.println("Fuel capacity: " + selectedVehicle.getFuelCapacity());
//            while (true) {
//                System.out.print("Enter fuel you want to add: ");
//                double addingFuel = scanner.nextDouble();
//                if (addingFuel <= (selectedVehicle.getFuelCapacity() - selectedVehicle.getCurrentFuel())) {
//                    System.out.println("Fuel has been added");
//                    break;
//                }
//                System.out.println("adding fuel is larger than remaining fuel slot. Please try again.");
//            }
//        }
//        selectedVehicle.setCurrentFuel(selectedVehicle.getCurrentFuel() - totalFuelConsumption);
//        destinationPort.addVehicle(selectedVehicle);
//        startingPort.removeVehicle(selectedVehicle);
//        for (Container container : selectedContainers) {
//            destinationPort.addContainer(container);
//            startingPort.removeContainer(container);
//        }
//        containerCRUD.writeBackToFileContainer(ports);
//        VehicleCRUD.writeVehiclesBackToFile(ports);
//
//    }
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

        if (selectedVehicle.getType() == 1) {
            for (Container container : selectedContainers) {
                switch (container.getType()) {
                    case 1:
                        fuelConsumptionPerKm = fuelConsumptionPerKm + 3.5;
                    case 2:
                        fuelConsumptionPerKm = fuelConsumptionPerKm + 2.8;
                    case 3:
                        fuelConsumptionPerKm = fuelConsumptionPerKm + 2.7;
                    case 4:
                        fuelConsumptionPerKm = fuelConsumptionPerKm + 4.5;
                    case 5:
                        fuelConsumptionPerKm = fuelConsumptionPerKm + 4.8;
                }
            }
        } else {
            for (Container container : selectedContainers) {
                switch (container.getType()) {
                    case 1:
                        fuelConsumptionPerKm = fuelConsumptionPerKm + 4.6;
                    case 2,3:
                        fuelConsumptionPerKm = fuelConsumptionPerKm + 3.2;
                    case 4:
                        fuelConsumptionPerKm = fuelConsumptionPerKm + 5.4;
                    case 5:
                        fuelConsumptionPerKm = fuelConsumptionPerKm + 5.3;
                }
            }
        }
        double startX = startingPort.getLatitude();
        double startY = startingPort.getLongitude();
        double desX = destinationPort.getLatitude();
        double desY = destinationPort.getLongitude();
        double distanceBetweenPort = Math.sqrt(Math.pow(desX - startX,2) + Math.pow(desY - startY,2));
        return distanceBetweenPort * fuelConsumptionPerKm;
    }
    static List<Trip> readTrips() {
        ArrayList<Trip> trips = new ArrayList<>();
        List<Port> ports = VehicleCRUD.readVehicle();
        try {
            FileReader fileReader = new FileReader("resources/trip_data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            if ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                int Trip_number = Integer.parseInt(parts[0]);
                int Vehicle_number = Integer.parseInt(parts[1]);
                Vehicle selectedVehicle = null;
                for (Port port : ports) {
                    for (Vehicle vehicle : port.getVehicles()) {
                        if (vehicle.getVehicle_number() == Vehicle_number) {
                            selectedVehicle = vehicle;
                        }
                    }
                }
                int departurePortID = Integer.parseInt(parts[2]);
                Port departurePort = null;
                for (Port port : ports) {
                    if (port.getP_number() == departurePortID) {
                        departurePort = port;
                    }
                }
                int arrivalPortID = Integer.parseInt(parts[3]);
                Port arrivalPort = null;
                for (Port port : ports) {
                    if (port.getP_number() == arrivalPortID) {
                        arrivalPort = port;
                    }
                }
                String containersSerialCodeInString = parts[4];
                ArrayList<Container> selectedContainer = new ArrayList<>();
                String[] containerSerialCodeInString = containersSerialCodeInString.split(",");
                for (String containerSerial : containerSerialCodeInString) {
                    int containerSerialNumber = Integer.parseInt(containerSerial);
                    for (Port port: ports) {
                        for (Container container : port.getContainers()) {
                            if (container.getSerialCode() == containerSerialNumber) {
                                selectedContainer.add(container);
                            }
                        }
                    }
                }
                String departureDate = parts[5];
                String arrivalDate = parts[6];
                double fuelConsumption = Double.parseDouble(parts[7]);
                int tripStatus = Integer.parseInt(parts[8]);
                Trip trip = new Trip(Trip_number, selectedVehicle, departurePort, arrivalPort, selectedContainer, departureDate, arrivalDate, fuelConsumption, tripStatus);
                trips.add(trip);
            }
        } catch (IOException e) {}
        return trips;
    }
    static void printAllTrip() {
        List<Port> ports = VehicleCRUD.readVehicle();
        List<Trip> trips = readTrips();
        for (Trip trip : trips) {
            System.out.println("Trip number: " + trip.getTrip_number());
            System.out.println("Vehicle: " + trip.getVehicle().getVehicleName());
            System.out.println("Departure port: " + trip.getDeparturePort().getP_number() + ". " + trip.getDeparturePort().getPortName());
            System.out.println("Arrival port: " + trip.getDestinationPort().getP_number() + ". " + trip.getDestinationPort().getPortName());
            System.out.println("Containers:");
            containerCRUD.printContainersInPort(trip.getLoadContainers());
            System.out.println("Departure date: " + trip.getDepartureDate());
            System.out.println("Arrival date: " + trip.getArrivalDate());
            System.out.println("Fuel consumption: " + trip.getFuelConsumption());
            System.out.println("Status: " + trip.getTripStatusName());
            System.out.println();
        }
    }

    static void writeTripToFile(Trip trip) {
        try {
            FileWriter fileWriter = new FileWriter("resources/trip_data.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(String.valueOf(trip.getTrip_number()));
            bufferedWriter.write(" ");
            bufferedWriter.write(String.valueOf(trip.getVehicle().getVehicle_number()));
            bufferedWriter.write(" ");
            bufferedWriter.write(String.valueOf(trip.getDeparturePort().getP_number()));
            bufferedWriter.write(" ");
            bufferedWriter.write(String.valueOf(trip.getDestinationPort().getP_number()));
            bufferedWriter.write(" ");
            for (Container container : trip.getLoadContainers()) {
                bufferedWriter.write(container.toString());
                bufferedWriter.write(",");
            }
            bufferedWriter.write(" ");
            bufferedWriter.write(trip.getDepartureDate());
            bufferedWriter.write(" ");
            bufferedWriter.write(trip.getArrivalDate());
            bufferedWriter.write(" ");
            bufferedWriter.write(String.valueOf(trip.getFuelConsumption()));
            bufferedWriter.write(" ");
            bufferedWriter.write(String.valueOf(trip.getTripStatus()));
            bufferedWriter.close();
        } catch (IOException e) {}
    }
    public static void main(String[] args) {
        printAllTrip();
    }
}
