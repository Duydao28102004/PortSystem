import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class containerCRUD {
    static void createContainer() {
        List<Port> ports = readContainer();
        Scanner scanner = new Scanner(System.in);

        // ask user for information of container before create
        System.out.print("Enter container weight: ");
        double weight = scanner.nextDouble();
        System.out.println("Choose Container Type:");
        System.out.println("1. DRY_STORAGE");
        System.out.println("2. OPEN_TOP");
        System.out.println("3. OPEN_SIDE");
        System.out.println("4. REFRIGERATED");
        System.out.println("5. LIQUID");
        System.out.print("Enter container type (1 -> 5): ");
        int type = scanner.nextInt();

        // print all available ports with free slot for container
        System.out.println("Available ports:");
        int checker = 0;
        for (Port port : ports) {
            List<Container> tempCon = port.getContainers();
            if (tempCon.size() < port.getStoringCap()) {
                System.out.println("Port ID: " + port.getP_number() + "-" + port.getPortName());
                checker++;
            }
        }

        if (checker == 0) {
            // print notification and exit when the system can't find available port for container
            System.out.println("No available ports for container storage.");
            return;
        }

        // enter port ID where user want to store the container
        System.out.print("Enter port ID where container is located: ");
        int tempPortID = scanner.nextInt();
        int portID = tempPortID - 1;
        int ID = autoGenerateContainerID(tempPortID);
        int serialCode = generateSerialCode();

        //add container to correct port in ports list and save it back to file
        Container container = new Container(ID, weight, type, serialCode);
        ports.get(portID).addContainer(container);
        writeBackToFileContainer(ports);
    }
    static int autoGenerateContainerID(int portID) {
        int containerID = 1;
        // create a reader to container_data.txt
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/container_data.txt"))) {
            String line;
            // loop through all lines in the file
            while ((line = bufferedReader.readLine()) != null) {
                // separate information in line into variable
                String[] parts = line.split(" ");
                // check the valid formation
                if (parts.length >= 2 && !parts[0].isEmpty()) {
                    int id = Integer.parseInt(parts[1]);
                    int currentPortID = Integer.parseInt(parts[0]);
                    if (id >= containerID && currentPortID == portID) {
                        // add 1 to ID if find any equal id or larger
                        containerID = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            // fail notification while reading file
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        // return ID after calculate
        return containerID;
    }
    static int generateSerialCode() {
        LocalDateTime now = LocalDateTime.now();
        // generate specific serial code by using create time
        int serialCode = now.getYear() * 100000000 +
                now.getMonthValue() * 1000000 +
                now.getDayOfMonth() * 10000 +
                now.getHour() * 100 +
                now.getMinute();
        System.out.println("Port created with serial code: " + serialCode);
        return serialCode;
    }
    static List<Port> readContainer() {
        List<Port> ports = portCRUD.readPorts();
        // create a reader to port_data.txt
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/container_data.txt"))) {
            // read each line and put it in line
            String line;
            // loop to read all lines in the file
            while ((line = bufferedReader.readLine()) != null) {
                // read the line out and put it in seperate parts
                String[] parts = line.split(" ");
                // check all parts in correct format
                if (parts.length >= 5) {
                    // put each part to temp container
                    int portID = Integer.parseInt(parts[0]) - 1;
                    int c_number = Integer.parseInt(parts[1]);
                    double weight = Double.parseDouble(parts[2]);
                    int type = Integer.parseInt(parts[3]);
                    int serialCode = Integer.parseInt(parts[4]);
                    Container tempCon = new Container(c_number, weight, type, serialCode);
                    // put temp container back to correct port in the list
                    ports.get(portID).addContainer(tempCon);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            // fail notification
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return ports;
    }
    static void printSelectedContainers() {
        List<Port> ports = readContainer();
        // loop through ports and print out all containers in each port
        for (Port port : ports) {
            System.out.println("Port ID: " + port.getP_number() + ". " + port.getPortName());
            List<Container> tempContainers = port.getContainers();
            printContainersInPort(tempContainers);
        }
    }
    static void printContainersInPort(List<Container> containersInPort) {
        // loop container list and print it out
        for (Container container : containersInPort) {
            System.out.println("Container ID: " + container.getC_number());
            System.out.println("Container weight: " + container.getWeight());
            System.out.println("Container type: " + container.getContainerTypeName());
            System.out.println("Container serial code: " + container.getSerialCode());
            System.out.println("   **********   ");
        }
    }
    static Port portExist(List<Port> ports, int portID) {
        // loop ports to find selected port
        for (Port port : ports) {
            if (port.getP_number() == portID) {
                // return selected port when found
                return port;
            }
        }
        // return null when system can't find
        return null;
    }
    static void updateContainer() {
        List<Port> ports = readContainer();
        Scanner scanner = new Scanner(System.in);

        // Ask the user for the port ID
        System.out.print("Enter the port ID where the container is located: ");
        int portIDToUpdate = scanner.nextInt();
        portIDToUpdate -= 1;

        if (portExist(ports, portIDToUpdate) == null) {
            System.out.println("Port with ID " + portIDToUpdate + " was not found.");
            return;
        }
        Port selectedPort = ports.get(portIDToUpdate);

        List<Container> containersInPort = selectedPort.getContainers();

        if (containersInPort.isEmpty()) {
            System.out.println("No containers found in port " + selectedPort.getPortName());
            return;
        }

        // print out all containers in selected port and ask the user for the container ID to update
        System.out.println("Containers in port " + selectedPort.getPortName() + ":");
        printContainersInPort(containersInPort);
        System.out.print("Enter the container ID you want to update: ");
        int containerIDToUpdate = scanner.nextInt();

        // loop through the selected port's containers to find selected container
        boolean containerFound = false;

        for (Container container : containersInPort) {
            if (container.getC_number() == containerIDToUpdate) {
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
                // check if user enter right type id
                if (tempType >= 1 && tempType <= 5) {
                    container.setType(tempType);
                    container.setWeight(tempWeight);
                } else {
                    System.out.println("You entered a wrong type!");
                }
                // make the flag true if the system can find the container
                containerFound = true;
                break;
            }
        }
        //return notification to user
        if (containerFound) {
            // success notification
            System.out.println("Container with ID " + containerIDToUpdate + " has been updated in port " + selectedPort.getPortName());
            writeBackToFileContainer(ports);
        } else {
            // fail to find notification
            System.out.println("Container with ID " + containerIDToUpdate + " was not found in port " + selectedPort.getPortName());
        }
    }
    static void deleteContainer() {
        List<Port> ports = readContainer();
        Scanner scanner = new Scanner(System.in);

        //ask user for port ID where delete container located
        System.out.print("Enter the port ID where you want to delete a container: ");
        int deletePortID = scanner.nextInt();

        // check if this port is valid
        if (portExist(ports, deletePortID) == null) {
            System.out.println("Port with ID " + deletePortID + " was not found.");
            return;
        }
        Port selectedPort = ports.get(deletePortID);

        // check if the port has any container or not
        List<Container> containersInPort = selectedPort.getContainers();

        if (containersInPort.isEmpty()) {
            System.out.println("No containers found in port " + selectedPort.getPortName());
            return;
        }

        // print out all containers in selected port and ask user to choose the delete container ID
        System.out.println("Containers in Port " + selectedPort.getPortName() + ":");
        printContainersInPort(containersInPort);

        System.out.print("Enter the container ID you want to delete: ");
        int deleteContainerID = scanner.nextInt();

        // check is the selected container ID exist or not and if exist the system will delete it
        boolean containerFound = false;
        for (Container container : containersInPort) {
            if (container.getC_number() == deleteContainerID) {
                // remove and make the flag true when found
                containersInPort.remove(container);
                containerFound = true;
                break;
            }
        }

        // notification and count the container number after delete
        if (containerFound) {
            // count again section
            for (int i = 0; i < containersInPort.size(); i++) {
                containersInPort.get(i).setC_number(i + 1);
            }
            // success notification
            System.out.println("Container with ID " + deleteContainerID + " has been deleted from port " + selectedPort.getPortName());
            writeBackToFileContainer(ports);
        } else {
            // fail to find notification
            System.out.println("Container with ID " + deleteContainerID + " was not found in port " + selectedPort.getPortName());
        }
    }

    static void writeBackToFileContainer(List<Port> ports) {
        try (FileWriter fileWriter = new FileWriter("resources/container_data.txt");
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            // loop the ports and print container in each port to container_data.txt file
            for (Port port : ports) {
                List<Container> containers = port.getContainers();
                for (Container container : containers) {
                    bufferedWriter.write(String.valueOf(port.getP_number()));
                    bufferedWriter.write(" ");
                    bufferedWriter.write(String.valueOf(container.getC_number()));
                    bufferedWriter.write(" ");
                    bufferedWriter.write(String.valueOf(container.getWeight()));
                    bufferedWriter.write(" ");
                    bufferedWriter.write(String.valueOf(container.getType()));
                    bufferedWriter.write(" ");
                    bufferedWriter.write(String.valueOf(container.getSerialCode()));
                    bufferedWriter.newLine();
                }
            }
            // success notification
            System.out.println("Changes have been saved!");
        } catch (IOException e) {
            // fail notification
            System.err.println("An error occurred while writing the file: " + e.getMessage());
        }
    }
}
