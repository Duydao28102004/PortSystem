import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class portCRUD {
    static void createPort() {
        ArrayList<Port> ports = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
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
        Port portAdd = new Port(portID, portName, latitude, longitude, storingCap, landingCap);
        ports.add(portAdd);
        try {
            FileWriter fileWriter = new FileWriter("resources/port_data.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Port port: ports) {
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
            System.out.println("change has been saved!");
        }
        catch (IOException e) {
            System.err.println("An error occurred while writing the file: " + e.getMessage());
        }
    }
    static int autoGeneratePortID() {
        int portID = 1;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/port_data.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (Integer.parseInt(parts[0])>= portID) {
                    portID = Integer.parseInt(parts[0]) + 1;
                }
            }
        }
        catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return portID;
    }
    static List<Port> readPorts() {
        ArrayList<Port> ports = new ArrayList<>();
        String line;
        try {
            FileReader fileReader = new FileReader("resources/port_data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                int portID = Integer.parseInt(parts[0]);
                String portName = parts[1];
                double latitude = Double.parseDouble(parts[2]);
                double longitude = Double.parseDouble(parts[3]);
                int storingCap = Integer.parseInt(parts[4]);
                int landingCap = Integer.parseInt(parts[5]);
                Port port = new Port(portID, portName, latitude, longitude, storingCap, landingCap);
                ports.add(port);
            }
            if (ports.isEmpty()) {
                System.out.println("There is no data in the file.");
            }
        }
        catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return ports;
    }
    static void printPorts() {
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
        System.out.print("Enter port ID you want to update: ");
        int tempPortID = scanner.nextInt();
        if (tempPortID > ports.toArray().length) {
            System.out.println("You enter wrong ID");
            return;
        }
        int portID = tempPortID - 1;
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
        scanner.close();
        writeBackToFile(ports);
    }
    static void deletePort() {
        List<Port> ports = readPorts();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter port ID you want to remove: ");
        int tempPortID = scanner.nextInt();
        if (tempPortID > ports.toArray().length) {
            System.out.println("You enter wrong ID");
            return;
        }
        int portID = tempPortID - 1;
        ports.remove(portID);
        // Iterate through the list and reassign new port IDs starting from 1
        for (int i = 0; i < ports.size(); i++) {
            ports.get(i).setP_number(i + 1);
        }
        writeBackToFile(ports);
    }
    static void writeBackToFile(List<Port> ports) {
        try {
            FileWriter fileWriter = new FileWriter("resources/port_data.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Port port: ports) {
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
            System.out.println("change has been saved!");
        }
        catch (IOException e) {
            System.err.println("An error occurred while writing the file: " + e.getMessage());
        }
    }
}
