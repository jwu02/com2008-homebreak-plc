package views;

import models.Property;

import javax.swing.*;
import java.util.LinkedHashMap;

public class MorePropertyDetailsFrame extends JFrame {
    private Property property;

    public MorePropertyDetailsFrame(Property property) {
        super("More Details");

        this.property = property;

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(840, 420);

        // TODO display public host information in more details frame
        JPanel hostDetailsPanel = new JPanel();
        add(hostDetailsPanel);

        JPanel facilityDetailsPanel = new JPanel();
        facilityDetailsPanel.setLayout(new BoxLayout(facilityDetailsPanel, BoxLayout.Y_AXIS));

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
