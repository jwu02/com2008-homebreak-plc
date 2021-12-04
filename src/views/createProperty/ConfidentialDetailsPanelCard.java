package views.createProperty;

import models.Property;
import views.InputAddressDetailsPanel;
import views.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;

import static database.OpenConnection.getConnection;

public class ConfidentialDetailsPanelCard extends JPanel implements ActionListener {
    private CreatePropertyPanel backReferencePanel;

    private JTextField name = new JTextField(20);
    private JTextArea description = new JTextArea(5,20);
    private JTextField pricePerNight = new JTextField(20);
    private JCheckBox offerBreakfast = new JCheckBox("Offer breakfast?");

    private InputAddressDetailsPanel inputAddressDetailsPanel;

    public ConfidentialDetailsPanelCard(CreatePropertyPanel backReferencePanel) {
        this.backReferencePanel = backReferencePanel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel propertyDetailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        propertyDetailsPanel.add(new JLabel("Property Details"),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        propertyDetailsPanel.add(new JLabel("Property name"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        propertyDetailsPanel.add(name,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        propertyDetailsPanel.add(new JLabel("Description"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        description.setBorder(new JTextField().getBorder());
        propertyDetailsPanel.add(description,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        propertyDetailsPanel.add(offerBreakfast,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        propertyDetailsPanel.add(new JLabel("Price per Night"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        propertyDetailsPanel.add(pricePerNight,gbc);
        add(propertyDetailsPanel);

        inputAddressDetailsPanel = new InputAddressDetailsPanel();
        add(inputAddressDetailsPanel);

        JButton nextButton1 = new JButton("Next");
        nextButton1.addActionListener(this);
        add(nextButton1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String dialogMessage;

        if (command.equals("Next")) {
            if (name.getText().equals("") || description.getText().equals("") || pricePerNight.getText().equals("") ||
                    !inputAddressDetailsPanel.validateAddressDetails()) {
                dialogMessage = "Please fill in all fields.";
                JOptionPane.showMessageDialog(this, dialogMessage,
                        "Property Details", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    // check if value for the price per night is valid and the property can be created
                    Property property = new Property(0, name.getText(), description.getText(),
                            new BigDecimal(pricePerNight.getText()), offerBreakfast.isSelected(),
                            null, null, null, null);

                    backReferencePanel.getCardLayout().next(backReferencePanel);
                } catch (Exception ex) {
                    ex.printStackTrace();

                    dialogMessage = "Please enter a valid price per night.";
                    JOptionPane.showMessageDialog(this, dialogMessage, "Create property", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    public void insertProperty() throws SQLException {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO Properties VALUES (null, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pst.clearParameters();
            pst.setString(1,name.getText());
            pst.setString(2,description.getText());
            pst.setBigDecimal(3,new BigDecimal(pricePerNight.getText()));
            pst.setBoolean(4,offerBreakfast.isSelected());
            pst.setInt(5, MainFrame.loggedInUser.getUserID());
            pst.setInt(6, inputAddressDetailsPanel.insertAddress());

            pst.executeUpdate();

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    CreatePropertyPanel.lastInsertedPropertyID = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to create property, no PropertyID returned.");
                }
            }
        }
    }

    public void clearConfidentialDetailFields() {
        name.setText("");
        description.setText("");
        pricePerNight.setText("");
        offerBreakfast.setSelected(false);

        inputAddressDetailsPanel.clearAddressDetailFields();
    }
}
