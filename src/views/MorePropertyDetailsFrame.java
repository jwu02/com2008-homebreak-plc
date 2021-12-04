package views;

import models.Booking;
import models.Property;
import models.facilities.BathingFacility;
import models.facilities.Bathroom;
import models.facilities.Bedroom;
import models.facilities.SleepingFacility;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class MorePropertyDetailsFrame extends JFrame {
    private JPanel backReferencePanel;
    private Property property;
    private Booking booking;

    public MorePropertyDetailsFrame(JPanel backReferencePanel) {
        super("More Details");

        this.backReferencePanel = backReferencePanel;

        long numberOfNights;
        BigDecimal totalServiceCharge;
        BigDecimal totalCleaningCharge;
        BigDecimal totalCost;

        if (backReferencePanel instanceof SearchPropertyPanelBookmarkPanel) {
            property = ((SearchPropertyPanelBookmarkPanel) backReferencePanel).getProperty();

            numberOfNights = ((SearchPropertyPanelBookmarkPanel) backReferencePanel).getNumberOfNights();
            totalServiceCharge = ((SearchPropertyPanelBookmarkPanel) backReferencePanel).calculateTotalServiceCharge();
            totalCleaningCharge = ((SearchPropertyPanelBookmarkPanel) backReferencePanel).calculateTotalCleaningCharge();
            totalCost = ((SearchPropertyPanelBookmarkPanel) backReferencePanel).calculateTotalCost();
        } else {
            booking = ((HomePanelBookmarkPanel) backReferencePanel).getBooking();
            property = booking.getProperty();

            numberOfNights = ((HomePanelBookmarkPanel) backReferencePanel).getNumberOfNights();
            totalServiceCharge = ((HomePanelBookmarkPanel) backReferencePanel).calculateTotalServiceCharge();
            totalCleaningCharge = ((HomePanelBookmarkPanel) backReferencePanel).calculateTotalCleaningCharge();
            totalCost = ((HomePanelBookmarkPanel) backReferencePanel).calculateTotalCost();
        }

        JPanel moreDetailsPanel = new JPanel();
        moreDetailsPanel.setLayout(new BoxLayout(moreDetailsPanel, BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(840, 420);

        JPanel userDetailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        String borderTitle;
        String userForename;
        String userEmail;
        String userMobile;

        if (MainFrame.loggedInUser.getRole().equals("host")) {
            // host should see guest details
            borderTitle = "Guest Details";
            userForename = booking.getGuest().getForename();
            userEmail = booking.getGuest().getEmail();
            userMobile = booking.getGuest().getMobile();
        } else {
            // guest or not logged in users should see host details
            borderTitle = "Host Details";
            userForename = property.getHost().getForename();
            userEmail = property.getHost().getEmail();
            userMobile = property.getHost().getMobile();
        }

        userDetailsPanel.setBorder(BorderFactory.createTitledBorder(borderTitle));
        gbc.gridx = 0;
        gbc.gridy = 0;
        userDetailsPanel.add(new JLabel("Name: "), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        userDetailsPanel.add(new JLabel(userForename), gbc);
        if (this.backReferencePanel instanceof HomePanelBookmarkPanel && booking.isAccepted()) {
            // only display confidential information if a booking is accepted and displayed on home panel
            gbc.gridx = 0;
            gbc.gridy = 1;
            userDetailsPanel.add(new JLabel("Email: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            userDetailsPanel.add(new JLabel(userEmail), gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            userDetailsPanel.add(new JLabel("Mobile: "),gbc);
            gbc.gridx = 1;
            gbc.gridy = 2;
            userDetailsPanel.add(new JLabel(userMobile),gbc);
        }

        if (!MainFrame.loggedInUser.getRole().equals("host")) {
            gbc.gridx = 0;
            gbc.gridy = 3;
            userDetailsPanel.add(new JLabel("Is super host: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 3;
            userDetailsPanel.add(new JLabel(property.getHost().getIsSuperHost() ? "Yes" : "No"), gbc);
        }

        moreDetailsPanel.add(userDetailsPanel);

        JPanel propertyDetailsPanel = new JPanel(new GridBagLayout());
        propertyDetailsPanel.setBorder(BorderFactory.createTitledBorder("Property Details"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        propertyDetailsPanel.add(new JLabel(property.getName()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        propertyDetailsPanel.add(new JLabel("Offers breakfast: " + (property.getOfferBreakfast() ? "Yes" : "No")),gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        propertyDetailsPanel.add(new JLabel("<html>"+property.getDescription()+"</html>"),gbc);
        moreDetailsPanel.add(propertyDetailsPanel);

        // display address if logged in user is host (viewing their own requested bookings from other users)
        // or if the booking is accepted (user viewing it as a guest)
        if (backReferencePanel instanceof HomePanelBookmarkPanel &&
                (MainFrame.loggedInUser.getRole().equals("host") || booking.isAccepted())) {
            JPanel addressDetailsPanel = new JPanel(new GridBagLayout());
            addressDetailsPanel.setBorder(BorderFactory.createTitledBorder("Address Details"));
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            addressDetailsPanel.add(new JLabel("House: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            addressDetailsPanel.add(new JLabel(property.getLocation().getHouse()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            addressDetailsPanel.add(new JLabel("Street: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            addressDetailsPanel.add(new JLabel(property.getLocation().getStreet()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            addressDetailsPanel.add(new JLabel("Place: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 2;
            addressDetailsPanel.add(new JLabel(property.getLocation().getPlace()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            addressDetailsPanel.add(new JLabel("Postcode: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 3;
            addressDetailsPanel.add(new JLabel(property.getLocation().getPostcode()), gbc);

            add(addressDetailsPanel);
        }

        JPanel costDetailsPanel = new JPanel(new GridBagLayout());
        costDetailsPanel.setBorder(BorderFactory.createTitledBorder("Cost Details"));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        costDetailsPanel.add(new JLabel("Number of nights: "),gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        costDetailsPanel.add(new JLabel(String.valueOf(numberOfNights)),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        costDetailsPanel.add(new JLabel("Price per night: "),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        costDetailsPanel.add(new JLabel("£"+property.getPricePerNight()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        costDetailsPanel.add(new JLabel("Total service charge: "),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        costDetailsPanel.add(new JLabel("£"+totalServiceCharge),gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        costDetailsPanel.add(new JLabel("Total cleaning charge: "),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        costDetailsPanel.add(new JLabel("£"+totalCleaningCharge),gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        costDetailsPanel.add(new JLabel("Total charge: "),gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        costDetailsPanel.add(new JLabel("£"+totalCost),gbc);
        moreDetailsPanel.add(costDetailsPanel);

        JPanel bedroomDetailsPanel = new JPanel();
        bedroomDetailsPanel.setLayout(new BoxLayout(bedroomDetailsPanel, BoxLayout.Y_AXIS));
        bedroomDetailsPanel.setBorder(BorderFactory.createTitledBorder("Bedroom Details"));
        for (Bedroom b : ((SleepingFacility) property.getFacilitiesMap().get("Sleeping Facility")).getBedrooms()) {
            JPanel bedroomPanel = new JPanel(new GridBagLayout());
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            bedroomPanel.add(new JLabel("Bed 1: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            bedroomPanel.add(new JLabel(b.getBed1()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            bedroomPanel.add(new JLabel("Bed 2: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            bedroomPanel.add(new JLabel(b.getBed2()), gbc);
            bedroomDetailsPanel.add(bedroomPanel);
        }
        moreDetailsPanel.add(bedroomDetailsPanel);

        JPanel bathroomDetailsPanel = new JPanel();
        bathroomDetailsPanel.setLayout(new BoxLayout(bathroomDetailsPanel, BoxLayout.Y_AXIS));
        bathroomDetailsPanel.setBorder(BorderFactory.createTitledBorder("Bathrooms Details"));
        for (Bathroom b : ((BathingFacility) property.getFacilitiesMap().get("Bathing Facility")).getBathrooms()) {
            JPanel bathroomPanel = new JPanel(new GridBagLayout());
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            bathroomPanel.add(new JLabel("Has Toilet: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            bathroomPanel.add(new JLabel(b.getHasToilet() ? "Yes" : "No"), gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            bathroomPanel.add(new JLabel("Has Bath: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            bathroomPanel.add(new JLabel(b.getHasBath() ? "Yes" : "No"), gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            bathroomPanel.add(new JLabel("Has Shower: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 2;
            bathroomPanel.add(new JLabel(b.getHasShower() ? "Yes" : "No"), gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            bathroomPanel.add(new JLabel("Shared with Host: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 3;
            bathroomPanel.add(new JLabel(b.getSharedWithHost() ? "Yes" : "No"), gbc);
            bathroomDetailsPanel.add(bathroomPanel);
        }
        moreDetailsPanel.add(bathroomDetailsPanel);

        JPanel facilityDetailsPanel = new JPanel();
        facilityDetailsPanel.setLayout(new BoxLayout(facilityDetailsPanel, BoxLayout.Y_AXIS));
        facilityDetailsPanel.setBorder(BorderFactory.createTitledBorder("Facilities Details"));

        // for each facility
        for (String facilityName : property.getFacilitiesMap().keySet()) {
            // get corresponding facility map
            // facilities map, maps each facility name to the corresponding facility object
            LinkedHashMap<String, Boolean> facilityMap = property.getFacilitiesMap().get(facilityName).getFacilityMap();
            // from which we can obtain another map for all the column labels of a facility table
            String[] columnLabels = facilityMap.keySet().toArray(new String[facilityMap.size()]);

            // store facility details in an array to create a table presenting the results
            String[] rowData = new String[columnLabels.length];
            for (int j=0; j<columnLabels.length; j++) {
                // return yes or no depending on whether a facility has something
                rowData[j] = facilityMap.get(columnLabels[j]) ? "yes" : "no";
            }

            String[][] tableData = {rowData};
            JTable facilityTable = new JTable(tableData, columnLabels);
            facilityDetailsPanel.add(new JLabel(facilityName));
            facilityDetailsPanel.add(facilityTable);
        }

        moreDetailsPanel.add(facilityDetailsPanel);

        JScrollPane scrollPane = new JScrollPane(moreDetailsPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        add(scrollPane);

        setVisible(true);
    }
}
