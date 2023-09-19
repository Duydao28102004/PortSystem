import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TripCRUD {
    static void createTrip(int PortID) {
        List<Port> ports = VehicleCRUD.readVehicle();
        Scanner scanner = new Scanner(System.in);
        portCRUD.printPorts();
        Port startingPort;
        if (PortID == 0) {
            System.out.print("Enter starting port ID: ");
            int startingPortID = scanner.nextInt();

            // Check if the starting port exists
            startingPort = containerCRUD.portExist(ports, startingPortID);
            if (startingPort == null) {
                System.out.println("Port with ID " + startingPortID + " was not found.");
                return;
            }
        } else {
            int startingPortID = PortID;
            startingPort = containerCRUD.portExist(ports, startingPortID);
        }

        System.out.print("Enter destination port ID: ");
        int destinationPortID = scanner.nextInt();

        // Check if the destination port exists
        Port destinationPort = containerCRUD.portExist(ports, destinationPortID);
        if (destinationPort == null) {
            System.out.println("Port with ID " + destinationPortID + " was not found.");
            return;
        }

        // List vehicles in starting port
        List<Vehicle> vehiclesInStartingPort = startingPort.getVehicles();
        if (vehiclesInStartingPort.isEmpty()) {
            System.out.println("No vehicles found in port " + startingPort.getPortName());
            return;
        }

        // print vehicles in starting port out
        for (Vehicle vehicle : vehiclesInStartingPort) {
            System.out.println("Vehicle ID: " + vehicle.getVehicle_number());
            System.out.println("Vehicle Name: " + vehicle.getVehicleName());
            System.out.println("Fuel Capacity: " + vehicle.getFuelCapacity());
            System.out.println("Carrying Capacity: " + vehicle.getCarryingCapacity());
            System.out.println("Type: " + vehicle.getVehicleTypeName());
            System.out.println("----------------------------------");
        }

        // ask users to select the vehicle
        System.out.print("Enter vehicle ID you want to use: ");
        int selectedVehicleID = scanner.nextInt();


        Vehicle selectedVehicle = null;
        for (Vehicle vehicle : vehiclesInStartingPort) {
            if (vehicle.getVehicle_number() == selectedVehicleID) ;
            selectedVehicle = vehicle;
        }

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

        // return message if the system can not find suitable container for vehicle
        if (compatibleContainersInStartingPort.isEmpty()) {
            System.out.println("There are no suitable containers for the selected vehicle.");
            return;
        }

        // Create a list to hold the selected containers
        List<Container> selectedContainers = new ArrayList<>();
        double totalWeight = 0;
        double remainingWeight = selectedVehicle.getCarryingCapacity();
        int userChoice = 0;
        // a loop for user to load multiple containers on the vehicles
        while (userChoice == 0) {
            System.out.println("Containers in port available to load in: ");
            containerCRUD.printContainersInPort(compatibleContainersInStartingPort);
            if (compatibleContainersInStartingPort.size() == 0) {
                System.out.println("There is no container remain");
                break;
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
                    // add selected container to selected containers list
                    selectedContainers.add(selectedContainer);
                    compatibleContainersInStartingPort.remove(selectedContainer);
                    // calculate remaining weight
                    totalWeight = totalWeight + selectedContainer.getWeight();
                    remainingWeight = remainingWeight - selectedContainer.getWeight();
                    System.out.println("Current weight: " + totalWeight);
                    System.out.println("Remaining weight: " + remainingWeight);
                } else {
                    // notify user if the container is overweight
                    System.out.println("The selected container is overweight");
                }
            } else {
                System.out.println("Invalid container ID. Please enter a valid ID.");
            }

            // ask user if they want to continue add more container
            while (true) {
                System.out.print("Do you want to add more containers? (0 for yes and 1 for no): ");
                userChoice = scanner.nextInt();
                if (userChoice == 1 || userChoice == 0) {
                    break;
                }
                System.out.println("Invalid command, please try again");
            }
        }

        // calculate total fuel consumption for the trip and print it out
        double totalFuelConsumption = totalFuelConsumptionForTrip(selectedContainers, startingPort, destinationPort, selectedVehicle);
        System.out.println("Fuel consumption for this trip: " + totalFuelConsumption);
        int tripID = autoGenerateTripID();
        // loop to ask user for departure date and check the format
        String departureDate;
        while (true) {
            System.out.print("Enter departure date (yyyy-MM-dd): ");
            String userInput = scanner.next();
            if (userInput.length() == 10) {
                departureDate = userInput;
                break;
            }
            System.out.println("Invalid date format, please try again");
        }

        // loop to ask user for arrival date and check the format
        String arrivalDate;
        while (true) {
            System.out.print("Enter arrival date (yyyy-MM-dd): ");
            String userInput = scanner.next();
            if (userInput.length() == 10) {
                arrivalDate = userInput;
                break;
            }
            System.out.println("Invalid date format, please try again");
        }
        // create trip and write back to file
        List<Trip> trips = readTrips();
        Trip trip = new Trip(tripID, selectedVehicle, startingPort, destinationPort, selectedContainers, departureDate, arrivalDate, totalFuelConsumption, 1);
        trips.add(trip);
        writeTripToFile(trips);
    }

    // this method scan the file for current trip ID and calculate the next trip ID
    static int autoGenerateTripID() {
        // default trip ID
        int tripID = 1;
        // read the file and loop to file the largest trip ID
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/trip_data.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    int id = Integer.parseInt(parts[0]);
                    // if exist add 1 more to default
                    if (id >= tripID) {
                        tripID = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            // fail to read notification
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        // return the calculated trip id
        return tripID;
    }

    // this method is used to approve the trip in pending to approve status
    static void approvedTrip() {
        Scanner scanner = new Scanner(System.in);
        // read trips, ports, containers, vehicles out to a list
        List<Trip> trips = readTrips();
        List<Port> ports = VehicleCRUD.readVehicle();
        // print trips
        printSpecifyTrip(trips);
        System.out.print("Enter trip ID you want to approve to move: ");
        int tripID = scanner.nextInt();
        Trip approvingTrip = null;
        for (Trip trip : trips) {
            if (trip.getTrip_number() == tripID) {
                approvingTrip = trip;
            }
        }
        // if trip was approved it will notify users.
        if (approvingTrip.getTripStatus() == 2) {
            System.out.println("This trip has been approved");
            return;
        }

        // get total consumption, selected containers, starting port , destination port and selected vehicle to variable
        double totalFuelConsumption = approvingTrip.getFuelConsumption();
        List<Container> selectedContainers = approvingTrip.getLoadContainers();
        Port startingPort = ports.get(approvingTrip.getDeparturePort().getP_number() - 1);
        Port destinationPort = ports.get(approvingTrip.getDestinationPort().getP_number() - 1);
        List<Container> containersToRemove = new ArrayList<>();
        // check if vehicle moved or not
        Vehicle selectedVehicle = null;
        for (Port port : ports) {
            for (Vehicle vehicle : port.getVehicles()) {
                if (vehicle.getVehicle_number() == approvingTrip.getVehicle().getVehicle_number()) {
                    selectedVehicle = vehicle;
                }
            }
        }
        boolean vehicleFound = false;
        for (Vehicle vehicle : startingPort.getVehicles()) {
            if (vehicle.getVehicle_number() == selectedVehicle.getVehicle_number()) {
                startingPort.getVehicles().remove(vehicle);
                vehicleFound = true;
                break; // Exit the loop once the vehicle is found and removed
            }
        }

        if (!vehicleFound) {
            System.out.println("Error: The selected vehicle is not in the starting port.");
            return; // You may want to return or handle the situation accordingly
        }

        // check the fuel that enough for trip or not and user can refuel it
        if (selectedVehicle.getCurrentFuel() < totalFuelConsumption) {
            System.out.println("Not enough fuel");
            System.out.println("Current fuel: " + selectedVehicle.getCurrentFuel());
            System.out.println("Fuel capacity: " + selectedVehicle.getFuelCapacity());
            while (true) {
                System.out.print("Refuel: ");
                double addingFuel = scanner.nextDouble();
                if (addingFuel <= (selectedVehicle.getFuelCapacity() - selectedVehicle.getCurrentFuel())) {
                    selectedVehicle.setCurrentFuel(selectedVehicle.getCurrentFuel() + addingFuel);
                    break;
                }
                System.out.println("Invalid fuel amount. Try again");
            }
        }

        selectedVehicle.setCurrentFuel(selectedVehicle.getCurrentFuel() - totalFuelConsumption);


        for (Container container : startingPort.getContainers()) {
            for (Container selectedContainer : selectedContainers) {
                if (container.getSerialCode() == selectedContainer.getSerialCode()) {
                    containersToRemove.add(container);
                }
            }
        }

        // move containers and vehicle to destination port and remove itself from starting port and write back to file
        startingPort.getVehicles().remove(selectedVehicle);
        destinationPort.getVehicles().add(selectedVehicle);
        startingPort.getContainers().removeAll(containersToRemove);
        destinationPort.getContainers().addAll(containersToRemove);
        approvingTrip.setTripStatus(2);
        containerCRUD.writeBackToFileContainer(ports);
        VehicleCRUD.writeVehiclesBackToFile(ports);
        writeTripToFile(trips);
    }

    static boolean isContainerCompatibleWithVehicle(Container container, Vehicle vehicle) {
        int containerType = container.getType();
        int vehicleType = vehicle.getType();

        if (vehicleType == 1) {
            // Ships can carry all container types
            return true;
        } else if (vehicleType == 2) {
            // Basic trucks can carry dry-storage, open-top, and open side-containers
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
                    case 2, 3:
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
        double distanceBetweenPort = Math.sqrt(Math.pow(desX - startX, 2) + Math.pow(desY - startY, 2));
        return distanceBetweenPort * fuelConsumptionPerKm;
    }

    static List<Trip> readTrips() {
        ArrayList<Trip> trips = new ArrayList<>();
        List<Port> ports = VehicleCRUD.readVehicle();
        try {
            FileReader fileReader = new FileReader("resources/trip_data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
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
                    for (Port port : ports) {
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
        } catch (IOException e) {
        }
        return trips;
    }

    static void printAllTrip() {
        List<Trip> trips = readTrips();
        printSpecifyTrip(trips);
    }
    static void printTripsFromStartingPort(int PortID) {
        System.out.println("Trip start from port ID " + PortID + ": ");
        List<Trip> trips = readTrips();
        ArrayList<Trip> selectedTrips = new ArrayList<>();
        for (Trip trip : trips) {
            if (trip.getDeparturePort().getP_number() == PortID) {
                selectedTrips.add(trip);
            }
        }
        if (selectedTrips.size() == 0) {
            System.out.println("There is no trip at this port");
            return;
        }
        printSpecifyTrip(selectedTrips);
    }

    static void printSpecifyTrip(List<Trip> trips) {
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

    static void deleteTrip() {
        Scanner scanner = new Scanner(System.in);
        List<Trip> trips = readTrips();

        // Print the list of trips with their IDs for the user to see
        printAllTrip();

        System.out.print("Enter the trip ID you want to delete: ");
        int tripIDToDelete = scanner.nextInt();

        // Check if the tripIDToDelete is within valid range
        if (tripIDToDelete < 1 || tripIDToDelete > trips.size()) {
            System.out.println("Invalid trip ID. No trip deleted.");
            return;
        }

        // Confirm with the user before deleting
        System.out.print("Are you sure you want to delete this trip? (1 for yes and 0 for no): ");
        int confirmation = scanner.nextInt();

        if (confirmation == 1) {
            // Remove the trip from the list based on user input
            trips.remove(tripIDToDelete - 1);
            // Update the trip data file
            writeTripToFile(trips);
            System.out.println("Trip with ID " + tripIDToDelete + " has been deleted.");
        } else {
            System.out.println("Trip deletion canceled.");
        }
    }


    static void writeTripToFile(List<Trip> trips) {
        try {
            FileWriter fileWriter = new FileWriter("resources/trip_data.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Trip trip : trips) {
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
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            System.out.println("Data have been saved");
        } catch (IOException e) {
            System.err.println("An error occurred while writing the file: " + e.getMessage());
        }
    }

    static double calculateTotalFuelConsumptionOnDepartureDay(String departureDate) {
        List<Trip> trips = readTrips();
        double totalFuelConsumption = 0;
        for (Trip trip : trips) {
            if (trip.getDepartureDate().equals(departureDate)) {
                totalFuelConsumption += trip.getFuelConsumption();
            }
        }
        return totalFuelConsumption;
    }

    static void listTripsInDateRange(String startDateStr, String endDateStr) {
        List<Trip> trips = readTrips();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            System.out.println("Trips within the date range " + startDateStr + " to " + endDateStr + ":");

            for (Trip trip : trips) {
                Date tripDate = dateFormat.parse(trip.getDepartureDate());
                if (!tripDate.before(startDate) && !tripDate.after(endDate)) {
                    System.out.println("Trip Number: " + trip.getTrip_number());
                    System.out.println("Departure Date: " + trip.getDepartureDate());
                    System.out.println("Arrival Date: " + trip.getArrivalDate());
                    System.out.println("Fuel Consumption: " + trip.getFuelConsumption());
                    System.out.println("Status: " + trip.getTripStatusName());
                    System.out.println();
                }
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        }
    }
    static void listTripsOnGivenDay(String givenDate) {
        List<Trip> trips = readTrips();

        System.out.println("Trips on " + givenDate + ":");

        for (Trip trip : trips) {
            if (trip.getDepartureDate().equals(givenDate)) {
                System.out.println("Trip Number: " + trip.getTrip_number());
                System.out.println("Vehicle: " + trip.getVehicle().getVehicleName());
                System.out.println("Departure Port: " + trip.getDeparturePort().getPortName());
                System.out.println("Destination Port: " + trip.getDestinationPort().getPortName());
                System.out.println("Containers:");
                containerCRUD.printContainersInPort(trip.getLoadContainers());
                System.out.println("Departure Date: " + trip.getDepartureDate());
                System.out.println("Arrival Date: " + trip.getArrivalDate());
                System.out.println("Fuel Consumption: " + trip.getFuelConsumption());
                System.out.println("Status: " + trip.getTripStatusName());
                System.out.println();
            }
        }
    }
}
