public class Vehicle {
    private int vehicle_number;
    private String vehicleName;
    private double currentFuel;
    private double fuelCapacity;
    private double carryingCapacity;
    private int totalContainer;
    private int type;
    // constructor

    public Vehicle(int vehicle_number, String vehicleName, double currentFuel, double fuelCapacity,double carryingCapacity, int totalContainer, int type) {
        this.vehicle_number = vehicle_number;
        this.vehicleName = vehicleName;
        this.currentFuel = currentFuel;
        this.fuelCapacity = fuelCapacity;
        this.carryingCapacity = carryingCapacity;
        this.totalContainer = totalContainer;
        this.type = type;
    }
    // getter and setter


    public int getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(int vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public double getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public int getTotalContainer() {
        return totalContainer;
    }

    public void setTotalContainer(int totalContainer) {
        this.totalContainer = totalContainer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public enum VehicleType {
        SHIP(1),
        BASICTRUCK(2),
        REEFERTRUCK(3),
        TANKERTRUCK(4);

        private final int value;

        VehicleType(int value) {
            this.value = value;
        }

        public static VehicleType fromValue(int value) {
            for (VehicleType type : VehicleType.values()) {
                if (type.value == value) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid VehicleType value: " + value);
        }
    }
    public String getVehicleTypeName() {
        return Vehicle.VehicleType.fromValue(type).name();
    }

}

