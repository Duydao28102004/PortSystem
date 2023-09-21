import java.util.List;
import java.util.ArrayList;

public class Port {
    private int p_number;
    private String portName;
    private double latitude;
    private double longitude;
    private int storingCap;
    private int landingCap;
    private List<Container> containers;
    private List<Vehicle> vehicles;

    //    constructor
    public Port(int p_number, String portName, double latitude, double longitude, int storingCap, int landingCap) {
        this.p_number = p_number;
        this.portName = portName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCap = storingCap;
        this.landingCap = landingCap;
        this.containers = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    //   getter and setter methods
    public int getP_number() {
        return p_number;
    }

    public String getPortName() {
        return portName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getStoringCap() {
        return storingCap;
    }

    public int getLandingCap() {
        return landingCap;
    }

    //    set value from user
    public void setP_number(int p_number) {
        this.p_number = p_number;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setStoringCap(int storingCap) {
        this.storingCap = storingCap;
    }

    public void setLandingCap(int landingCap) {
        this.landingCap = landingCap;
    }

    //   add and remove container
    public void addContainer(Container container) {
        if (containers.size() < storingCap) {
            containers.add(container);
        } else {
            System.out.println("Port has reached its storing capacity. Cannot add more containers.");
        }
    }

    public void removeContainer(Container container) {
        containers.remove(container);
    }
    //   get container list
    public List<Container> getContainers() {
        return containers;
    }
    //  add and remove vehicle
    public void addVehicle(Vehicle vehicle) {
        if (vehicle.getType() == 1) {
            vehicles.add(vehicle);
            return;
        }
        int countvehicles = 0;
        for (Vehicle tempVehicle : vehicles) {
            if (vehicle.getType() == 2 || vehicle.getType() == 3 || vehicle.getType() == 4) {
                countvehicles++;
            }
        }
        if (countvehicles < landingCap) {
            vehicles.add(vehicle);
        } else {
            System.out.println("Port has reached its landing capacity. Cannot add more vehicles.");
        }
    }
    public void removeVehicle(Vehicle vehicle){
        vehicles.remove(vehicle);
    }
    // get vehicle list
    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
