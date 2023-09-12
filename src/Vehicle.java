//import java.io.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public abstract class Vehicle {
//    protected String id;
//    protected String name;
//    protected double currentFuel;
//    protected double carryingCapacity;
//    protected double fuelCapacity;
//    protected Port currentPort;
//    protected int totalContainers;
//    protected List<Container> containers;
//
//    public Vehicle(String id, String name, double currentFuel, double carryingCapacity, double fuelCapacity) {
//        this.id = id;
//        this.name = name;
//        this.currentFuel = currentFuel;
//        this.carryingCapacity = carryingCapacity;
//        this.fuelCapacity = fuelCapacity;
//        this.currentPort = null;
//        this.totalContainers = 0;
//        this.containers = new ArrayList<>();
//    }
//
//    // Abstract method to check if the vehicle can move to a port.
//    public abstract boolean canMoveToPort(Port destinationPort);
//
//    // Abstract method to move the vehicle to a port.
//    public abstract void moveToPort(Port destinationPort);
//
//    // Abstract method to refuel the vehicle.
//    public abstract void refuel();
//
//    // Abstract method to load a container onto the vehicle.
//    public abstract void loadContainer(Container container);
//
//    // Abstract method to unload a container from the vehicle.
//    public abstract void unloadContainer(Container container);
//
//    public abstract double calulateRequiredFuel(Port destinatedPort);
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public double getCurrentFuel() {
//        return currentFuel;
//    }
//
//    public double getCarryingCapacity() {
//        return carryingCapacity;
//    }
//
//    public double getFuelCapacity() {
//        return fuelCapacity;
//    }
//
//    public Port getCurrentPort() {
//        return currentPort;
//    }
//
//    public int getTotalContainers() {
//        return totalContainers;
//    }
//
//    public List<Container> getContainers() {
//        return containers;
//    }
//
//    public String getContainersAsString() {
//        StringBuilder result = new StringBuilder();
//        for (Container container : containers) {
//            result.append(container.getId()).append(", "); // Append container ID and a comma
//        }
//        // Remove the trailing comma and space, if there are containers
//        if (result.length() > 0) {
//            result.delete(result.length() - 2, result.length());
//        }
//        return result.toString();
//    }
//
//
//@Override
//    public String toString() {
//        return String.format("ID: %s, Type: %s, Name: %s, Current Fuel: %.2f, " +
//                        "Fuel Capacity: %.2f, Carrying Capacity: %.2f, Remaining Capacity: %.2f, " +
//                        "Current Port: {ID: %s, Name: %s}, Containers: {%s}",
//                        getId(), getVehicleType(), getName(), getCurrentFuel(),
//                        getFuelCapacity(), getCarryingCapacity(), getRemainingCapacity(),
//                        getCurrentPort().getId(),
//                        getCurrentPort().getName(), getContainersAsString());
//    }
//}
