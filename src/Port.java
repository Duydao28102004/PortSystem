import java.util.ArrayList;
import java.lang.Math;

public class Port {
    private int p_number;
    private String portName;
    private double latitude;
    private double longitude;
    private int storingCap;
    private int landingCap;
    private int containerNumber;

//    default value
    public Port() {
    }

//    constructor
    public Port(int p_number, String portName, double latitude, double longitude, int storingCap, int landingCap, int containerNumber) {
        this.p_number = p_number;
        this.portName = portName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCap = storingCap;
        this.landingCap = landingCap;
        this.containerNumber = containerNumber;
    }

//    get value from the user
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

    public int getContainerNumber() {
        return containerNumber;
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

    public void setContainerNumber(int containerNumber) {
        this.containerNumber = containerNumber;
    }
//    unique method
    public double getDistanceBetweenPorts(Port otherPort) {
        return Math.sqrt(Math.pow(otherPort.latitude - this.latitude, 2) + Math.pow(otherPort.longitude - this.latitude, 2));
    }
}
