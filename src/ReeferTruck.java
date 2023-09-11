public class ReeferTruck extends Truck {

    public ReeferTruck(String id, String name, double currentFuel, double carryingCapacity, double fuelCapacity) {
        super(id, name, currentFuel, carryingCapacity, fuelCapacity);
    }

    @Override
    public boolean canMoveToPort(Port destinationPort) {
        // A reefer truck can move to a port if the destination port has landing ability.
        return destinationPort.isLandingAbility();
    }

    @Override
    public void loadContainer(Container container) {
        // Check if the reefer truck has enough capacity and the container type is allowed.
        if (totalContainers + 1 <= carryingCapacity && isContainerTypeAllowed(container.getType())) {
            containers.add(container);
            totalContainers++;
            System.out.println("Loaded container onto the reefer truck.");
        } else {
            System.out.println("Cannot load the container onto the reefer truck.");
        }
    }
    @Override
    protected boolean isContainerTypeAllowed(Container container) {
        return container.getType() == ContainerType.REFRIGERATED; // Reefer trucks can only carry refrigerated containers.
    }
}

