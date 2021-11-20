package views;

import models.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static database.OpenConnection.getConnection;

public class LoginPanel extends JPanel {
    private JTextField email = new JTextField(20);
    private JPasswordField password = new JPasswordField(20);
    private JButton loginButton = new JButton("Login");

    // login panel takes mainframe in constructor, so we can dispose mainframe later when logging in
    private MainFrame mainFrame;

    public LoginPanel(MainFrame mainFrame) {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(new JLabel("Login"),gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Email"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(email,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(password,gbc);

        loginButton.addActionListener(e -> {
            if (email.getText().equals("") || String.valueOf(password.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                        "Login", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    login();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton,gbc);
    }

    public void login() throws SQLException {
        try (Connection con = getConnection()) {
            String query = "SELECT * FROM Users WHERE Email=? AND Password=? LIMIT 1";
            PreparedStatement statement = con.prepareStatement(query);
            statement.clearParameters();
            statement.setString(1, email.getText());
            statement.setString(2, String.valueOf(password.getPassword()));

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Invalid credentials");
            } else {
                System.out.println("You have successfully logged in!");
                mainFrame.dispose();
                new MainFrame(new User(resultSet));
            }
        }
    }
}
