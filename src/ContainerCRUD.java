import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ContainerCRUD {
    static void createContainer() {
        List<Port> ports = readContainer();
        Scanner scanner = new Scanner(System.in);


        // print all available ports with free slot for container
        System.out.println("Available ports to store container:");
        int checker = 0;
        // loop through ports and print out all ports that have free slot for container
        for (Port port : ports) {
            List<Container> tempCon = port.getContainers();
            if (tempCon.size() < port.getStoringCap()) {
                System.out.println("Port ID: " + port.getP_number() + "-" + port.getPortName());
                checker++;
            }
        }

        // check if there is any available port for container
        if (checker == 0) {
            // print notification and exit when the system can't find available port for container
            System.out.println("No available ports for container storage.");
            return;
        }

        // enter port ID where user want to store the container
        System.out.print("Enter port ID where container is located: ");
        int tempPortID = scanner.nextInt();
        createContainerInPort(tempPortID);
    }
    // create container in specific port
    static void createContainerInPort(int PortID) {
        // read all ports from file
        List<Port> ports = readContainer();
        Scanner scanner = new Scanner(System.in);
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
        int portID = PortID - 1;
        int serialCode = generateSerialCode();

        //add container to correct port in ports list and save it back to file
        Container container = new Container(weight, type, serialCode);
        ports.get(portID).addContainer(container);
        writeBackToFileContainer(ports);
    }
    // generate serial code for container
    static int generateSerialCode() {
        // get current time
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
    // read all ports from file
    static List<Port> readContainer() {
        List<Port> ports = PortCRUD.readPorts();
        // create a reader to port_data.txt
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/container_data.txt"))) {
            // read each line and put it in line
            String line;
            // loop to read all lines in the file
            while ((line = bufferedReader.readLine()) != null) {
                // read the line out and put it in separate parts
                String[] parts = line.split(" ");
                // check all parts in correct format
                if (parts.length >= 4) {
                    // put each part to temp container
                    int portID = Integer.parseInt(parts[0]);
                    double weight = Double.parseDouble(parts[1]);
                    int type = Integer.parseInt(parts[2]);
                    int serialCode = Integer.parseInt(parts[3]);
                    Container tempCon = new Container(weight, type, serialCode);
                    // put temp container back to correct port in the list
                    for (Port port : ports) {
                        if (portID == port.getP_number()) {
                            port.getContainers().add(tempCon);
                        }
                    }
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
    // print all containers in all ports
    static void printContainers() {
        List<Port> ports = readContainer();
        // loop through ports and print out all containers in each port
        for (Port port : ports) {
            System.out.println("Port ID: " + port.getP_number() + ". " + port.getPortName());
            List<Container> tempContainers = port.getContainers();
            printContainersInPort(tempContainers);
        }
    }
    // print all containers in specific port with given port ID
    static void printContainersWithPortID(int PortID) {
        List<Port> ports = readContainer();
        System.out.println("Port ID: " + ports.get(PortID - 1).getP_number() + ". " + ports.get(PortID - 1).getPortName());
        List<Container> tempContainers = ports.get(PortID - 1).getContainers();
        printContainersInPort(tempContainers);
    }
    // print all containers in specific port
    static void printContainersInPort(List<Container> containersInPort) {
        // loop container list and print it out
        int countContainer = 1;
        for (Container container : containersInPort) {
            System.out.println("Container ID: " + countContainer);
            System.out.println("Container weight: " + container.getWeight());
            System.out.println("Container type: " + container.getContainerTypeName());
            System.out.println("Container serial code: " + container.getSerialCode());
            System.out.println("   **********   ");
            countContainer++;
        }
    }
    // check if the port exist or not
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
    // update container
    static void updateContainer() {
        List<Port> ports = readContainer();
        Scanner scanner = new Scanner(System.in);

        // Ask the user for the port ID
        System.out.print("Enter the port ID where the container is located: ");
        int portIDToUpdate = scanner.nextInt();
        Port selectedPort = ports.get(portIDToUpdate - 1);

        List<Container> containersInPort = selectedPort.getContainers();
        enterContainerUpdateInfo(containersInPort,selectedPort,ports);
    }
    // update container in specific port
    static void updateContainersInSpecificPort(int PortID) {
        List<Port> ports = readContainer();
        Port selectedPort = ports.get(PortID - 1);
        List<Container> containersInPort = selectedPort.getContainers();
        enterContainerUpdateInfo(containersInPort,selectedPort,ports);
    }
    // enter container update information
    static void enterContainerUpdateInfo(List<Container> containersInPort, Port selectedPort, List<Port> ports) {
        Scanner scanner = new Scanner(System.in);
        // check if the port has any container or not
        if (containersInPort.isEmpty()) {
            System.out.println("No containers found in port " + selectedPort.getPortName());
            return;
        }

        // print out all containers in selected port and ask the user for the container ID to update
        System.out.println("Containers in port " + selectedPort.getPortName() + ":");
        printContainersInPort(containersInPort);
        int containerIDToUpdate = 0;
        while (true) {
            System.out.print("Enter the container ID you want to update: ");
            containerIDToUpdate = scanner.nextInt();
            if (containersInPort.size() >= containerIDToUpdate - 1) {
                break;
            }
            System.out.println("Invalid container ID, please try again");
        }
        containerIDToUpdate = containerIDToUpdate - 1;

        // loop through the selected port's containers to find selected container
        boolean containerFound = false;

        for (Container container : containersInPort) {
            if (containersInPort.get(containerIDToUpdate) == container) {
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
                // make the containerFound true if the system can find the container
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
    // delete container
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
        Port selectedPort = ports.get(deletePortID - 1);

        // check if the port has any container or not
        List<Container> containersInPort = selectedPort.getContainers();

        deleteContainerWithGivenInfo(containersInPort, selectedPort, ports);
    }
    // delete container in specific port
    static void deleteContainerInSpecificPort(int PortID) {
        List<Port> ports = readContainer();
        Port selectedPort = ports.get(PortID - 1);
        List<Container> containersInPort = selectedPort.getContainers();
        deleteContainerWithGivenInfo(containersInPort, selectedPort, ports);
    }
    // delete container with given information
    static void deleteContainerWithGivenInfo(List<Container> containersInPort, Port selectedPort, List<Port> ports) {
        Scanner scanner = new Scanner(System.in);
        if (containersInPort.isEmpty()) {
            System.out.println("No containers found in port " + selectedPort.getPortName());
            return;
        }

        // print out all containers in selected port and ask user to choose the delete container ID
        System.out.println("Containers in Port " + selectedPort.getPortName() + ":");
        printContainersInPort(containersInPort);

        System.out.print("Enter the container ID you want to delete: ");
        int deleteContainerID = 0;
        while (true) {
            deleteContainerID = scanner.nextInt();
            if (containersInPort.size() >= deleteContainerID - 1) {
                break;
            }
            System.out.println("Invalid container ID, please try again");
        }
        deleteContainerID = deleteContainerID - 1;

        // check is the selected container ID exist or not and if exist the system will delete it
        boolean containerFound = false;
        for (Container container : containersInPort) {
            if (containersInPort.get(deleteContainerID) == container) {
                // remove and make the flag true when found
                containersInPort.remove(container);
                containerFound = true;
                break;
            }
        }

        // notification and count the container number after delete
        if (containerFound) {
            // success notification
            System.out.println("Container with ID " + deleteContainerID + " has been deleted from port " + selectedPort.getPortName());
            writeBackToFileContainer(ports);
        } else {
            // fail to find notification
            System.out.println("Container with ID " + deleteContainerID + " was not found in port " + selectedPort.getPortName());

        }
    }
    // write back to file
    static void writeBackToFileContainer(List<Port> ports) {
        try (FileWriter fileWriter = new FileWriter("resources/container_data.txt");
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            // loop the ports and print container in each port to container_data.txt file
            for (Port port : ports) {
                List<Container> containers = port.getContainers();
                for (Container container : containers) {
                    bufferedWriter.write(String.valueOf(port.getP_number()));
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
            bufferedWriter.close();
            System.out.println("Changes have been saved!");
        } catch (IOException e) {
            // fail notification
            System.err.println("An error occurred while writing the file: " + e.getMessage());
        }
    }
}
