import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;


public class Account {

    Scanner scanner = new Scanner(System.in);
    String filename = "account.txt";
    public Account() {
        try {
            System.out.println("1. Create user account");
            System.out.println("2. Login");
            System.out.print("Enter 1 or 2 to choose: ");
            String choice = scanner.nextLine();
            if (choice.equals("1")){
                createAccount();
            } else if (choice.equals("2")){
                loginAccount();
            } else {
                System.out.println("Invalid choice");
                System.out.println("Press any key to continue");
                String proc = scanner.nextLine();
                new UserAccount();
            }
        }catch (Exception ex) {
            System.out.print("Error");
        }
    }

    // Write the input username and password into a .txt file
    void createAccount() {
        try {
            Path path = Paths.get(filename.toString());
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(path, APPEND));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
            System.out.println("\n CREATE ACCOUNT \n ");
            System.out.println("Enter username: ");
            String username = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();

            writer.write(username + "," + password);
            writer.newLine();
            System.out.println("Account has been successfully created!");
            writer.close();
            output.close();

            System.out.println("Press any key to continue");
            // PRESS ANY KEY TO RECALL THE METHOD "UserAccount()"
            String proc = scanner.nextLine();
            new UserAccount();

        } catch(Exception ex){
            System.out.print(ex.getMessage());
        }
    }

    // Check the enter username and password if they match with those stores in the .txt file
    void loginAccount() {
        try {
            Path path = Paths.get(filename.toString());
            InputStream input = Files.newInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println("\n LOGIN \n ");
            System.out.println("Enter username: ");
            String username = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();

            String _temp = null;
            String _user;
            String _pass;
            boolean found = false;
            // continue read the file until there nothing to read
            while ((_temp = reader.readLine()) != null){
                // turn the each line of username and password into array & separate the username and password in the same line with a comma ","
                String[] account = _temp.split(",");
                // let username index = 0 and password index = 1
                _user = account[0];
                _pass = account[1];
                // if the username and password in the file match with the input username and password => found = true
                if (_user.equals(username) && (_pass.equals(password))){
                    found = true;
                }
            }

            if (found == true){
                System.out.println("Access granted");
            } else {
                System.out.println("Invalid username or password");
            }
            reader.close();

            System.out.println("Press any key to continue");
            String proc = scanner.nextLine();
            new UserAccount();

        } catch (Exception ex){
            System.out.print(ex.getMessage());
        }
    }

}
