import java.util.ArrayList;
import java.util.List;

public class Truck extends Vehicle {
    private List<Trip> trips; // Keep track of trips made by the truck.

    public Truck(String id, String name, double currentFuel, double carryingCapacity, double fuelCapacity) {
        super(id, name, currentFuel, carryingCapacity, fuelCapacity);
        this.trips = new ArrayList<>();
    }

    @Override
    public boolean canMoveToPort(Port destinationPort) {
        // A truck can move to a port if the destination port has landing ability.
        return destinationPort.isLandingAbility();
    }

    @Override
    public void moveToPort(Port destinationPort) {
        // Check if the truck can move to the destination port.
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
    public double calulateRequiredFuel(Port destinatedPort){
        double distance = calculateDistanceToPort(destinationPort);
        double fuelConsumption = distance * getFuelConsumptionPerMile();
        return fuelConsumption;
    }

    @Override
    public void refuel() {
        // Refill the fuel tank to its capacity.
        this.currentFuel = this.fuelCapacity;
        System.out.println("Truck refueled to capacity.");
    }

    @Override
    public void loadContainer(Container container) {
        // Check if the truck has enough capacity and the container type is allowed.
        if (totalContainers + 1 <= carryingCapacity && isContainerTypeAllowed(container.getType())) {
            containers.add(container);
            totalContainers++;
            System.out.println("Loaded container onto the truck.");
        } else {
            System.out.println("Cannot load the container onto the truck.");
        }
    }

    @Override
    public void unloadContainer(Container container) {
        if (containers.contains(container)) {
            containers.remove(container);
            totalContainers--;
            System.out.println("Unloaded container from the truck.");
        } else {
            System.out.println("Container not found on the truck.");
        }
    }

    // Check if a container type is allowed on the truck.
    public boolean isContainerTypeAllowed(Container container) {
        Container type = container.getType();
        switch (type) {
            case DRY_STORAGE:
            case OPEN_TOP:
            case OPEN_SIDE:
                return true; // Basic trucks can carry dry storage, open top, and open side containers.
            default:
                return false;
        }
    }

    // Additional method to get the list of trips made by the truck.
    public List<Trip> getTrips() {
        return trips;
    }
}

    

