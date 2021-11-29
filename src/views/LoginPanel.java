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

        this.mainFrame = mainFrame;

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
            PreparedStatement pst = con.prepareStatement(query);
            pst.clearParameters();
            pst.setString(1, email.getText());
            pst.setString(2, String.valueOf(password.getPassword()));

            ResultSet resultSet = pst.executeQuery();

            // if no records are returned
            if (!resultSet.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "Incorrect credentials.",
                        "Login", JOptionPane.WARNING_MESSAGE);
            } else {
                resultSet.next();
                int userID = resultSet.getInt("UserID");
                String forename = resultSet.getString("Forename");
                String surname = resultSet.getString("Surname");
                String email = resultSet.getString("Email");
                String mobile = resultSet.getString("Mobile");
                String role = resultSet.getString("Role");

                mainFrame.dispose();
                new MainFrame("Homebreak PLC",new User(userID, forename, surname, email, mobile, role));
            }
        }
    }
}
