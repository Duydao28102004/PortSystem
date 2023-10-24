import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

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

        // Add components to the panel with GridBagConstraints to center them
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(heading, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // Replace this with your authentication logic
                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Login Failed. Please try again.");
                }

                // Clear the password field
                passwordField.setText("");
            }
        });

        frame.setVisible(true);
    }

    // Replace this method with your authentication logic
    private static boolean authenticate(String username, String password) {
        // Example: Check username and password against predefined values
        return username.equals("yourUsername") && password.equals("yourPassword");
    }
}
