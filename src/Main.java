import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        HashMap<String, String> passwords = new HashMap<>();
        HashMap<String, Integer> userTypes = new HashMap<>();
        readLoginData(passwords, userTypes);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String inputUserName = scanner.next();
            System.out.print("Enter your password: ");
            String inputPassword = scanner.next();
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

    static void readLoginData(HashMap<String, String> passwords, HashMap<String, Integer> userTypes) {
        try {
            FileReader fileReader = new FileReader("resources/login_data.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {

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
        }
    }
}
