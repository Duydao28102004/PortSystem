import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("CONTAINER PORT MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Minh Vu & Dr. Phong Ngo");
        System.out.println("Team: 23");
        System.out.println("Dao Bao Duy - s3978826\n" +
                "Tran Luu Quang Tung - s3978481\n" +
                "Nguyen Duy Anh - s4022628\n" +
                "Nguyen Huy Anh - s3956092\n");

        // read login data from file
        HashMap<String, String> passwords = new HashMap<>();
        HashMap<String, Integer> userTypes = new HashMap<>();
        readLoginData(passwords, userTypes);
        // ask user for username and password
        System.out.println("LOGIN");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String inputUserName = scanner.next();
            System.out.print("Enter your password: ");
            String inputPassword = scanner.next();
            // check if username and password are correct
            if (passwords.containsKey(inputUserName)) {
                String storedPassword = passwords.get(inputUserName);
                if (storedPassword.equals(inputPassword)) {
                    System.out.println("Welcome user " + inputUserName);
                    if (userTypes.get(inputUserName) == 0) {
                        User.admin();
                    } else {
                        System.out.println("Port " + userTypes.get(inputUserName) + " commands:");
                        User.portManager(userTypes.get(inputUserName));
                    }
                    break;
                } else {
                    System.out.println("Invalid password, please try again");
                }
            } else {
                System.out.println("Invalid username, please try again");
            }
        }
    }
    // read login data from file
    static void readLoginData(HashMap<String, String> passwords, HashMap<String, Integer> userTypes) {
        try {
            // create a reader to login_data.txt file
            FileReader fileReader = new FileReader("resources/login_data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            // loop through all lines in the file
            while ((line = bufferedReader.readLine()) != null) {
                // divide the line into parts and put it in variables
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    int userType = Integer.parseInt(parts[2]);
                    passwords.put(username, password);
                    userTypes.put(username, userType);
                }
            }
            bufferedReader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
}
