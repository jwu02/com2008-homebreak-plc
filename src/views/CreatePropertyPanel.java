package views;

import database.InsertAddress;
import models.Address;
import models.Bedroom;
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
    private CardLayout cardLayout = new CardLayout();

    private JTextField name = new JTextField(20);
    private JTextArea description = new JTextArea(5,20);
    private JCheckBox offerBreakfast = new JCheckBox("Offer breakfast?");

    private JTextField house = new JTextField(20);
    private JTextField street = new JTextField(20);
    private JTextField place = new JTextField(20);
    private JTextField postcode = new JTextField(20);

    private JPanel chargeBandDetailsPanel = new JPanel();
    private JPanel addedChargeBandsPanel = new JPanel();

    private ArrayList<ChargeBand> chargeBandsList = new ArrayList<>();
    private JTextField startDate = new JTextField("yyyy-mm-dd");
    private JTextField endDate = new JTextField("yyyy-mm-dd");
    private JTextField pricePerNight = new JTextField(10);
    private JTextField serviceCharge = new JTextField(10);
    private JTextField cleaningCharge = new JTextField(10);
    private JButton addChargeBandButton = new JButton("Add Charge Band");

    // facilities
    // sleeping facility
    private ArrayList<Bedroom> bedroomsList = new ArrayList<>();
    private JCheckBox hasBedLinen = new JCheckBox("Bed Linen");
    private JCheckBox hasTowels = new JCheckBox("Towels");
    // bedroom
    private JTextField bed1 = new JTextField(20);
    private JTextField bed2 = new JTextField(20);
    private JButton addBedroomButton = new JButton("Add Bedroom");

    // bathing facility
    private JCheckBox hasHairDryer = new JCheckBox("Hair Dryer");
    private JCheckBox hasShampoo = new JCheckBox("Shampoo");
    private JCheckBox hasToiletPaper = new JCheckBox("Toilet Paper");
    // bathroom
    private JCheckBox hasToilet = new JCheckBox("Toilet");
    private JCheckBox hasBath = new JCheckBox("Bath");
    private JCheckBox hasShower = new JCheckBox("Shower");
    private JCheckBox isSharedWithHost = new JCheckBox("Shared With Host?");
    private JButton addBathroomButton = new JButton("Add Bathroom");

    // kitchen facility
    private JCheckBox hasRefrigerator = new JCheckBox("Refrigerator");
    private JCheckBox hasMicrowave = new JCheckBox("Microwave");
    private JCheckBox hasOven = new JCheckBox("Oven");
    private JCheckBox hasStove = new JCheckBox("Stove");
    private JCheckBox hasDishwasher = new JCheckBox("Dishwasher");
    private JCheckBox hasTableware = new JCheckBox("Tableware");
    private JCheckBox hasCookware = new JCheckBox("Cookware");
    private JCheckBox hasBasicProvisions = new JCheckBox("Basic Provisions");

    // living facility
    private JCheckBox hasWifi = new JCheckBox("Wifi");
    private JCheckBox hasTelevision = new JCheckBox("Television");
    private JCheckBox hasSatellite = new JCheckBox("Satellite");
    private JCheckBox hasStreaming = new JCheckBox("Streaming");
    private JCheckBox hasDvdPlayer = new JCheckBox("DVD Player");
    private JCheckBox hasBoardGames = new JCheckBox("Board Games");

    // utility facility
    private JCheckBox hasHeating = new JCheckBox("Heating");
    private JCheckBox hasWashingMachine = new JCheckBox("Washing Machine");
    private JCheckBox hasDryingMachine = new JCheckBox("Drying Machine");
    private JCheckBox hasFireExtinguisher = new JCheckBox("Fire Extinguisher");
    private JCheckBox hasSmokeAlarm = new JCheckBox("Smoke Alarm");
    private JCheckBox hasFirstAidKit = new JCheckBox("First Aid Kit");

    // outdoor facility
    private JCheckBox hasFreeOnsiteParking = new JCheckBox("Free Onsite Parking");
    private JCheckBox hasOnRoadParking = new JCheckBox("On Road Parking");
    private JCheckBox hasPaidCarPark = new JCheckBox("Paid Car Park");
    private JCheckBox hasPatio = new JCheckBox("Patio");
    private JCheckBox hasBarbecue = new JCheckBox("Barbecue");

    private JButton createPropertyButton = new JButton("Create Property");

    private int lastInsertedPropertyID;

    public CreatePropertyPanel() {
        setLayout(cardLayout);

        // card 1
        JPanel confidentialDetailsPanel = new JPanel();
        confidentialDetailsPanel.setLayout(new BoxLayout(confidentialDetailsPanel, BoxLayout.Y_AXIS));

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
        confidentialDetailsPanel.add(propertyDetailsPanel);

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
        confidentialDetailsPanel.add(addressDetailsPanel);

        JButton nextButton1 = new JButton("Next");
        nextButton1.addActionListener(e -> {
            if (name.getText().equals("") || description.getText().equals("") || house.getText().equals("") ||
                    street.getText().equals("") || place.getText().equals("") || postcode.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                        "Property Details", JOptionPane.WARNING_MESSAGE);
            } else {
                cardLayout.next(this);
            }
        });
        confidentialDetailsPanel.add(nextButton1);
        add("propertyDetails",confidentialDetailsPanel);

        // card 2
        chargeBandDetailsPanel.setLayout(new BoxLayout(chargeBandDetailsPanel,BoxLayout.Y_AXIS));

        JPanel chargeBandDetailsInputPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        chargeBandDetailsInputPanel.add(new JLabel("Charge Band Details"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        chargeBandDetailsInputPanel.add(new JLabel("Start Date"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        chargeBandDetailsInputPanel.add(startDate,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        chargeBandDetailsInputPanel.add(new JLabel("End Date"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        chargeBandDetailsInputPanel.add(endDate,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        chargeBandDetailsInputPanel.add(new JLabel("Price per Night"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        chargeBandDetailsInputPanel.add(pricePerNight,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        chargeBandDetailsInputPanel.add(new JLabel("Service Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        chargeBandDetailsInputPanel.add(serviceCharge,gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        chargeBandDetailsInputPanel.add(new JLabel("Cleaning Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        chargeBandDetailsInputPanel.add(cleaningCharge,gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
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
        chargeBandDetailsInputPanel.add(addChargeBandButton,gbc);

        chargeBandDetailsPanel.add(chargeBandDetailsInputPanel);
        addedChargeBandsPanel.setLayout(new BoxLayout(addedChargeBandsPanel,BoxLayout.Y_AXIS));
        chargeBandDetailsPanel.add(addedChargeBandsPanel);
        JButton nextButton2 = new JButton("Next");
        nextButton2.addActionListener(e -> {
            if (chargeBandsList.size() == 0) {
                JOptionPane.showMessageDialog(this, "Please add at least one charge band.",
                        "Charge Band Details", JOptionPane.WARNING_MESSAGE);
            } else {
                cardLayout.next(this);
            }
        });
        chargeBandDetailsPanel.add(nextButton2);
        chargeBandDetailsPanel.add(newPreviousButton());

        add("chargeBandsDetails",chargeBandDetailsPanel);

        // card 3
        JPanel facilitiesDetailsPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        facilitiesDetailsPanel.add(new JLabel("Facilities Details"));

        // sleeping facility
        JPanel sleepingFacilityPanel = new JPanel();
        sleepingFacilityPanel.setLayout(new BoxLayout(sleepingFacilityPanel,BoxLayout.Y_AXIS));
        sleepingFacilityPanel.add(new JLabel("Sleeping Facility"));
        sleepingFacilityPanel.add(hasBedLinen);
        sleepingFacilityPanel.add(hasTowels);
        // add bedroom
        JPanel addBedroomsPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        addBedroomsPanel.add(new JLabel("Add Bedrooms"),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        addBedroomsPanel.add(new JLabel("Bed 1"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        addBedroomsPanel.add(bed1,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        addBedroomsPanel.add(new JLabel("Bed 2"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        addBedroomsPanel.add(bed2,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        addBedroomsPanel.add(addBedroomButton,gbc);
        sleepingFacilityPanel.add(addBedroomsPanel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        facilitiesDetailsPanel.add(sleepingFacilityPanel,gbc);

        // bathing facility
        JPanel bathingFacilityPanel = new JPanel();
        bathingFacilityPanel.setLayout(new BoxLayout(bathingFacilityPanel,BoxLayout.Y_AXIS));
        bathingFacilityPanel.add(new JLabel("Bathing Facility"));
        bathingFacilityPanel.add(hasHairDryer);
        bathingFacilityPanel.add(hasShampoo);
        bathingFacilityPanel.add(hasToiletPaper);
        // add bathroom
        JPanel addBathroomsPanel = new JPanel();
        addBathroomsPanel.setLayout(new BoxLayout(addBathroomsPanel,BoxLayout.Y_AXIS));
        addBathroomsPanel.add(new JLabel("Add Bathrooms"));
        addBathroomsPanel.add(hasToilet);
        addBathroomsPanel.add(hasBath);
        addBathroomsPanel.add(hasShower);
        addBathroomsPanel.add(isSharedWithHost);
        addBathroomsPanel.add(addBathroomButton);
        bathingFacilityPanel.add(addBathroomsPanel);
        gbc.gridx = 1;
        gbc.gridy = 1;
        facilitiesDetailsPanel.add(bathingFacilityPanel,gbc);

        // kitchen facility
        JPanel kitchenFacilityPanel = new JPanel();
        kitchenFacilityPanel.setLayout(new BoxLayout(kitchenFacilityPanel,BoxLayout.Y_AXIS));
        kitchenFacilityPanel.add(new JLabel("Kitchen Facility"));
        kitchenFacilityPanel.add(hasRefrigerator);
        kitchenFacilityPanel.add(hasMicrowave);
        kitchenFacilityPanel.add(hasOven);
        kitchenFacilityPanel.add(hasStove);
        kitchenFacilityPanel.add(hasDishwasher);
        kitchenFacilityPanel.add(hasTableware);
        kitchenFacilityPanel.add(hasCookware);
        kitchenFacilityPanel.add(hasBasicProvisions);
        gbc.gridx = 0;
        gbc.gridy = 2;
        facilitiesDetailsPanel.add(kitchenFacilityPanel,gbc);

        // living facility
        JPanel livingFacilityPanel = new JPanel();
        livingFacilityPanel.setLayout(new BoxLayout(livingFacilityPanel,BoxLayout.Y_AXIS));
        livingFacilityPanel.add(new JLabel("Living Facility"));
        livingFacilityPanel.add(hasWifi);
        livingFacilityPanel.add(hasTelevision);
        livingFacilityPanel.add(hasSatellite);
        livingFacilityPanel.add(hasStreaming);
        livingFacilityPanel.add(hasDvdPlayer);
        livingFacilityPanel.add(hasBoardGames);
        gbc.gridx = 1;
        gbc.gridy = 2;
        facilitiesDetailsPanel.add(livingFacilityPanel,gbc);

        // utility facility
        JPanel utilityFacilityPanel = new JPanel();
        utilityFacilityPanel.setLayout(new BoxLayout(utilityFacilityPanel,BoxLayout.Y_AXIS));
        utilityFacilityPanel.add(new JLabel("Utility Facility"));
        utilityFacilityPanel.add(hasHeating);
        utilityFacilityPanel.add(hasWashingMachine);
        utilityFacilityPanel.add(hasDryingMachine);
        utilityFacilityPanel.add(hasFireExtinguisher);
        utilityFacilityPanel.add(hasSmokeAlarm);
        utilityFacilityPanel.add(hasFirstAidKit);
        gbc.gridx = 0;
        gbc.gridy = 3;
        facilitiesDetailsPanel.add(utilityFacilityPanel,gbc);

        // outdoors facility
        JPanel outdoorFacilityPanel = new JPanel();
        outdoorFacilityPanel.setLayout(new BoxLayout(outdoorFacilityPanel,BoxLayout.Y_AXIS));
        outdoorFacilityPanel.add(new JLabel("Outdoor Facility"));
        outdoorFacilityPanel.add(hasFreeOnsiteParking);
        outdoorFacilityPanel.add(hasOnRoadParking);
        outdoorFacilityPanel.add(hasPaidCarPark);
        outdoorFacilityPanel.add(hasPatio);
        outdoorFacilityPanel.add(hasBarbecue);
        gbc.gridx = 1;
        gbc.gridy = 3;
        facilitiesDetailsPanel.add(outdoorFacilityPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        facilitiesDetailsPanel.add(newPreviousButton(),gbc);

        createPropertyButton.addActionListener(e -> {
            if (bedroomsList.size() == 0) {
                JOptionPane.showMessageDialog(this, "Please add at least one bedroom.",
                        "Facilities Details", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    insertProperty();
                    insertChargeBands();
                    insertFacilities();
                    clearPropertyFields();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error adding property",
                            "Add Property", JOptionPane.WARNING_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        facilitiesDetailsPanel.add(createPropertyButton,gbc);

        add("facilitiesDetails",facilitiesDetailsPanel);
    }

    public JButton newPreviousButton() {
        JButton nextButton = new JButton("Previous");
        nextButton.addActionListener(e -> {
            cardLayout.previous(this);
        });

        return nextButton;
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
        JPanel chargeBandDetailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        chargeBandDetailsPanel.add(new JLabel("Start Date"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        chargeBandDetailsPanel.add(new JLabel(startDate.getText()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        chargeBandDetailsPanel.add(new JLabel("End Date"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        chargeBandDetailsPanel.add(new JLabel(endDate.getText()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        chargeBandDetailsPanel.add(new JLabel("Price per Night"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        chargeBandDetailsPanel.add(new JLabel(pricePerNight.getText()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        chargeBandDetailsPanel.add(new JLabel("Service Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        chargeBandDetailsPanel.add(new JLabel(serviceCharge.getText()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        chargeBandDetailsPanel.add(new JLabel("Cleaning Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        chargeBandDetailsPanel.add(new JLabel(cleaningCharge.getText()),gbc);
        addedChargeBandsPanel.add(chargeBandDetailsPanel,0);
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
        clearFacilityFields();
    }

    public void clearChargeBandFields() {
        startDate.setText("yyyy-mm-dd");
        endDate.setText("yyyy-mm-dd");
        pricePerNight.setText("");
        serviceCharge.setText("");
        cleaningCharge.setText("");
    };

    public void clearFacilityFields() {

    }

    public void insertProperty() throws SQLException {
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

            pst.executeUpdate();

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    lastInsertedPropertyID = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to create property, no PropertyID returned.");
                }
            }
        }
    }

    public void insertChargeBands() throws SQLException {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO ChargeBands VALUES (null, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            for (ChargeBand cb : chargeBandsList) {
                pst.clearParameters();
                pst.setDate(1,Date.valueOf(cb.getStartDate()));
                pst.setDate(2,Date.valueOf(cb.getEndDate()));
                pst.setBigDecimal(3,cb.getPricePerNight());
                pst.setBigDecimal(4,cb.getServiceCharge());
                pst.setBigDecimal(5,cb.getCleaningCharge());
                pst.setInt(6,lastInsertedPropertyID);
                pst.addBatch();
            }
            pst.executeBatch();

            addedChargeBandsPanel.removeAll();
            revalidate();
            repaint();
        }
    }

    public void insertFacilities() throws SQLException {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO SleepingFacilities VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1,lastInsertedPropertyID);
            pst.setBoolean(2,hasBedLinen.isSelected());
            pst.setBoolean(3,hasTowels.isSelected());
            pst.addBatch();

            query = "INSERT INTO BathingFacilities VALUES (?, ?, ?, ?)";
            pst = con.prepareStatement(query);
            pst.setInt(1,lastInsertedPropertyID);
            pst.setBoolean(2,hasHairDryer.isSelected());
            pst.setBoolean(3,hasShampoo.isSelected());
            pst.setBoolean(4,hasToiletPaper.isSelected());
            pst.addBatch();

            query = "INSERT INTO KitchenFacilities VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(query);
            pst.setInt(1,lastInsertedPropertyID);
            pst.setBoolean(2,hasRefrigerator.isSelected());
            pst.setBoolean(3,hasMicrowave.isSelected());
            pst.setBoolean(4,hasOven.isSelected());
            pst.setBoolean(5,hasStove.isSelected());
            pst.setBoolean(6,hasDishwasher.isSelected());
            pst.setBoolean(7,hasTableware.isSelected());
            pst.setBoolean(8,hasCookware.isSelected());
            pst.setBoolean(9,hasBasicProvisions.isSelected());
            pst.addBatch();

            query = "INSERT INTO LivingFacilities VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(query);
            pst.setInt(1,lastInsertedPropertyID);
            pst.setBoolean(2,hasWifi.isSelected());
            pst.setBoolean(3,hasTelevision.isSelected());
            pst.setBoolean(4,hasSatellite.isSelected());
            pst.setBoolean(5,hasStreaming.isSelected());
            pst.setBoolean(6,hasDvdPlayer.isSelected());
            pst.setBoolean(7,hasBoardGames.isSelected());
            pst.addBatch();

            query = "INSERT INTO UtilityFacilities VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(query);
            pst.setInt(1,lastInsertedPropertyID);
            pst.setBoolean(2,hasHeating.isSelected());
            pst.setBoolean(3,hasWashingMachine.isSelected());
            pst.setBoolean(4,hasDryingMachine.isSelected());
            pst.setBoolean(5,hasFireExtinguisher.isSelected());
            pst.setBoolean(6,hasSmokeAlarm.isSelected());
            pst.setBoolean(7,hasFirstAidKit.isSelected());
            pst.addBatch();

            query = "INSERT INTO LivingFacilities VALUES (?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(query);
            pst.setInt(1,lastInsertedPropertyID);
            pst.setBoolean(2,hasFreeOnsiteParking.isSelected());
            pst.setBoolean(3,hasOnRoadParking.isSelected());
            pst.setBoolean(4,hasPaidCarPark.isSelected());
            pst.setBoolean(5,hasPatio.isSelected());
            pst.setBoolean(6,hasBarbecue.isSelected());
            pst.addBatch();

            pst.executeBatch();
        }

        insertBedrooms();
        insertBathrooms();
    }

    public void insertBedrooms() throws SQLException {

    }

    public void insertBathrooms() throws SQLException {

    }
}