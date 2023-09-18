import java.util.*;
public class Trip {
    private int trip_number;
    private Vehicle vehicle;
    private Port departurePort;
    private Port destinationPort;
    private List<Container> loadContainers;
    private String departureDate;
    private String arrivalDate;
    private double fuelConsumption;
    private int tripStatus;

    // constructor

    public Trip(int trip_number, Vehicle vehicle, Port departurePort, Port destinationPort, List<Container> loadContainers, String departureDate, String arrivalDate, double fuelConsumption, int tripStatus) {
        this.trip_number = trip_number;
        this.vehicle = vehicle;
        this.departurePort = departurePort;
        this.destinationPort = destinationPort;
        this.loadContainers = loadContainers;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.fuelConsumption = fuelConsumption;
        this.tripStatus = tripStatus;
    }

    public int getTrip_number() {
        return trip_number;
    }

    public void setTrip_number(int trip_number) {
        this.trip_number = trip_number;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(Port departurePort) {
        this.departurePort = departurePort;
    }

    public Port getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(Port destinationPort) {
        this.destinationPort = destinationPort;
    }

    public List<Container> getLoadContainers() {
        return loadContainers;
    }

    public void setLoadContainers(List<Container> loadContainers) {
        this.loadContainers = loadContainers;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public int getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(int tripStatus) {
        this.tripStatus = tripStatus;
    }
    public enum TripStatus {
        WAITING_APPROVE(1),
        APPROVED(2),
        CANCELLED(3);

        private final int value;

        TripStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static TripStatus fromValue(int value) {
            for (TripStatus status : TripStatus.values()) {
                if (status.value == value) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid TripStatus value: " + value);
        }
    }
    public String getTripStatusName() {
        return TripStatus.fromValue(tripStatus).name();
    }
}

