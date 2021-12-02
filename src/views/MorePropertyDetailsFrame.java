package views;

import models.Property;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.LinkedHashMap;

public class MorePropertyDetailsFrame extends JFrame {
    private PropertyBookmarkPanel backReferencePanel;

    public MorePropertyDetailsFrame(JPanel backReferencePanel, Property property) {
        super("More Details");

        this.backReferencePanel = (PropertyBookmarkPanel) backReferencePanel;

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(840, 420);

        JPanel hostDetailsPanel = new JPanel(new GridBagLayout());
        Border hostDetailsPanelBorder = BorderFactory.createTitledBorder("Host Details");
        hostDetailsPanel.setBorder(hostDetailsPanelBorder);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        hostDetailsPanel.add(new JLabel("Host name: "), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        hostDetailsPanel.add(new JLabel(property.getHost().getForename()), gbc);
        if (this.backReferencePanel.getBackReferencePanel() instanceof HomePanel && this.backReferencePanel.isBookingAccepted()) {
            gbc.gridx = 0;
            gbc.gridy = 1;
            hostDetailsPanel.add(new JLabel("Email: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            hostDetailsPanel.add(new JLabel(property.getHost().getEmail()), gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            hostDetailsPanel.add(new JLabel("Mobile: "),gbc);
            gbc.gridx = 1;
            gbc.gridy = 2;
            hostDetailsPanel.add(new JLabel(property.getHost().getMobile()),gbc);
        }
        gbc.gridx = 0;
        gbc.gridy = 3;
        hostDetailsPanel.add(new JLabel("Is super host: "), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        hostDetailsPanel.add(new JLabel(property.getHost().isSuperHost() ? "Yes" : "No"), gbc);
        add(hostDetailsPanel);

        JPanel propertyDetailsPanel = new JPanel(new GridBagLayout());
        Border propertyDetailsPanelBorder = BorderFactory.createTitledBorder("Property Details");
        propertyDetailsPanel.setBorder(propertyDetailsPanelBorder);
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
        add(propertyDetailsPanel);

        JPanel costDetailsPanel = new JPanel(new GridBagLayout());
        Border costDetailsPanelBorder = BorderFactory.createTitledBorder("Cost Details");
        costDetailsPanel.setBorder(costDetailsPanelBorder);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        costDetailsPanel.add(new JLabel("Number of nights: "),gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        costDetailsPanel.add(new JLabel(String.valueOf(((PropertyBookmarkPanel) backReferencePanel).getNumberOfNights())),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        costDetailsPanel.add(new JLabel("Price per night: "),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        costDetailsPanel.add(new JLabel(String.valueOf(property.getPricePerNight())),gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        costDetailsPanel.add(new JLabel("Total service charge: "),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        costDetailsPanel.add(new JLabel(),gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        costDetailsPanel.add(new JLabel("Total cleaning charge: "),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        costDetailsPanel.add(new JLabel(),gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        costDetailsPanel.add(new JLabel("Total charge: "),gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        costDetailsPanel.add(new JLabel(),gbc);
        add(costDetailsPanel);

        if (this.backReferencePanel.getBackReferencePanel() instanceof HomePanel && this.backReferencePanel.isBookingAccepted()) {
            JPanel addressDetailsPanel = new JPanel(new GridBagLayout());
            Border addressDetailsPanelBorder = BorderFactory.createTitledBorder("Address Details");
            addressDetailsPanel.setBorder(addressDetailsPanelBorder);
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

        JPanel facilityDetailsPanel = new JPanel();
        facilityDetailsPanel.setLayout(new BoxLayout(facilityDetailsPanel, BoxLayout.Y_AXIS));
        Border facilityDetailsPanelBorder = BorderFactory.createTitledBorder("Facility Details");
        facilityDetailsPanel.setBorder(facilityDetailsPanelBorder);

        String[] facilityNames = property.getFacilitiesMap().keySet().toArray(new String[property.getFacilitiesMap().size()]);
        for (int i=0; i<facilityNames.length; i++) {
            LinkedHashMap<String, Boolean> facilityMap = property.getFacilitiesMap().get(facilityNames[i]).getFacilityMap();
            String[] columnLabels = facilityMap.keySet().toArray(new String[facilityMap.size()]);

            String[] rowData = new String[columnLabels.length];
            for (int j=0; j<columnLabels.length; j++) {
                if (facilityMap.get(columnLabels[j])) {
                    rowData[j] = "yes";
                } else {
                    rowData[j] = "no";
                }
            }

            String[][] tableData = {rowData};
            JTable facilityTable = new JTable(tableData, columnLabels);
            facilityDetailsPanel.add(new JLabel(facilityNames[i]));
            facilityDetailsPanel.add(new JScrollPane(facilityTable));
        }

        add(facilityDetailsPanel);

        setVisible(true);
    }
}
