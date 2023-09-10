import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class containerCRUD {
    static void createContainer() {

        List<Port> ports = readContainer();
        Scanner scanner = new Scanner(System.in);
        int ID = autoGenerateContainerID();
        System.out.print("Enter container weight: ");
        double weight = scanner.nextDouble();
        System.out.println("Choose Container Type:");
        System.out.println("1. DRY_STORAGE");
        System.out.println("2. OPEN_TOP");
        System.out.println("3. OPEN_SIDE");
        System.out.println("4. REFRIGERATED");
        System.out.println("5. LIQUID");
        System.out.print("Enter container type(1 -> 5): ");
        int type = scanner.nextInt();
        System.out.println("Available port:");
        int checker = 0;
        for (Port port : ports) {
            List<Container> tempCon = port.getContainers();
            if (tempCon.toArray().length < port.getStoringCap()) {
                System.out.println("Port ID: "+port.getP_number() +"-"+port.getPortName());
                checker++;
            }
        }
        if (checker == 0) {
            System.out.println("No available ports for container storage.");
            return; // Exit the method
        }
        System.out.println("Choose port where container located (port ID): ");
        int tempPortID = scanner.nextInt();
        int portID = tempPortID - 1;
        Container container = new Container(ID, weight, type);
        ports.get(portID).addContainer(container);
        writeBackToFileContainer(ports);
    }
    static int autoGenerateContainerID() {
        int containerID = 1;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/container_data.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 1 && !parts[0].isEmpty()) {
                    int id = Integer.parseInt(parts[0]);
                    if (id >= containerID) {
                        containerID = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return containerID;
    }
    static ArrayList<Port> readContainer() {
        ArrayList<Port> ports = (ArrayList<Port>) portCRUD.readPorts();
        try {
            FileReader fileReader = new FileReader("resources/container_data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 4) { // Check if there are at least 4 parts in the line
                    int c_number = Integer.parseInt(parts[0]);
                    double weight = Double.parseDouble(parts[2]);
                    int type = Integer.parseInt(parts[3]);
                    Container tempCon = new Container(c_number, weight, type);
                    int portID = Integer.parseInt(parts[1]) - 1;
                    ports.get(portID).addContainer(tempCon);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ports;
    }
    static void printContainer() {
        List<Port> ports = readContainer();
        for (Port port: ports) {
            System.out.println("Port ID: "+port.getP_number()+". "+port.getPortName());
            List<Container> tempContainers = port.getContainers();
            for (Container container : tempContainers) {
                System.out.println("Container ID: " + container.getC_number());
                System.out.println("Container weight: " + container.getWeight());
                System.out.println("Container type: " + container.getContainerTypeName());
                System.out.println("   **********   ");
            }
        }
    }
    static void updateContainer() {
        ArrayList<Port> ports = readContainer();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter container ID you want to update information: ");
        int checkConID = scanner.nextInt();
        boolean containerFound = false; // Flag to track if the container ID was found

        for (Port port : ports) {
            List<Container> tempContainers = port.getContainers();
            for (Container container : tempContainers) {
                if (container.getC_number() == checkConID) {
                    System.out.print("Enter new weight: ");
                    double tempWeight = scanner.nextDouble();
                    System.out.println("Choose Container Type:");
                    System.out.println("1. DRY_STORAGE");
                    System.out.println("2. OPEN_TOP");
                    System.out.println("3. OPEN_SIDE");
                    System.out.println("4. REFRIGERATED");
                    System.out.println("5. LIQUID");
                    System.out.print("Enter new container type (1 -> 5): ");
                    int tempType = scanner.nextInt();

                    if (tempType >= 1 && tempType <= 5) {
                        container.setType(tempType);
                        container.setWeight(tempWeight);
                    } else {
                        System.out.println("You entered a wrong type!");
                    }

                    containerFound = true;
                    break;
                }
            }
            if (containerFound) {
                break;
            }
        }
        if (!containerFound) {
            System.out.println("Container ID not found!");
        }
        writeBackToFileContainer(ports);
    }
    static void deleteContainer() {
        ArrayList<Port> ports = readContainer();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the container ID you want to delete: ");
        int deleteContainerID = scanner.nextInt();

        boolean containerFound = false;

        for (Port port : ports) {
            List<Container> tempContainers = port.getContainers();
            for (Container container : tempContainers) {
                if (container.getC_number() == deleteContainerID) {
                    tempContainers.remove(container);
                    containerFound = true;
                    break;
                }
            }
            if (containerFound) {
                break;
            }
        }
        if (containerFound) {
            System.out.println("Container with ID " + deleteContainerID + " has been deleted.");
            writeBackToFileContainer(ports);
        } else {
            System.out.println("Container with ID " + deleteContainerID + " was not found.");
        }
    }

    static void writeBackToFileContainer(List<Port> ports) {
        try {
            FileWriter fileWriter = new FileWriter("resources/container_data.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Port port : ports) {
                List<Container> containers = port.getContainers();
                for (Container container : containers) {
                    bufferedWriter.write(String.valueOf(container.getC_number()));
                    bufferedWriter.write(" ");
                    bufferedWriter.write(String.valueOf(port.getP_number()));
                    bufferedWriter.write(" ");
                    bufferedWriter.write(String.valueOf(container.getWeight()));
                    bufferedWriter.write(" ");
                    bufferedWriter.write(String.valueOf(container.getType()));
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
            System.out.println("change has been saved!");
        }
        catch (IOException e) {
            System.err.println("An error occurred while writing the file: " + e.getMessage());
        }
    }
}
