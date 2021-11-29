package views;

import models.Address;
import models.ChargeBand;
import models.Property;
import models.User;
import models.facilities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import static database.OpenConnection.getConnection;

public class SearchPropertyPanel extends JPanel implements ActionListener {

    private JTextField requestLocationField = new JTextField(10);
    private JTextField requestStartDateField = new JTextField("yyyy-mm-dd");
    private JTextField requestEndDateField = new JTextField("yyyy-mm-dd");
    private JButton searchPropertyButton = new JButton("Search");
    private JPanel filteredPropertiesPanel = new JPanel();
    private ArrayList<Property> filteredProperties = new ArrayList<>();

    public SearchPropertyPanel() {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        add(new JLabel("Search Property"));

        JPanel searchPropertyPanel = new JPanel();
        searchPropertyPanel.add(new JLabel("Location:"));
        searchPropertyPanel.add(requestLocationField);
        searchPropertyButton.addActionListener(this);
        searchPropertyPanel.add(searchPropertyButton);
        add(searchPropertyPanel);

        add(filteredPropertiesPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Search")) {
            try (Connection con = getConnection()) {
                String query = "SELECT * FROM Properties AS p, Users AS u, Addresses AS a " +
                        "WHERE p.UserID = u.UserID " +
                        "AND p.AddressID = a.AddressID " +
                        "AND a.Place LIKE ?";

                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, "%"+requestLocationField.getText()+"%");

                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    int propertyID = rs.getInt("PropertyID");
                    String propertyName = rs.getString("Name");
                    String propertyDescription = rs.getString("Description");
                    Boolean offerBreakfast = rs.getBoolean("OfferBreakfast");

                    System.out.println(propertyName);
                    System.out.println(propertyDescription);

                    int userID = rs.getInt("p.UserID");
                    String forename = rs.getString("Forename");
                    String surname = rs.getString("Surname");
                    String email = rs.getString("Email");
                    String mobile = rs.getString("Mobile");
                    String role = rs.getString("Role");
                    User host = new User(userID, forename, surname, email, mobile, role);

                    int addressID = rs.getInt("p.AddressID");
                    String house = rs.getString("House");
                    String street = rs.getString("Street");
                    String place = rs.getString("Place");
                    String postcode = rs.getString("Postcode");
                    Address address = new Address(addressID, house, street, place, postcode);

                    // convert charge band information to model
                    ArrayList<ChargeBand> chargeBandsList = new ArrayList<>();
                    query = "SELECT * FROM ChargeBands WHERE PropertyID = ?";
                    PreparedStatement pstChargeBands = con.prepareStatement(query);
                    pstChargeBands.setInt(1, propertyID);
                    ResultSet rsChargeBands = pstChargeBands.executeQuery();
                    while (rsChargeBands.next()) {
                        LocalDate startDate = rsChargeBands.getDate("StartDate").toLocalDate();
                        LocalDate endDate = rsChargeBands.getDate("EndDate").toLocalDate();
                        BigDecimal pricePerNight = rsChargeBands.getBigDecimal("PricePerNight");
                        BigDecimal serviceCharge = rsChargeBands.getBigDecimal("ServiceCharge");
                        BigDecimal cleaningCharge = rsChargeBands.getBigDecimal("CleaningCharge");
                        chargeBandsList.add(new ChargeBand(startDate, endDate, pricePerNight, serviceCharge, cleaningCharge));
                    }

                    // convert facility information to models
                    ArrayList<Facility> facilitiesList = new ArrayList<>();

                    ArrayList<Bedroom> bedroomsList = new ArrayList<>();
                    query = "SELECT * FROM Bedrooms WHERE PropertyID = ?";
                    PreparedStatement pstFacilities = con.prepareStatement(query);
                    pstFacilities.setInt(1, propertyID);
                    ResultSet rsFacilities = pstFacilities.executeQuery();
                    while (rsFacilities.next()) {
                        String bed1 = rsFacilities.getString("Bed1");
                        String bed2 = rsFacilities.getString("Bed2");
                        bedroomsList.add(new Bedroom(bed1, bed2));
                    }

                    query = "SELECT * FROM SleepingFacilities WHERE PropertyID = ? LIMIT 1";
                    pstFacilities = con.prepareStatement(query);
                    pstFacilities.clearParameters();
                    pstFacilities.setInt(1, propertyID);
                    rsFacilities = pstFacilities.executeQuery();
                    rsFacilities.next();
                    boolean hasBedLinen = rsFacilities.getBoolean("HasBedLinen");
                    boolean hasTowels = rsFacilities.getBoolean("HasBedLinen");
                    facilitiesList.add(new SleepingFacility(hasBedLinen, hasTowels, bedroomsList));

                    ArrayList<Bathroom> bathroomsList = new ArrayList<>();
                    query = "SELECT * FROM Bathrooms WHERE PropertyID = ?";
                    pstFacilities = con.prepareStatement(query);
                    pstFacilities.clearParameters();
                    pstFacilities.setInt(1, propertyID);
                    rsFacilities = pstFacilities.executeQuery();
                    while (rsFacilities.next()) {
                        boolean hasToilet = rsFacilities.getBoolean("HasToilet");
                        boolean hasBath = rsFacilities.getBoolean("HasBath");
                        boolean hasShower = rsFacilities.getBoolean("HasShower");
                        boolean sharedWithHost = rsFacilities.getBoolean("SharedWithHost");
                        bathroomsList.add(new Bathroom(hasToilet, hasBath, hasShower, sharedWithHost));
                    }

                    query = "SELECT * FROM BathingFacilities WHERE PropertyID = ? LIMIT 1";
                    pstFacilities = con.prepareStatement(query);
                    pstFacilities.clearParameters();
                    pstFacilities.setInt(1, propertyID);
                    rsFacilities = pstFacilities.executeQuery();
                    rsFacilities.next();
                    boolean hasHairDryer = rsFacilities.getBoolean("HasHairDryer");
                    boolean hasShampoo = rsFacilities.getBoolean("HasShampoo");
                    boolean hasToiletPaper = rsFacilities.getBoolean("HasToiletPaper");
                    facilitiesList.add(new BathingFacility(hasHairDryer, hasShampoo, hasToiletPaper, bathroomsList));

                    query = "SELECT * FROM KitchenFacilities WHERE PropertyID = ? LIMIT 1";
                    pstFacilities = con.prepareStatement(query);
                    pstFacilities.clearParameters();
                    pstFacilities.setInt(1, propertyID);
                    rsFacilities = pstFacilities.executeQuery();
                    rsFacilities.next();
                    boolean hasRefrigerator = rsFacilities.getBoolean("HasRefrigerator");
                    boolean hasMicrowave = rsFacilities.getBoolean("HasMicrowave");
                    boolean hasOven = rsFacilities.getBoolean("HasOven");
                    boolean hasStove = rsFacilities.getBoolean("HasStove");
                    boolean hasDishwasher = rsFacilities.getBoolean("HasDishwasher");
                    boolean hasTableware = rsFacilities.getBoolean("HasTableware");
                    boolean hasCookware = rsFacilities.getBoolean("HasCookware");
                    boolean hasBasicProvisions = rsFacilities.getBoolean("HasBasicProvisions");
                    facilitiesList.add(new KitchenFacility(hasRefrigerator, hasMicrowave, hasOven, hasStove,
                            hasDishwasher, hasTableware, hasCookware, hasBasicProvisions));

                    query = "SELECT * FROM LivingFacilities WHERE PropertyID = ? LIMIT 1";
                    pstFacilities = con.prepareStatement(query);
                    pstFacilities.clearParameters();
                    pstFacilities.setInt(1, propertyID);
                    rsFacilities = pstFacilities.executeQuery();
                    rsFacilities.next();
                    boolean hasWifi = rsFacilities.getBoolean("HasWifi");
                    boolean hasTelevision = rsFacilities.getBoolean("HasTelevision");
                    boolean hasSatellite = rsFacilities.getBoolean("HasSatellite");
                    boolean hasStreaming = rsFacilities.getBoolean("HasStreaming");
                    boolean hasDvdPlayer = rsFacilities.getBoolean("HasDvdPlayer");
                    boolean hasBoardGames = rsFacilities.getBoolean("HasBoardGames");
                    facilitiesList.add(new LivingFacility(hasWifi, hasTelevision, hasSatellite, hasStreaming,
                            hasDvdPlayer, hasBoardGames));

                    query = "SELECT * FROM UtilityFacilities WHERE PropertyID = ? LIMIT 1";
                    pstFacilities = con.prepareStatement(query);
                    pstFacilities.clearParameters();
                    pstFacilities.setInt(1, propertyID);
                    rsFacilities = pstFacilities.executeQuery();
                    rsFacilities.next();
                    boolean hasHeating = rsFacilities.getBoolean("HasHeating");
                    boolean hasWashingMachine = rsFacilities.getBoolean("HasWashingMachine");
                    boolean hasDryingMachine = rsFacilities.getBoolean("HasDryingMachine");
                    boolean hasFireExtinguisher = rsFacilities.getBoolean("HasFireExtinguisher");
                    boolean hasSmokeAlarm = rsFacilities.getBoolean("HasSmokeAlarm");
                    boolean hasFirstAidKit = rsFacilities.getBoolean("HasFirstAidKit");
                    facilitiesList.add(new UtilityFacility(hasHeating, hasWashingMachine, hasDryingMachine,
                            hasFireExtinguisher, hasSmokeAlarm, hasFirstAidKit));

                    query = "SELECT * FROM OutdoorFacilities WHERE PropertyID = ? LIMIT 1";
                    pstFacilities = con.prepareStatement(query);
                    pstFacilities.clearParameters();
                    pstFacilities.setInt(1, propertyID);
                    rsFacilities = pstFacilities.executeQuery();
                    rsFacilities.next();
                    boolean hasFreeOnsiteParking = rsFacilities.getBoolean("HasFreeOnsiteParking");
                    boolean hasOnRoadParking = rsFacilities.getBoolean("HasOnRoadParking");
                    boolean hasPaidCarPark = rsFacilities.getBoolean("HasPaidCarPark");
                    boolean hasPatio = rsFacilities.getBoolean("HasPatio");
                    boolean hasBarbecue = rsFacilities.getBoolean("HasBarbecue");
                    facilitiesList.add(new OutdoorFacility(hasFreeOnsiteParking, hasOnRoadParking, hasPaidCarPark,
                            hasPatio, hasBarbecue));

                    Property property = new Property(propertyID, propertyName, propertyDescription, offerBreakfast,
                            host, address, chargeBandsList, facilitiesList);
                    filteredProperties.add(property);
                }

                filteredPropertiesPanel = new JPanel();
                add(filteredPropertiesPanel);
                filteredPropertiesPanel.setLayout(new BoxLayout(filteredPropertiesPanel, BoxLayout.Y_AXIS));

                for (Property p : filteredProperties) {
                    filteredPropertiesPanel.add(newPropertyBookmark(p));
                }
                revalidate();
                repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public JPanel newPropertyBookmark(Property property) {
        JPanel propertyBookmarkPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        propertyBookmarkPanel.add(new JLabel(property.getName()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        propertyBookmarkPanel.add(new JLabel(property.getLocation().getPlace()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        propertyBookmarkPanel.add(new JTextArea(property.getDescription()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        if (isAvailableWithinRequestedPeriod(property)) {
            propertyBookmarkPanel.add(new JLabel("Available within requested period."),gbc);
        } else {
            propertyBookmarkPanel.add(new JLabel("Not available within requested period."),gbc);
        }
        gbc.gridx = 0;
        gbc.gridy = 4;
        JButton moreDetailsButton = new JButton("More Details");
        moreDetailsButton.addActionListener(e -> {
            // TODO display further details of host (public info) and facilities
            JFrame moreDetailsFrame = new JFrame("More Details");
            moreDetailsFrame.setLayout(new BoxLayout(moreDetailsFrame, BoxLayout.Y_AXIS));
            moreDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel hostDetailsPanel = new JPanel();


            JLayeredPane mdFrameLayeredPane = moreDetailsFrame.getLayeredPane();
            JButton previousFacility = new JButton("<");
            JButton nextFacility = new JButton(">");

            CardLayout cardLayout = new CardLayout();
            JPanel facilitiesCardPanel = new JPanel(cardLayout);
            mdFrameLayeredPane.add(facilitiesCardPanel, new Integer(2));
            mdFrameLayeredPane.add(previousFacility, new Integer(1));
            mdFrameLayeredPane.add(nextFacility, new Integer(1));




            moreDetailsFrame.add(moreDetailsPanel);
            moreDetailsFrame.setVisible(true);
        });
        propertyBookmarkPanel.add(moreDetailsButton,gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        JButton bookButton = new JButton("Book");
        bookButton.addActionListener(e -> {
            // TODO implement booking function
        });
        propertyBookmarkPanel.add(bookButton,gbc);

        return propertyBookmarkPanel;
    }

    public boolean isAvailableWithinRequestedPeriod(Property property) {
        return false;
    }
}
