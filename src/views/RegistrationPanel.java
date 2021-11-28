package views;

import database.InsertAddress;
import models.Address;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import static database.OpenConnection.getConnection;

public class RegistrationPanel extends JPanel {
    private JTextField forename = new JTextField(20);
    private JTextField surname = new JTextField(20);
    private JTextField email = new JTextField(20);
    private JTextField password = new JPasswordField(20);
    private JTextField mobile = new JTextField(20);

    private JRadioButton hostRadioButton = new JRadioButton("Host");
    private JRadioButton guestRadioButton = new JRadioButton("Guest");

    private JTextField house = new JTextField(20);
    private JTextField street = new JTextField(20);
    private JTextField place = new JTextField(20);
    private JTextField postcode = new JTextField(20);

    private JButton registerButton = new JButton("Register");

    public RegistrationPanel() {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        JPanel userDetailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        userDetailsPanel.add(new JLabel("User Details"),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        userDetailsPanel.add(new JLabel("Forename"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        userDetailsPanel.add(forename,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        userDetailsPanel.add(new JLabel("Surname"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        userDetailsPanel.add(surname,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        userDetailsPanel.add(new JLabel("Email"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        userDetailsPanel.add(email,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        userDetailsPanel.add(new JLabel("Password"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        userDetailsPanel.add(password,gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        userDetailsPanel.add(new JLabel("Mobile"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        userDetailsPanel.add(mobile,gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        userDetailsPanel.add(new JLabel("Register as"),gbc);
        ButtonGroup roleButtonGroup = new ButtonGroup();
        roleButtonGroup.add(hostRadioButton);
        roleButtonGroup.add(guestRadioButton);
        gbc.gridx = 1;
        gbc.gridy = 6;
        userDetailsPanel.add(hostRadioButton,gbc);
        gbc.gridx = 2;
        gbc.gridy = 6;
        userDetailsPanel.add(guestRadioButton,gbc);
        add(userDetailsPanel);

        JPanel addressDetailsPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        addressDetailsPanel.add(new JLabel("Address Details"),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        addressDetailsPanel.add(new JLabel("House"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        addressDetailsPanel.add(house,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        addressDetailsPanel.add(new JLabel("Street"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        addressDetailsPanel.add(street,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        addressDetailsPanel.add(new JLabel("Place"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        addressDetailsPanel.add(place,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        addressDetailsPanel.add(new JLabel("Postcode"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        addressDetailsPanel.add(postcode,gbc);
        add(addressDetailsPanel);

        registerButton.addActionListener(e -> {
            if (forename.getText().equals("") || email.getText().equals("") || password.getText().equals("") ||
                    mobile.getText().equals("") || !(hostRadioButton.isSelected() || guestRadioButton.isSelected())) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                        "Registration", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    register();
                    JOptionPane.showMessageDialog(this,"Successfully registered.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error registering user.",
                            "Registration", JOptionPane.WARNING_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        add(registerButton);
    }

    public void register() throws SQLException {
        try (Connection con = getConnection()) {
            String query = "SELECT UserID FROM Users WHERE Email=? LIMIT 1";
            PreparedStatement pst = con.prepareStatement(query);
            pst.clearParameters();
            pst.setString(1, email.getText());

            ResultSet resultSet = pst.executeQuery();

            // if user with given email doesn't exist already insert otherwise return error message
            if (!resultSet.isBeforeFirst()) {
                // MD5() sql function hashes the password - REMEMBER TO ADD TO QUERY LATER
                query = "INSERT INTO Users VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
                pst = con.prepareStatement(query);
                pst.clearParameters();
                pst.setString(1, forename.getText());
                pst.setString(2, surname.getText());
                pst.setString(3, email.getText());
                pst.setString(4, password.getText());
                pst.setString(5, mobile.getText());
                String role;
                if (hostRadioButton.isSelected()) {
                    role = "host";
                } else {
                    role = "guest";
                }
                pst.setString(6, role);
                Address addressToInsert = new Address(0,house.getText(),street.getText(),place.getText(),postcode.getText());
                pst.setInt(7, InsertAddress.insertAddress(addressToInsert)); // obtain id of address

                pst.executeUpdate();
            } else {
                String message = "User with provided email already exists.";
                JOptionPane.showMessageDialog(this, message, "Registration", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
