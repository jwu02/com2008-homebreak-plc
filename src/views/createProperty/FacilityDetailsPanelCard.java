package views.createProperty;

import models.facilities.Bathroom;
import models.facilities.Bedroom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static database.OpenConnection.getConnection;

public class FacilityDetailsPanelCard extends JPanel implements ActionListener {
    private CreatePropertyPanel backReferencePanel;

    // sleeping facility
    private JPanel sleepingFacilityPanel = new JPanel();
    private JCheckBox hasBedLinen = new JCheckBox("Bed Linen");
    private JCheckBox hasTowels = new JCheckBox("Towels");
    // bedroom
    private ArrayList<Bedroom> bedroomsList = new ArrayList<>();
    private JPanel addBedroomsPanel = new JPanel(new GridBagLayout());
    ButtonGroup bed1ButtonGroup = new ButtonGroup();
    private JRadioButton singleBed1RadioButton = new JRadioButton("single");
    private JRadioButton doubleBed1RadioButton = new JRadioButton("double");
    private JRadioButton kingsizeBed1RadioButton = new JRadioButton("kingsize");
    private JRadioButton bunkBed1RadioButton = new JRadioButton("bunk");

    ButtonGroup bed2ButtonGroup = new ButtonGroup();
    private JRadioButton singleBed2RadioButton = new JRadioButton("single");
    private JRadioButton doubleBed2RadioButton = new JRadioButton("double");
    private JRadioButton kingsizeBed2RadioButton = new JRadioButton("kingsize");
    private JRadioButton bunkBed2RadioButton = new JRadioButton("bunk");
    private JRadioButton noBed2RadioButton = new JRadioButton("no bed 2");

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

    public FacilityDetailsPanelCard(CreatePropertyPanel backReferencePanel) {
        this.backReferencePanel = backReferencePanel;

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(new JLabel("Facilities Details"));

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

        JButton addBedroomButton = new JButton("Add Bedroom");
        addBedroomButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        addBedroomsPanel.add(addBedroomButton,gbc);
        sleepingFacilityPanel.add(addBedroomsPanel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(sleepingFacilityPanel,gbc);

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

        JButton addBathroomButton = new JButton("Add Bathroom");
        addBathroomButton.addActionListener(this);
        addBathroomsPanel.add(addBathroomButton);
        bathingFacilityPanel.add(addBathroomsPanel);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(bathingFacilityPanel,gbc);

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
        add(kitchenFacilityPanel,gbc);

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
        add(livingFacilityPanel,gbc);

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
        add(utilityFacilityPanel,gbc);

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
        add(outdoorFacilityPanel,gbc);

        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(previousButton);

        JButton createPropertyButton = new JButton("Create Property");
        createPropertyButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(createPropertyButton,gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Add Bedroom")) {
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
        } else if (command.equals("Add Bathroom")) {
            bathroomsList.add(new Bathroom(hasToilet.isSelected(),hasBath.isSelected(),hasShower.isSelected(),
                    isSharedWithHost.isSelected()));

            JOptionPane.showMessageDialog(this,"Added bathroom.");

            hasToilet.setSelected(false);
            hasBath.setSelected(false);
            hasShower.setSelected(false);
            isSharedWithHost.setSelected(false);
        } else if (command.equals("Previous")) {
            backReferencePanel.getCardLayout().previous(backReferencePanel);
        } else if (command.equals("Create Property")) {
            if (bedroomsList.size() == 0) {
                JOptionPane.showMessageDialog(this, "Please add at least one bedroom.",
                        "Facilities Details", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    backReferencePanel.getConfidentialDetailsPanelCard().insertProperty();
                    backReferencePanel.getChargeBandDetailsPanelCard().insertChargeBands();
                    insertFacilities();
                    backReferencePanel.clearAllFields();
                    JOptionPane.showMessageDialog(this,"Property created.");

                    backReferencePanel.getCardLayout().show(backReferencePanel, "confidentialDetails");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error adding property",
                            "Add Property", JOptionPane.WARNING_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }

    public void insertFacilities() throws SQLException {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO SleepingFacilities VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,CreatePropertyPanel.lastInsertedPropertyID);
            pst.setBoolean(2,hasBedLinen.isSelected());
            pst.setBoolean(3,hasTowels.isSelected());
            pst.executeUpdate();

            query = "INSERT INTO BathingFacilities VALUES (?, ?, ?, ?)";
            pst.clearParameters();
            pst = con.prepareStatement(query);
            pst.setInt(1,CreatePropertyPanel.lastInsertedPropertyID);
            pst.setBoolean(2,hasHairDryer.isSelected());
            pst.setBoolean(3,hasShampoo.isSelected());
            pst.setBoolean(4,hasToiletPaper.isSelected());
            pst.executeUpdate();

            query = "INSERT INTO KitchenFacilities VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst.clearParameters();
            pst = con.prepareStatement(query);
            pst.setInt(1,CreatePropertyPanel.lastInsertedPropertyID);
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
            pst.setInt(1,CreatePropertyPanel.lastInsertedPropertyID);
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
            pst.setInt(1,CreatePropertyPanel.lastInsertedPropertyID);
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
            pst.setInt(1,CreatePropertyPanel.lastInsertedPropertyID);
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
                pst.setString(1, br.getBed1());
                pst.setString(2, br.getBed2());
                pst.setInt(3, CreatePropertyPanel.lastInsertedPropertyID);
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
                pst.setBoolean(1, br.getHasToilet());
                pst.setBoolean(2, br.getHasBath());
                pst.setBoolean(3, br.getHasShower());
                pst.setBoolean(4, br.getSharedWithHost());
                pst.setInt(5, CreatePropertyPanel.lastInsertedPropertyID);
                pst.executeUpdate();
            }

            bathroomsList = new ArrayList<>();
        }
    }

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
}
