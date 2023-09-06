import java.util.*;
public class Trip {
    private String vehicleInfo;
    private Date departureDate;
    private Date arrivalDate;
    private Port departurePort;
    private Port arrivalPort;
    private String Status;

    public Trip(String vehicleInfo, Date departureDate, Date arrivalDate, Port departurePort, Port arrivalPort, String status) {
        this.vehicleInfo = vehicleInfo;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        Status = status;
    }

//    Getter value from user

    public String getVehicleInfo() {
        return vehicleInfo;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public String getStatus() {
        return Status;
    }
//    Setter value from user

    public void setVehicleInfo(String vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setDeparturePort(Port departurePort) {
        this.departurePort = departurePort;
    }

    public void setArrivalPort(Port arrivalPort) {
        this.arrivalPort = arrivalPort;
    }

    public void setStatus(String status) {
        Status = status;
    }

}

