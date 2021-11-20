package views;

import database.InsertAddress;
import models.Address;
import models.ChargeBand;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static database.OpenConnection.getConnection;

public class CreatePropertyPanel extends JPanel {
    private JTextField name = new JTextField(20);
    private JTextArea description = new JTextArea();
    private JCheckBox offerBreakfast = new JCheckBox();

    private JTextField house = new JTextField(20);
    private JTextField street = new JTextField(20);
    private JTextField place = new JTextField(20);
    private JTextField postcode = new JTextField(20);

    private JPanel chargeBandSection = new JPanel();
    private JPanel addedChargeBandSection = new JPanel();

    private ArrayList<ChargeBand> chargeBandsList = new ArrayList<>();
    private JTextField startDate = new JTextField("yyyy-mm-dd");
    private JTextField endDate = new JTextField("yyyy-mm-dd");
    private JTextField pricePerNight = new JTextField(10);
    private JTextField serviceCharge = new JTextField(10);
    private JTextField cleaningCharge = new JTextField(10);
    private JButton addChargeBandButton = new JButton("Add Charge Band");

    private JButton createPropertyButton = new JButton("Create Property");

    public CreatePropertyPanel() {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        add(new JLabel("Property Details"));
        JPanel propertySection = new JPanel(new GridLayout(4,2));
        propertySection.add(new JLabel("Property name"));
        propertySection.add(name);
        propertySection.add(new JLabel("Description"));
        propertySection.add(description);
        propertySection.add(new JLabel("Offer breakfast?"));
        propertySection.add(offerBreakfast);
        add(propertySection);

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

        chargeBandSection.setLayout(new BoxLayout(chargeBandSection,BoxLayout.Y_AXIS));
        chargeBandSection.add(new JLabel("Charge Band Details"));
        addedChargeBandSection.setLayout(new BoxLayout(addedChargeBandSection,BoxLayout.Y_AXIS));
        chargeBandSection.add(addedChargeBandSection);

        JPanel chargeBandInputSection = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        chargeBandInputSection.add(new JLabel("Start Date"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        chargeBandInputSection.add(startDate,gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        chargeBandInputSection.add(new JLabel("End Date"),gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        chargeBandInputSection.add(endDate,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        chargeBandInputSection.add(new JLabel("Price per Night"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        chargeBandInputSection.add(pricePerNight,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        chargeBandInputSection.add(new JLabel("Service Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        chargeBandInputSection.add(serviceCharge,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        chargeBandInputSection.add(new JLabel("Cleaning Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        chargeBandInputSection.add(cleaningCharge,gbc);
        chargeBandSection.add(chargeBandInputSection);
        addChargeBandButton.addActionListener(e -> {
            if (startDate.getText().equals("yyyy-mm-dd") || endDate.getText().equals("yyyy-mm-dd") ||
                    pricePerNight.getText().equals("") || serviceCharge.getText().equals("") ||
                    cleaningCharge.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                        "Add Charge Band", JOptionPane.WARNING_MESSAGE);
            } else {
                addChargeBandToList();
            }
        });
        chargeBandSection.add(addChargeBandButton);
        add(chargeBandSection);

        createPropertyButton.addActionListener(e -> {
            if (name.getText().equals("") || description.getText().equals("") || house.getText().equals("") ||
                    street.getText().equals("") || place.getText().equals("") || postcode.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                        "Add Charge Band", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    insertChargeBands(); // in turn calls insertProperty()
                    clearPropertyFields();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error adding property",
                            "Add Property", JOptionPane.WARNING_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        add(createPropertyButton);
    }

    public void addChargeBandToList() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(this.startDate.getText(), dtf);
            LocalDate endDate = LocalDate.parse(this.endDate.getText(), dtf);
            BigDecimal pricePerNight = new BigDecimal(this.pricePerNight.getText());
            BigDecimal serviceCharge = new BigDecimal(this.serviceCharge.getText());
            BigDecimal cleaningCharge = new BigDecimal(this.cleaningCharge.getText());
            ChargeBand newChargeBand = new ChargeBand(startDate,endDate,pricePerNight,serviceCharge,cleaningCharge);
            chargeBandsList.add(newChargeBand);
            JOptionPane.showMessageDialog(this,"Added charge band.");

            addChargeBandToPanel();
            clearChargeBandFields();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"Error adding charge band.","Add Charge Band",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void addChargeBandToPanel() {
        JPanel chargeBandInputSection = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        chargeBandInputSection.add(new JLabel("Start Date"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        chargeBandInputSection.add(new JLabel(startDate.getText()),gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        chargeBandInputSection.add(new JLabel("End Date"),gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        chargeBandInputSection.add(new JLabel(endDate.getText()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        chargeBandInputSection.add(new JLabel("Price per Night"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        chargeBandInputSection.add(new JLabel(pricePerNight.getText()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        chargeBandInputSection.add(new JLabel("Service Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        chargeBandInputSection.add(new JLabel(serviceCharge.getText()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        chargeBandInputSection.add(new JLabel("Cleaning Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        chargeBandInputSection.add(new JLabel(cleaningCharge.getText()),gbc);
        addedChargeBandSection.add(chargeBandInputSection,0);
        revalidate();
        repaint();
    }

    public void clearPropertyFields() {
        name.setText("");
        description.setText("");
        offerBreakfast.setSelected(false);
        house.setText("");
        street.setText("");
        place.setText("");
        postcode.setText("");

        clearChargeBandFields();
    }

    public void clearChargeBandFields() {
        startDate.setText("yyyy-mm-dd");
        endDate.setText("yyyy-mm-dd");
        pricePerNight.setText("");
        serviceCharge.setText("");
        cleaningCharge.setText("");
    };

    public void insertChargeBands() throws SQLException {
        try (Connection con = getConnection()) {
            for (ChargeBand cb : chargeBandsList) {
                String query = "INSERT INTO ChargeBands VALUES (null, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);

                pst.clearParameters();
                pst.setDate(1,Date.valueOf(cb.getStartDate()));
                pst.setDate(2,Date.valueOf(cb.getEndDate()));
                pst.setBigDecimal(3,cb.getPricePerNight());
                pst.setBigDecimal(4,cb.getServiceCharge());
                pst.setBigDecimal(5,cb.getCleaningCharge());
                pst.setInt(6,insertProperty());
                pst.executeUpdate();
            }

            chargeBandSection.remove(addedChargeBandSection);
            addedChargeBandSection = new JPanel();
            addedChargeBandSection.setLayout(new BoxLayout(addedChargeBandSection,BoxLayout.Y_AXIS));
            chargeBandSection.add(addedChargeBandSection, 1);
            revalidate();
            repaint();
        }
    }

    public int insertProperty() throws SQLException {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO Properties VALUES (null, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            pst.clearParameters();
            pst.setString(1,name.getText());
            pst.setString(2,description.getText());
            pst.setBoolean(3,offerBreakfast.isSelected());
            pst.setInt(4,MainFrame.loggedInUser.getUserID());
            Address addressToInsert = new Address(house.getText(),street.getText(),place.getText(),postcode.getText());
            pst.setInt(5, InsertAddress.insertAddress(addressToInsert));

            int insertedRow = pst.executeUpdate();

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to create property, no PropertyID returned.");
                }
            }
        }
    }
}