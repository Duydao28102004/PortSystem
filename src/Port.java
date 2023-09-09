
public class Port {
    private int p_number;
    private String portName;
    private double latitude;
    private double longitude;
    private int storingCap;
    private int landingCap;


//    constructor
    public Port(int p_number, String portName, double latitude, double longitude, int storingCap, int landingCap) {
        this.p_number = p_number;
        this.portName = portName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCap = storingCap;
        this.landingCap = landingCap;
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
}
