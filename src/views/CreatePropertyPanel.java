package views;

import database.InsertAddress;
import models.Address;
import models.facilities.Bathroom;
import models.facilities.Bedroom;
import models.ChargeBand;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static database.OpenConnection.getConnection;

public class CreatePropertyPanel extends JPanel {
    private CardLayout cardLayout = new CardLayout();

    private JTextField name = new JTextField(20);
    private JTextArea description = new JTextArea(5,20);
    private JTextField pricePerNight = new JTextField(20);
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
    private JTextField serviceCharge = new JTextField(10);
    private JTextField cleaningCharge = new JTextField(10);
    private JButton addChargeBandButton = new JButton("Add Charge Band");

    // facilities
    // sleeping facility
    JPanel sleepingFacilityPanel = new JPanel();
    private JCheckBox hasBedLinen = new JCheckBox("Bed Linen");
    private JCheckBox hasTowels = new JCheckBox("Towels");
    // bedroom
    private ArrayList<Bedroom> bedroomsList = new ArrayList<>();
    private JPanel addBedroomsPanel = new JPanel(new GridBagLayout());
    private JRadioButton singleBed1RadioButton = new JRadioButton("single");
    private JRadioButton doubleBed1RadioButton = new JRadioButton("double");
    private JRadioButton kingsizeBed1RadioButton = new JRadioButton("kingsize");
    private JRadioButton bunkBed1RadioButton = new JRadioButton("bunk");

    private JRadioButton singleBed2RadioButton = new JRadioButton("single");
    private JRadioButton doubleBed2RadioButton = new JRadioButton("double");
    private JRadioButton kingsizeBed2RadioButton = new JRadioButton("kingsize");
    private JRadioButton bunkBed2RadioButton = new JRadioButton("bunk");
    private JRadioButton noBed2RadioButton = new JRadioButton("no bed 2");
    private JButton addBedroomButton = new JButton("Add Bedroom");

    // bathing facility
    private JPanel bathingFacilityPanel = new JPanel();
    private JCheckBox hasHairDryer = new JCheckBox("Hair Dryer");
    private JCheckBox hasShampoo = new JCheckBox("Shampoo");
    private JCheckBox hasToiletPaper = new JCheckBox("Toilet Paper");
    // bathroom
    private ArrayList <Bathroom> bathroomsList = new ArrayList<>();
    private JPanel addBathroomsPanel = new JPanel();
    private JCheckBox hasToilet = new JCheckBox("Toilet");
    private JCheckBox hasBath = new JCheckBox("Bath");
    private JCheckBox hasShower = new JCheckBox("Shower");
    private JCheckBox isSharedWithHost = new JCheckBox("Shared With Host?");
    private JButton addBathroomButton = new JButton("Add Bathroom");

    // kitchen facility
    private JPanel kitchenFacilityPanel = new JPanel();
    private JCheckBox hasRefrigerator = new JCheckBox("Refrigerator");
    private JCheckBox hasMicrowave = new JCheckBox("Microwave");
    private JCheckBox hasOven = new JCheckBox("Oven");
    private JCheckBox hasStove = new JCheckBox("Stove");
    private JCheckBox hasDishwasher = new JCheckBox("Dishwasher");
    private JCheckBox hasTableware = new JCheckBox("Tableware");
    private JCheckBox hasCookware = new JCheckBox("Cookware");
    private JCheckBox hasBasicProvisions = new JCheckBox("Basic Provisions");

    // living facility
    private JPanel livingFacilityPanel = new JPanel();
    private JCheckBox hasWifi = new JCheckBox("Wifi");
    private JCheckBox hasTelevision = new JCheckBox("Television");
    private JCheckBox hasSatellite = new JCheckBox("Satellite");
    private JCheckBox hasStreaming = new JCheckBox("Streaming");
    private JCheckBox hasDvdPlayer = new JCheckBox("DVD Player");
    private JCheckBox hasBoardGames = new JCheckBox("Board Games");

    // utility facility
    private JPanel utilityFacilityPanel = new JPanel();
    private JCheckBox hasHeating = new JCheckBox("Heating");
    private JCheckBox hasWashingMachine = new JCheckBox("Washing Machine");
    private JCheckBox hasDryingMachine = new JCheckBox("Drying Machine");
    private JCheckBox hasFireExtinguisher = new JCheckBox("Fire Extinguisher");
    private JCheckBox hasSmokeAlarm = new JCheckBox("Smoke Alarm");
    private JCheckBox hasFirstAidKit = new JCheckBox("First Aid Kit");

