package views;

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
        JPanel userSection = new JPanel();
        userSection.setLayout(new GridLayout(6,2));
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
        JPanel addressSection = new JPanel();
        addressSection.setLayout(new GridLayout(4,2));
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
            // MD5() sql function hashes the password - REMEMBER TO ADD TO QUERY LATER
            String query = "INSERT INTO Users VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
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
            statement.setInt(7, insertAddress()); // obtain id of address

            statement.executeUpdate();
        }
    }

    public int insertAddress() throws SQLException {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO Addresses VALUES (null, ?, ?, ?, ?, null)";

            PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.clearParameters();
            statement.setString(1, house.getText());
            statement.setString(2, street.getText());
            statement.setString(3, place.getText());
            statement.setString(4, postcode.getText());

            int insertedRows = statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to create address, no AddressID returned.");
                }
            }
        }
    }
}
