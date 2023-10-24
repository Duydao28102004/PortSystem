import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding

        // Create UI components
        JLabel heading = new JLabel("Port Management System!");
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");

        JButton exitButton = new JButton("Exit");


        // Add components to the panel with GridBagConstraints to center them
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(heading, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(usernameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx= 1;
        gbc.gridwidth = 2;
        panel.add(passwordField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        panel.add(loginButton, gbc);

        gbc.gridy = 3;
        gbc.gridx = 2;
        panel.add(exitButton, gbc);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> passwords = new HashMap<>();
                HashMap<String, Integer> userTypes = new HashMap<>();
                readLoginData(passwords, userTypes);
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                // check if username and password are correct
                if (passwords.containsKey(username)) {
                    String storedPassword = passwords.get(username);
                    if (storedPassword.equals(password)) {
                        JOptionPane.showMessageDialog(frame, "Welcome user " + username);
                        frame.dispose();
                        if (userTypes.get(username) == 0) {
                            User.admin();
                        } else {
                            User.portManager(userTypes.get(username));
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Wrong password. Please try again.");

                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Wrong username. Please try again.");
                }
            }
        });

        frame.setVisible(true);
    }

    // Replace this method with your authentication logic
    private static boolean authenticate(String username, String password) {
        // Example: Check username and password against predefined values
        return username.equals("yourUsername") && password.equals("yourPassword");
    }
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
