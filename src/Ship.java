import java.util.ArrayList;
import java.util.List;

public class Ship extends Vehicle {
    private List<Trip> trips; // Keep track of trips made by the ship.

    public Ship(String id, String name, double currentFuel, double carryingCapacity, double fuelCapacity) {
        super(id, name, currentFuel, carryingCapacity, fuelCapacity);
        this.trips = new ArrayList<>();
    }

    @Override
    public boolean canMoveToPort(Port destinationPort) {
        // A ship can move to a port if the destination port has landing ability.
        return destinationPort.isLandingAbility();
    }

    @Override
    public void moveToPort(Port destinationPort) {
        // Check if the ship can move to the destination port.
        if (!canMoveToPort(destinationPort)) {
            System.out.println("Cannot move to the destination port.");
            return;
        }
        // Calculate the distance to the destination port (you need to implement this).
        double distance = calculateDistanceToPort(destinationPort);

        // Calculate the fuel consumption for the trip.
        double fuelConsumption = distance * getFuelConsumptionPerMile();

        // Check if there is enough fuel for the trip.
        if (currentFuel < fuelConsumption) {
            System.out.println("Not enough fuel for the trip.");
            return;
        }

        // Update the current port.
        this.currentPort = destinationPort;

        // Deduct fuel based on the distance and fuel consumption.
        this.currentFuel -= fuelConsumption;

        // Create a new trip and add it to the list of trips.
        Trip trip = new Trip(this, destinationPort);
        trips.add(trip);

        System.out.println("Moved to port: " + destinationPort.getName());
    }

    @Override
    public void refuel() {
        // Refill the fuel tank to its capacity.
        this.currentFuel = this.fuelCapacity;
        System.out.println("Ship refueled to capacity.");
    }

    @Override
    public void loadContainer(Container container) {
        // Check if the ship has enough capacity and the container type is allowed.
        if (totalContainers + 1 <= carryingCapacity && isContainerTypeAllowed(container.getType())) {
            containers.add(container);
            totalContainers++;
            System.out.println("Loaded container onto the ship.");
        } else {
            System.out.println("Cannot load the container onto the ship.");
        }
    }

    @Override
    public void unloadContainer(Container container) {
        if (containers.contains(container)) {
            containers.remove(container);
            totalContainers--;
            System.out.println("Unloaded container from the ship.");
        } else {
            System.out.println("Container not found on the ship.");
        }
    }

    // Check if a container type is allowed on the ship.
    private boolean isContainerTypeAllowed(Container container) {
        return true;
    }

    // Additional method to get the list of trips made by the ship.
    public List<Trip> getTrips() {
        return trips;
    }
}
