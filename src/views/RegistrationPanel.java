package views;

import database.InsertAddress;
import models.Address;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

import static database.OpenConnection.getConnection;

public class RegistrationPanel extends JPanel {
    public JTextField forename = new JTextField();
    public JTextField surname = new JTextField();
    public JTextField email = new JTextField();
    public JTextField password = new JPasswordField();
    public JTextField mobile = new JTextField();

    public JRadioButton hostRole = new JRadioButton("Host");
    public JRadioButton guestRole = new JRadioButton("Guest");

    public JTextField house = new JTextField();
    public JTextField street = new JTextField();
    public JTextField place = new JTextField();
    public JTextField postcode = new JTextField();

    public JButton registerButton = new JButton("Register");

    public RegistrationPanel() {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        // listener object for text fields which are required to be filled in
        DocumentListener requireFilledListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                requiredInformationFilled();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                requiredInformationFilled();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                requiredInformationFilled();
            }
        };

        add(new JLabel("Register"));
        JPanel userSection = new JPanel(new GridLayout(6,2));
        userSection.add(new JLabel("Forename"));
        userSection.add(forename);
        forename.getDocument().addDocumentListener(requireFilledListener);
        userSection.add(new JLabel("Surname"));
        userSection.add(surname);
        userSection.add(new JLabel("Email"));
        userSection.add(email);
        email.getDocument().addDocumentListener(requireFilledListener);
        userSection.add(new JLabel("Password"));
        userSection.add(password);
        password.getDocument().addDocumentListener(requireFilledListener);
        userSection.add(new JLabel("Mobile"));
        userSection.add(mobile);

        userSection.add(new JLabel("Register as"));
        ButtonGroup roleGroup = new ButtonGroup();
        // listener object for the role radio selection
        ItemListener radioItemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                requiredInformationFilled();
            }
        };
        hostRole.addItemListener(radioItemListener);
        guestRole.addItemListener(radioItemListener);
        roleGroup.add(hostRole);
        roleGroup.add(guestRole);
        JPanel rolePanel = new JPanel();
        rolePanel.add(hostRole);
        rolePanel.add(guestRole);
        userSection.add(rolePanel);

        add(userSection);

        add(new JLabel("Address Details"));
        JPanel addressSection = new JPanel(new GridLayout(4,2));
        addressSection.add(new JLabel("House"));
        addressSection.add(house);
        addressSection.add(new JLabel("Street"));
        addressSection.add(street);
        addressSection.add(new JLabel("Place"));
        addressSection.add(place);
        addressSection.add(new JLabel("Postcode"));
        addressSection.add(postcode);
        add(addressSection);

        registerButton.setEnabled(false);
        registerButton.addActionListener(e -> {
            try {
                register();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        add(registerButton);
    }

    public void requiredInformationFilled() {
        if (forename.getText().equals("") || email.getText().equals("") || password.getText().equals("") ||
                !(hostRole.isSelected() || guestRole.isSelected())) {
            registerButton.setEnabled(false);
        } else {
            registerButton.setEnabled(true);
        }
    }

    public void register() throws SQLException {
        try (Connection con = getConnection();) {
            String query = "SELECT UserID FROM Users WHERE Email=? LIMIT 1";
            PreparedStatement statement = con.prepareStatement(query);
            statement.clearParameters();
            statement.setString(1, email.getText());

            ResultSet resultSet = statement.executeQuery();

            // if user with given email doesn't exist already insert otherwise return error message
            if (!resultSet.isBeforeFirst()) {
                // MD5() sql function hashes the password - REMEMBER TO ADD TO QUERY LATER
                query = "INSERT INTO Users VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
                statement = con.prepareStatement(query);
                statement.clearParameters();
                statement.setString(1, forename.getText());
                statement.setString(2, surname.getText());
                statement.setString(3, email.getText());
                statement.setString(4, password.getText());
                statement.setString(5, mobile.getText());
                String role;
                if (hostRole.isSelected()) {
                    role = "host";
                } else {
                    role = "guest";
                }
                statement.setString(6, role);
                Address addressToInsert = new Address(house.getText(),street.getText(),place.getText(),postcode.getText());
                statement.setInt(7, InsertAddress.insertAddress(addressToInsert)); // obtain id of address

                statement.executeUpdate();
            } else {
                throw new SQLException("User with provided email already exists.");
            }
        }
    }
}
