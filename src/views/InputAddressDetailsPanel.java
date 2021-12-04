package views;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import static database.OpenConnection.getConnection;

public class InputAddressDetailsPanel extends JPanel {
    private JTextField houseField = new JTextField(20);
    private JTextField streetField = new JTextField(20);
    private JTextField placeField = new JTextField(20);
    private JTextField postcodeField = new JTextField(20);

    public InputAddressDetailsPanel() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(new JLabel("Address Details"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("House"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(houseField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Street"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(streetField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Place"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(placeField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Postcode"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(postcodeField, gbc);
    }

    public boolean validateAddressDetails() {
        if (houseField.getText().equals("") || streetField.getText().equals("") || placeField.getText().equals("") ||
                postcodeField.getText().equals("")) {
            return false;
        }
        return true;
    }

    public int insertAddress() throws SQLException {
        try (Connection con = getConnection()) {
            String query = "SELECT AddressID FROM Addresses WHERE House=? AND Postcode=? LIMIT 1";
            PreparedStatement statement = con.prepareStatement(query);
            statement.clearParameters();
            statement.setString(1, houseField.getText());
            statement.setString(2, streetField.getText());

            ResultSet resultSet = statement.executeQuery();

            // insert new address if address doesn't exist already otherwise return AddressID of existing address
            if (!resultSet.isBeforeFirst()) {
                query = "INSERT INTO Addresses VALUES (null, ?, ?, ?, ?)";

                statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.clearParameters();
                statement.setString(1, houseField.getText());
                statement.setString(2, streetField.getText());
                statement.setString(3, placeField.getText());
                statement.setString(4, postcodeField.getText());

                int insertedRow = statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to create address, no AddressID returned.");
                    }
                }
            } else {
                resultSet.next();

                return resultSet.getInt("AddressID");
            }
        }
    }

    public void clearAddressDetailFields() {
        houseField.setText("");
        streetField.setText("");
        placeField.setText("");
        postcodeField.setText("");
    }
}
