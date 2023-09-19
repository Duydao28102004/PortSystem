import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class PortCRUD {
    static void createPort() {
        ArrayList<Port> ports = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        // ask user for port information before create it
        int portID = autoGeneratePortID();
        System.out.print("Enter port name: ");
        String portName = scanner.next();
        System.out.print("Enter port latitude: ");
        double latitude = scanner.nextDouble();
        System.out.print("Enter port longitude: ");
        double longitude = scanner.nextDouble();
        System.out.print("Enter port storing capacity: ");
        int storingCap = scanner.nextInt();
        System.out.print("Enter port landing capacity: ");
        int landingCap = scanner.nextInt();
        // add created port to ports list and save back to file
        Port portAdd = new Port(portID, portName, latitude, longitude, storingCap, landingCap);
        ports.add(portAdd);
        writeBackToFile(ports, true);
    }
    static int autoGeneratePortID() {
        int portID = 1;
        // create a reader to port_data.txt file
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/port_data.txt"))) {
            String line;
            // loop to read all lines in the file
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                // add 1 to default port ID when find equal port ID
                if (Integer.parseInt(parts[0])>= portID) {
                    portID = Integer.parseInt(parts[0]) + 1;
                }
            }
        }
        catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        // return port ID after calculate
        return portID;
    }
    static List<Port> readPorts() {
        ArrayList<Port> ports = new ArrayList<>();
        String line;
        try {
            // create a reader to port_data.txt file
            FileReader fileReader = new FileReader("resources/port_data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // loop through all lines in the file
            while ((line = bufferedReader.readLine()) != null) {
                // divide the line into parts and put it in variables
                String[] parts = line.split(" ");
                int portID = Integer.parseInt(parts[0]);
                String portName = parts[1];
                double latitude = Double.parseDouble(parts[2]);
                double longitude = Double.parseDouble(parts[3]);
                int storingCap = Integer.parseInt(parts[4]);
                int landingCap = Integer.parseInt(parts[5]);
                // add it to port and save in a list
                Port port = new Port(portID, portName, latitude, longitude, storingCap, landingCap);
                ports.add(port);
            }
            if (ports.isEmpty()) {
                // no port in file notification
                System.out.println("There is no data in the file.");
            }
        }
        catch (IOException e) {
            // fail to read notification
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        // return a list after finished reading ports.
        return ports;
    }
    static void printPorts() {
        // loop through port list and print it out
        for (Port port : readPorts()) {
            System.out.println("Port ID: " + port.getP_number());
            System.out.println("Port name: " + port.getPortName());
            System.out.println("Port latitude: " + port.getLatitude());
            System.out.println("Port latitude: " + port.getLongitude());
            System.out.println("Port storing capacity: " + port.getStoringCap());
            System.out.println("Port landing capacity: " + port.getLandingCap());
            System.out.println("   *********   ");
        }
    }
    static void updatePorts() {
        List<Port> ports = readPorts();
        Scanner scanner = new Scanner(System.in);
        // print port out and  ask user to select the port id they want to update
        printPorts();
        System.out.print("Enter port ID you want to update: ");
        int tempPortID = scanner.nextInt();
        // check valid port ID
        if (tempPortID > ports.toArray().length) {
            System.out.println("You enter wrong ID");
            return;
        }
        int portID = tempPortID - 1;
        enterUpdatePortInfo(ports, portID, scanner);
        // save the port back to file again
        writeBackToFile(ports, false);
    }
    static void updateSpecificPort(int PortID) {
        Scanner scanner = new Scanner(System.in);
        int portID = PortID - 1;
        List<Port> ports = readPorts();
        enterUpdatePortInfo(ports, portID, scanner);
        writeBackToFile(ports, false);
    }
    static void enterUpdatePortInfo(List<Port> ports, int portID, Scanner scanner) {
        // ask user for information they want to update
        System.out.print("Enter new port name (Enter skip to skip): ");
        String temp1 = scanner.next();
        if (!temp1.equals("skip")) {
            ports.get(portID).setPortName(temp1);
        }
        System.out.print("Enter new latitude (Enter skip to skip): ");
        String temp2 = scanner.next();
        if (!temp2.equals("skip")) {
            ports.get(portID).setLatitude(Double.parseDouble(temp2));
        }
        System.out.print("Enter new longitude (Enter skip to skip): ");
        String temp3 = scanner.next();
        if (!temp3.equals("skip")) {
            ports.get(portID).setLongitude(Double.parseDouble(temp3));
        }
        System.out.print("Enter new storing capacity (Enter skip to skip): ");
        String temp4 = scanner.next();
        if (!temp4.equals("skip")) {
            ports.get(portID).setStoringCap(Integer.parseInt(temp4));
        }
        System.out.print("Enter new landing capacity (Enter skip to skip): ");
        String temp5 = scanner.next();
        if (!temp5.equals("skip")) {
            ports.get(portID).setLandingCap(Integer.parseInt(temp5));
        }
    }
    static void deletePort() {
        List<Port> ports = ContainerCRUD.readContainer();
        Scanner scanner = new Scanner(System.in);
        // print out port list and ask user for port ID they want to delete
        printPorts();
        System.out.print("Enter port ID you want to delete: ");
        int tempPortID = scanner.nextInt();
        int portID = tempPortID - 1;
        // check valid port ID
        if (portID < 0 || portID >= ports.size()) {
            System.out.println("Invalid port ID.");
            return;
        }
        // remove container in selected port
        Port portToRemove = ports.get(portID);
        ArrayList<Container> containersToRemove = new ArrayList<>(portToRemove.getContainers());
        for (Container container : containersToRemove) {
            portToRemove.removeContainer(container);
        }
        //remove the port
        ports.remove(portID);

        // count all ports again and write back to file
        for (int i = 0; i < ports.size(); i++) {
            ports.get(i).setP_number(i + 1);
        }
        writeBackToFile(ports, false);
        ContainerCRUD.writeBackToFileContainer(ports);
    }
    static void writeBackToFile(List<Port> ports, Boolean append) {
        try {
            // create a write to port_data.txt
            FileWriter fileWriter = new FileWriter("resources/port_data.txt", append);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Port port: ports) {
                // loop through each port and write information back
                bufferedWriter.write(String.valueOf(port.getP_number()));
                bufferedWriter.write(" ");
                bufferedWriter.write(port.getPortName());
                bufferedWriter.write(" ");
                bufferedWriter.write(String.valueOf(port.getLatitude()));
                bufferedWriter.write(" ");
                bufferedWriter.write(String.valueOf(port.getLongitude()));
                bufferedWriter.write(" ");
                bufferedWriter.write(String.valueOf(port.getStoringCap()));
                bufferedWriter.write(" ");
                bufferedWriter.write(String.valueOf(port.getLandingCap()));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            // success notification
            System.out.println("change has been saved!");
        }
        catch (IOException e) {
            // fail to write information
            System.err.println("An error occurred while writing the file: " + e.getMessage());
        }
    }
}
