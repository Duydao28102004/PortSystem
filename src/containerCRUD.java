import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class containerCRUD {
    static void createContainer() {
        List<Port> ports = portCRUD.readPorts();
        try {
            FileReader fileReader = new FileReader("resources/container_data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                int pID = Integer.parseInt(parts[0]);
                int cID = Integer.parseInt(parts[1]);
                double w = Double.parseDouble(parts[2]);
                int t = Integer.parseInt(parts[3]);
                Container tempContainer = new Container(cID,w,t);
                ports.get(pID).addContainer(tempContainer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        for (Port port : ports) {
            System.out.println(port.getP_number());
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
                if (Integer.parseInt(parts[0])>= containerID) {
                    containerID = Integer.parseInt(parts[0]) + 1;
                }
            }
        }
        catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return containerID;
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

//public class containerCRUD {
//
//    Scanner scanner = new Scanner(System.in);
//    String filename = "Containers.txt";
//    String ports = "Ports.txt";
//
//    static void containerCRUD() {
//        try {
//            System.out.println("1. Create Container");
//            System.out.println("2. Read Containers");
//            System.out.println("3. Update");
//            System.out.println("4. Delete");
//            System.out.println("5. Stop the process");
//            System.out.print("Enter a number to choose: ");
//            String choice = scanner.nextLine();
//            if (choice.equals("1")) {
//                createContainer();
//            } else if (choice.equals("2")) {
//                readContainer();
//            } else if (choice.equals("3")) {
//                updateContainer();
//            } else if (choice.equals("4")) {
//                deleteContainer();
//            } else if (choice.equals("5")) {
//                System.out.println("End the process");
//            }
//            else {
//                System.out.println("Invalid choice");
//                System.out.println("Press any key to continue");
//                scanner.nextLine();
//                new containerCRUD();
//            }
//        } catch (Exception ex) {
//            System.out.print("Error");
//        }
//    }
//
//    void createContainer() {
//        try {
//            Path path = Paths.get(filename);
//            Path portPath = Paths.get(ports);
//
//            try {
//                if (!Files.exists(path)) {
//                    Files.createFile(path);
//                    System.out.println("File created");
//                } else {
//                    System.out.println("File already exists.");
//                }
//            } catch (IOException e) {
//                System.out.println("An error occurred.");
//                e.printStackTrace();
//            }
//
//            // Open the file for appending
//            OutputStream output = new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.APPEND));
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
//
//            System.out.println("\n CREATE CONTAINER \n ");
//
//            // Generate a new ID based on existing IDs in the file
//            int newID = generateNewID();
//
//            System.out.println("Generated ID: " + newID);
//            System.out.println("Enter Weight: ");
//            float weight = Float.parseFloat(scanner.nextLine());
//
//            // Display container type options
//            System.out.println("Choose Container Type:");
//            System.out.println("1. DRY_STORAGE");
//            System.out.println("2. OPEN_TOP");
//            System.out.println("3. OPEN_SIDE");
//            System.out.println("4. REFRIGERATED");
//            System.out.println("5. LIQUID");
//
//            // Prompt the user to enter a number for container type
//            System.out.print("Enter the number for Container Type (1-5): ");
//            int choice = Integer.parseInt(scanner.nextLine());
//
//            String typeStr = ""; // Initialize to an empty string
//
//            switch (choice) {
//                case 1:
//                    typeStr = "DRY_STORAGE";
//                    break;
//                case 2:
//                    typeStr = "OPEN_TOP";
//                    break;
//                case 3:
//                    typeStr = "OPEN_SIDE";
//                    break;
//                case 4:
//                    typeStr = "REFRIGERATED";
//                    break;
//                case 5:
//                    typeStr = "LIQUID";
//                    break;
//                default:
//                    System.out.println("Invalid choice");
//                    System.out.println("Press any key to continue");
//                    scanner.nextLine();
//                    new containerCRUD();
//                    return; // Exit the method
//            }
//
//            // Read the existing port information from ports.txt and store it in a map
//            Map<Integer, String> portIdToNameMap = readPortInfoFromFile(portPath);
//
//            // Prompt the user to enter the port ID
//            System.out.print("Enter new Port ID: ");
//            int PortID = Integer.parseInt(scanner.nextLine());
//
//            // Look up the port name corresponding to the entered port ID
//            String portName = portIdToNameMap.get(PortID);
//
//            if (portName != null) {
//                // Write container details to the file
//                writer.write(newID + "," + weight + "," + typeStr + "," + portName);
//                writer.newLine();
//                System.out.println("Container has been successfully created!");
//            } else {
//                System.out.println("Port ID does not exist in the file.");
//            }
//
//            writer.close();
//            output.close();
//
//            System.out.println("Press any key to continue");
//            scanner.nextLine();
//            new containerCRUD();
//
//        } catch (IOException ex) {
//            System.out.print(ex.getMessage());
//        }
//    }
//
//    // Read existing port names from the file
//    public static List<String> readPortNamesFromFile(Path fileName) throws IOException {
//        List<String> portNames = new ArrayList<>();
//
//        try (BufferedReader reader = Files.newBufferedReader(fileName)) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length >= 2) {
//                    String portName = parts[1].trim(); // Extract the port name from the second column (index 1)
//                    portNames.add(portName);
//                }
//            }
//        }
//
//        return portNames;
//    }
//
//
//
//    // Method to generate a new ID based on existing IDs in the file
//    private int generateNewID() {
//        try {
//            Path path = Paths.get(filename);
//            if (!Files.exists(path)) {
//                return 1; // If the file doesn't exist, start with ID 1
//            }
//
//            InputStream input = Files.newInputStream(path);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//
//            int maxID = 0;
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                int id = Integer.parseInt(parts[0]);
//                if (id > maxID) {
//                    maxID = id;
//                }
//            }
//
//            reader.close();
//
//            // Generate the new ID by adding 1 to the maximum existing ID
//            return maxID + 1;
//
//        } catch (Exception ex) {
//            System.out.print(ex.getMessage());
//            return -1; // Error occurred, return -1 as a fallback
//        }
//    }
//
//
//
//    void readContainer() {
//        try {
//            Path path = Paths.get(filename);
//            InputStream input = Files.newInputStream(path);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//
//            System.out.println("\n READ CONTAINER \n ");
//            System.out.println("Enter ID to search for a container: ");
//            int filterID = Integer.parseInt(scanner.nextLine());
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                int id = Integer.parseInt(parts[0]);
//                if (filterID == id) {
//                    // Create a Container object for the matched container
//                    System.out.println("Current Container Information:");
//                    System.out.println("ID: " + id);
//                    System.out.println("Weight: " + parts[1].trim());
//                    System.out.println("Type: " + parts[2].trim());
//                    System.out.println("Port: " + parts[3].trim());
//                }
//            }
//
//
//            reader.close();
//
//            System.out.println("Press any key to continue");
//            scanner.nextLine();
//            new containerCRUD();
//
//        } catch (Exception ex) {
//            System.out.print(ex.getMessage());
//        }
//    }
//
//    void deleteContainer() {
//        try {
//            Path path = Paths.get(filename);
//            InputStream input = Files.newInputStream(path);
//            // BufferedReader object
//            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//
//            System.out.println("\n DELETE CONTAINER \n ");
//            System.out.println("Enter ID to delete a container: ");
//            int filterID = Integer.parseInt(scanner.nextLine());
//
//            List<String> lines = new ArrayList<>();
//
//            // Read all lines from the file and store them in a list
//            String line;
//            while ((line = reader.readLine()) != null) {
//                lines.add(line);
//            }
//
//            boolean found = false;
//
//            // Loop through the lines to find and delete the container with the specified ID
//            Iterator<String> iterator = lines.iterator();
//            while (iterator.hasNext()) {
//                String currentLine = iterator.next();
//                String[] parts = currentLine.split(",");
//                int id = Integer.parseInt(parts[0].trim());
//
//                if (filterID == id) {
//                    // Remove the line if the ID matches
//                    iterator.remove();
//                    found = true;
//                    System.out.println("Container with ID " + filterID + " has been deleted.");
//                }
//            }
//
//            reader.close();
//
//            // Write the updated lines back to the file, effectively removing the deleted container
//            Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
//
//
//
//            if (!found) {
//                System.out.println("Container with ID " + filterID + " not found.");
//            }
//
//            System.out.println("Press any key to continue");
//            scanner.nextLine();
//            new containerCRUD();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("File operation performed successfully");
//    }
//
//    void updateContainer() {
//        try {
//            // Define the paths for Containers.txt and ports.txt
//            Path containersPath = Paths.get(filename);
//            Path portsPath = Paths.get(ports);
//
//            // Check if the Containers.txt file exists
//            if (!Files.exists(containersPath)) {
//                Files.createFile(containersPath);
//            }
//
//            // Read the existing port information from ports.txt and store it in a map
//            Map<Integer, String> portIdToNameMap = readPortInfoFromFile(portsPath);
//
//            // Open the Containers.txt file for reading and writing
//            RandomAccessFile containersFile = new RandomAccessFile(containersPath.toFile(), "rw");
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("\n UPDATE CONTAINER \n ");
//            System.out.println("Enter ID to update a container: ");
//            int filterID = Integer.parseInt(scanner.nextLine());
//
//            long currentPosition = 0; // To keep track of the position in the file
//
//            // Loop through the Containers.txt file to find the container with the specified ID
//            String line;
//            while ((line = containersFile.readLine()) != null) {
//                String[] parts = line.split(",");
//                int id = Integer.parseInt(parts[0].trim());
//
//                if (filterID == id) {
//                    // Print the current container information
//                    System.out.println("Current Container Information:");
//                    System.out.println("ID: " + id);
//                    System.out.println("Weight: " + parts[1].trim());
//                    System.out.println("Type: " + parts[2].trim());
//                    System.out.println("Port: " + parts[3].trim());
//
//                    // Prompt the user to update container information
//                    System.out.print("Enter new Weight: ");
//                    float newWeight = Float.parseFloat(scanner.nextLine());
//
//                    System.out.println("Choose new Container Type:");
//                    System.out.println("1. DRY_STORAGE");
//                    System.out.println("2. OPEN_TOP");
//                    System.out.println("3. OPEN_SIDE");
//                    System.out.println("4. REFRIGERATED");
//                    System.out.println("5. LIQUID");
//                    System.out.print("Enter the number for Container Type (1-5): ");
//                    int choice = Integer.parseInt(scanner.nextLine());
//                    String typeStr = "";
//
//                    switch (choice) {
//                        case 1:
//                            typeStr = "DRY_STORAGE";
//                            break;
//                        case 2:
//                            typeStr = "OPEN_TOP";
//                            break;
//                        case 3:
//                            typeStr = "OPEN_SIDE";
//                            break;
//                        case 4:
//                            typeStr = "REFRIGERATED";
//                            break;
//                        case 5:
//                            typeStr = "LIQUID";
//                            break;
//                        default:
//                            System.out.println("Invalid choice");
//                            break;
//                    }
//
//                    // Prompt the user to enter the port ID
//                    System.out.print("Enter new Port ID: ");
//                    int newPortID = Integer.parseInt(scanner.nextLine());
//
//                    // Look up the port name corresponding to the entered port ID
//                    String oldPortName = parts[3].trim();
//                    String newPortName = portIdToNameMap.get(newPortID);
//
//                    if (newPortName == null) {
//                        System.out.println("Port ID does not exist in the file.");
//                        break; // Exit the loop if the new port ID is invalid
//                    }
//
//                    // Check if the new port name is different from the old one
//                    if (!newPortName.equals(oldPortName)) {
//                        // Update the container information in the Containers.txt file
//                        String updatedLine = id + "," + newWeight + "," + typeStr + "," + newPortName;
//                        containersFile.seek(currentPosition);
//                        containersFile.writeBytes(updatedLine);
//
//                        System.out.println("Container information updated successfully.");
//                    } else {
////                        String updatedLine = id + "," + newWeight + "," + typeStr + "," + oldPortName;
////                        containersFile.seek(currentPosition);
////                        containersFile.writeBytes(updatedLine);
////                        System.out.println("The port name of the Container id" + " " + id + " " + "is not change.");
//                        System.out.println("Container information is not changed because the port name is similar.");
//                    }
//
//                    break; // Exit the loop after updating the container
//                }
//
//                currentPosition = containersFile.getFilePointer();
//            }
//
//            containersFile.close();
//
//            System.out.println("Press any key to continue");
//            scanner.nextLine();
//            new containerCRUD();
//
//        } catch (IOException ex) {
//            System.out.print(ex.getMessage());
//        }
//    }
//
//    // Read existing port information from ports.txt and store it in a map
//    private Map<Integer, String> readPortInfoFromFile(Path fileName) throws IOException {
//        Map<Integer, String> portInfoMap = new HashMap<>();
//
//        try (BufferedReader reader = Files.newBufferedReader(fileName)) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length >= 2) {
//                    int portID = Integer.parseInt(parts[0].trim()); // Extract the port ID from the first column (index 0)
//                    String portName = parts[1].trim(); // Extract the port name from the second column (index 1)
//                    portInfoMap.put(portID, portName);
//                }
//            }
//        }
//        return portInfoMap;
//    }
//}
