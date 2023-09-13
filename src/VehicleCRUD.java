import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VehicleCRUD {
    static void writeVehiclesBackToFile(List<Port> ports) {
        try {
            FileWriter fileWriter = new FileWriter("resources/vehicle_data.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Port port : ports) {
                List<Vehicle> vehicles = port.getVehicles();
                for (Vehicle vehicle : vehicles) {
                    bufferedWriter.write(String.valueOf(vehicle.getVehicle_number()));
                    bufferedWriter.write(vehicle.getVehicleName());
                    bufferedWriter.write(String.valueOf(vehicle.getCurrentFuel()));
                    bufferedWriter.write(String.valueOf(vehicle.getFuelCapacity()));
                    bufferedWriter.write(String.valueOf(vehicle.get));




                }
            }

        } catch (IOException e) {}
    }
}