    // outdoor facility
    private JPanel outdoorFacilityPanel = new JPanel();
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
        gbc.gridx = 0;
        gbc.gridy = 4;
        propertyDetailsPanel.add(new JLabel("Price per Night"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        propertyDetailsPanel.add(pricePerNight,gbc);
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
            if (name.getText().equals("") || description.getText().equals("") || pricePerNight.getText().equals("") ||
                    house.getText().equals("") || street.getText().equals("") || place.getText().equals("") ||
                    postcode.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                        "Property Details", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    // check if value for the price per night is valid
                    new BigDecimal(pricePerNight.getText());
                    cardLayout.next(this);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid price per night",
                            "Create property", JOptionPane.WARNING_MESSAGE);
                }
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
        chargeBandDetailsInputPanel.add(new JLabel("Service Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        chargeBandDetailsInputPanel.add(serviceCharge,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        chargeBandDetailsInputPanel.add(new JLabel("Cleaning Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        chargeBandDetailsInputPanel.add(cleaningCharge,gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        addChargeBandButton.addActionListener(e -> {
            if (startDate.getText().equals("yyyy-mm-dd") || endDate.getText().equals("yyyy-mm-dd") ||
                    serviceCharge.getText().equals("") || cleaningCharge.getText().equals("")) {
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
        sleepingFacilityPanel.setLayout(new BoxLayout(sleepingFacilityPanel,BoxLayout.Y_AXIS));
        sleepingFacilityPanel.add(new JLabel("Sleeping Facility"));
        sleepingFacilityPanel.add(hasBedLinen);
        sleepingFacilityPanel.add(hasTowels);
        // add bedroom
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        addBedroomsPanel.add(new JLabel("Add Bedrooms"),gbc);
        // bed 1
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        addBedroomsPanel.add(new JLabel("Bed 1"),gbc);
        ButtonGroup bed1ButtonGroup = new ButtonGroup();
        singleBed1RadioButton.setActionCommand("single");
        bed1ButtonGroup.add(singleBed1RadioButton);
        doubleBed1RadioButton.setActionCommand("double");
        bed1ButtonGroup.add(doubleBed1RadioButton);
        kingsizeBed1RadioButton.setActionCommand("kingsize");
        bed1ButtonGroup.add(kingsizeBed1RadioButton);
        bunkBed1RadioButton.setActionCommand("bunk");
        bed1ButtonGroup.add(bunkBed1RadioButton);
        gbc.gridx = 0;
        gbc.gridy = 2;
        addBedroomsPanel.add(singleBed1RadioButton,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        addBedroomsPanel.add(doubleBed1RadioButton,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        addBedroomsPanel.add(kingsizeBed1RadioButton,gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        addBedroomsPanel.add(bunkBed1RadioButton,gbc);
        // bed2
        gbc.gridx = 1;
        gbc.gridy = 1;
        addBedroomsPanel.add(new JLabel("Bed 2"),gbc);
        ButtonGroup bed2ButtonGroup = new ButtonGroup();
        singleBed2RadioButton.setActionCommand("single");
        bed2ButtonGroup.add(singleBed2RadioButton);
        doubleBed2RadioButton.setActionCommand("double");
        bed2ButtonGroup.add(doubleBed2RadioButton);
        kingsizeBed2RadioButton.setActionCommand("kingsize");
        bed2ButtonGroup.add(kingsizeBed2RadioButton);
        bunkBed2RadioButton.setActionCommand("bunk");
        bed2ButtonGroup.add(bunkBed2RadioButton);
        noBed2RadioButton.setActionCommand("");
        bed2ButtonGroup.add(noBed2RadioButton);
        gbc.gridx = 1;
        gbc.gridy = 2;
        addBedroomsPanel.add(singleBed2RadioButton,gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        addBedroomsPanel.add(doubleBed2RadioButton,gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        addBedroomsPanel.add(kingsizeBed2RadioButton,gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        addBedroomsPanel.add(bunkBed2RadioButton,gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        addBedroomsPanel.add(noBed2RadioButton,gbc);

        addBedroomButton.addActionListener(e -> {
            if (bed1ButtonGroup.getSelection() == null || bed2ButtonGroup.getSelection() == null) {
                JOptionPane.showMessageDialog(this, "Please select all options.",
                        "Add Bedroom", JOptionPane.WARNING_MESSAGE);
            } else {
                bedroomsList.add(new Bedroom(bed1ButtonGroup.getSelection().getActionCommand(),
                        bed2ButtonGroup.getSelection().getActionCommand()));

                JOptionPane.showMessageDialog(this,"Added bedroom.");

                bed1ButtonGroup.clearSelection();
                bed2ButtonGroup.clearSelection();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        addBedroomsPanel.add(addBedroomButton,gbc);
        sleepingFacilityPanel.add(addBedroomsPanel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        facilitiesDetailsPanel.add(sleepingFacilityPanel,gbc);

        // bathing facility
        bathingFacilityPanel.setLayout(new BoxLayout(bathingFacilityPanel,BoxLayout.Y_AXIS));
        bathingFacilityPanel.add(new JLabel("Bathing Facility"));
        bathingFacilityPanel.add(hasHairDryer);
        bathingFacilityPanel.add(hasShampoo);
        bathingFacilityPanel.add(hasToiletPaper);
        // add bathroom
        addBathroomsPanel.setLayout(new BoxLayout(addBathroomsPanel,BoxLayout.Y_AXIS));
        addBathroomsPanel.add(new JLabel("Add Bathrooms"));
        addBathroomsPanel.add(hasToilet);
        addBathroomsPanel.add(hasBath);
        addBathroomsPanel.add(hasShower);
        addBathroomsPanel.add(isSharedWithHost);
        addBathroomButton.addActionListener(e -> {
            bathroomsList.add(new Bathroom(hasToilet.isSelected(),hasBath.isSelected(),hasShower.isSelected(),
                    isSharedWithHost.isSelected()));

            JOptionPane.showMessageDialog(this,"Added bathroom.");

            hasToilet.setSelected(false);
            hasBath.setSelected(false);
            hasShower.setSelected(false);
            isSharedWithHost.setSelected(false);
        });
        addBathroomsPanel.add(addBathroomButton);
        bathingFacilityPanel.add(addBathroomsPanel);
        gbc.gridx = 1;
        gbc.gridy = 1;
        facilitiesDetailsPanel.add(bathingFacilityPanel,gbc);

        // kitchen facility
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
                    JOptionPane.showMessageDialog(this,"Property created.");

                    cardLayout.show(this, "propertyDetails");
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
            BigDecimal serviceCharge = new BigDecimal(this.serviceCharge.getText());
            BigDecimal cleaningCharge = new BigDecimal(this.cleaningCharge.getText());
            ChargeBand newChargeBand = new ChargeBand(startDate,endDate,serviceCharge,cleaningCharge);
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
        chargeBandDetailsPanel.add(new JLabel("Service Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        chargeBandDetailsPanel.add(new JLabel(serviceCharge.getText()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        chargeBandDetailsPanel.add(new JLabel("Cleaning Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        chargeBandDetailsPanel.add(new JLabel(cleaningCharge.getText()),gbc);
        addedChargeBandsPanel.add(chargeBandDetailsPanel,0);
        revalidate();
        repaint();
    }

    public void clearPropertyFields() {
        name.setText("");
        description.setText("");
        pricePerNight.setText("");
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
        serviceCharge.setText("");
        cleaningCharge.setText("");
    };

    public void clearFacilityFields() {
        ArrayList<Component> componentsList = new ArrayList<>();
        componentsList.addAll(Arrays.asList(sleepingFacilityPanel.getComponents()));
        componentsList.addAll(Arrays.asList(addBedroomsPanel.getComponents()));
        componentsList.addAll(Arrays.asList(bathingFacilityPanel.getComponents()));
        componentsList.addAll(Arrays.asList(addBathroomsPanel.getComponents()));
        componentsList.addAll(Arrays.asList(kitchenFacilityPanel.getComponents()));
        componentsList.addAll(Arrays.asList(livingFacilityPanel.getComponents()));
        componentsList.addAll(Arrays.asList(utilityFacilityPanel.getComponents()));
        componentsList.addAll(Arrays.asList(outdoorFacilityPanel.getComponents()));
        for(Component c : componentsList) {
            if (c instanceof JCheckBox) {
                ((JCheckBox) c).setSelected(false);
            }
        }
    }

    public void insertProperty() throws SQLException {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO Properties VALUES (null, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            pst.clearParameters();
            pst.setString(1,name.getText());
            pst.setString(2,description.getText());
            pst.setBigDecimal(3,new BigDecimal(pricePerNight.getText()));
            pst.setBoolean(4,offerBreakfast.isSelected());
            pst.setInt(5,MainFrame.loggedInUser.getUserID());
            Address addressToInsert = new Address(0,house.getText(),street.getText(),place.getText(),postcode.getText());
            pst.setInt(6, InsertAddress.insertAddress(addressToInsert));

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
            String query = "INSERT INTO ChargeBands VALUES (null, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            for (ChargeBand cb : chargeBandsList) {
                pst.clearParameters();
                pst.setDate(1,Date.valueOf(cb.getStartDate()));
                pst.setDate(2,Date.valueOf(cb.getEndDate()));
                pst.setBigDecimal(3,cb.getServiceCharge());
                pst.setBigDecimal(4,cb.getCleaningCharge());
                pst.setInt(5,lastInsertedPropertyID);
                pst.executeUpdate();
            }

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
            pst.executeUpdate();

            query = "INSERT INTO BathingFacilities VALUES (?, ?, ?, ?)";
            pst.clearParameters();
            pst = con.prepareStatement(query);
            pst.setInt(1,lastInsertedPropertyID);
            pst.setBoolean(2,hasHairDryer.isSelected());
            pst.setBoolean(3,hasShampoo.isSelected());
            pst.setBoolean(4,hasToiletPaper.isSelected());
            pst.executeUpdate();

            query = "INSERT INTO KitchenFacilities VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst.clearParameters();
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
            pst.executeUpdate();

            query = "INSERT INTO LivingFacilities VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst.clearParameters();
            pst = con.prepareStatement(query);
            pst.setInt(1,lastInsertedPropertyID);
            pst.setBoolean(2,hasWifi.isSelected());
            pst.setBoolean(3,hasTelevision.isSelected());
            pst.setBoolean(4,hasSatellite.isSelected());
            pst.setBoolean(5,hasStreaming.isSelected());
            pst.setBoolean(6,hasDvdPlayer.isSelected());
            pst.setBoolean(7,hasBoardGames.isSelected());
            pst.executeUpdate();

            query = "INSERT INTO UtilityFacilities VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst.clearParameters();
            pst = con.prepareStatement(query);
            pst.setInt(1,lastInsertedPropertyID);
            pst.setBoolean(2,hasHeating.isSelected());
            pst.setBoolean(3,hasWashingMachine.isSelected());
            pst.setBoolean(4,hasDryingMachine.isSelected());
            pst.setBoolean(5,hasFireExtinguisher.isSelected());
            pst.setBoolean(6,hasSmokeAlarm.isSelected());
            pst.setBoolean(7,hasFirstAidKit.isSelected());
            pst.executeUpdate();

            query = "INSERT INTO OutdoorFacilities VALUES (?, ?, ?, ?, ?, ?)";
            pst.clearParameters();
            pst = con.prepareStatement(query);
            pst.setInt(1,lastInsertedPropertyID);
            pst.setBoolean(2,hasFreeOnsiteParking.isSelected());
            pst.setBoolean(3,hasOnRoadParking.isSelected());
            pst.setBoolean(4,hasPaidCarPark.isSelected());
            pst.setBoolean(5,hasPatio.isSelected());
            pst.setBoolean(6,hasBarbecue.isSelected());
            pst.executeUpdate();
        }

        insertBedrooms();
        insertBathrooms();
    }

    public void insertBedrooms() throws SQLException {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO Bedrooms VALUES (null, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            for (Bedroom br : bedroomsList) {
                pst.clearParameters();
                pst.setString(1,br.getBed1());
                pst.setString(2,br.getBed2());
                pst.setInt(3,lastInsertedPropertyID);
                pst.executeUpdate();
            }

            bedroomsList = new ArrayList<>();
        }
    }

    public void insertBathrooms() throws SQLException {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO Bathrooms VALUES (null, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            for (Bathroom br : bathroomsList) {
                pst.clearParameters();
                pst.setBoolean(1,br.getHasToilet());
                pst.setBoolean(2,br.getHasBath());
                pst.setBoolean(3,br.getHasShower());
                pst.setBoolean(4,br.getSharedWithHost());
                pst.setInt(5,lastInsertedPropertyID);
                pst.executeUpdate();
            }

            bathroomsList = new ArrayList<>();
        }
    }
}