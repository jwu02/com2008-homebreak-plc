package views;

import models.Property;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static database.OpenConnection.getConnection;

public class SearchPropertyPanel extends JPanel implements ActionListener {
    private MainFrame mainFrame;

    private JTextField requestLocationField = new JTextField(10);
    private JTextField requestStartDateField = new JTextField("yyyy-mm-dd");
    private JTextField requestEndDateField = new JTextField("yyyy-mm-dd");
    private JButton searchPropertyButton = new JButton("Search");
    private JPanel filteredPropertiesPanel = new JPanel();
    private ArrayList<Property> filteredProperties = new ArrayList<>();

    public SearchPropertyPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        add(new JLabel("Search Property"));

        JPanel searchPropertyPanel = new JPanel();
        searchPropertyPanel.add(new JLabel("Location:"));
        searchPropertyPanel.add(requestLocationField);
        searchPropertyPanel.add(new JLabel("Start Date:"));
        searchPropertyPanel.add(requestStartDateField);
        searchPropertyPanel.add(new JLabel("End Date"));
        searchPropertyPanel.add(requestEndDateField);
        searchPropertyButton.addActionListener(this);
        searchPropertyPanel.add(searchPropertyButton);
        add(searchPropertyPanel);

        add(filteredPropertiesPanel);
    }

    public LocalDate getRequestedStartDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(requestStartDateField.getText(), dtf);
    }

    public LocalDate getRequestedEndDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(requestEndDateField.getText(), dtf);
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String dialogMessage;
        if (command.equals("Search")) {
            dialogMessage = validateSearchCriteria(); // returns a string error message
            if (dialogMessage != null) {
                JOptionPane.showMessageDialog(this, dialogMessage, "Search Property",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                // if no error message returned, null, then requested start and end dates are valid
                try (Connection con = getConnection()) {
                    // query return all properties matching the requested location
                    String query = "SELECT * FROM Properties AS p, Users AS u, Addresses AS a " +
                            "WHERE p.UserID = u.UserID " +
                            "AND p.AddressID = a.AddressID " +
                            "AND a.Place LIKE ?";

                    PreparedStatement pst = con.prepareStatement(query);
                    // pattern matching provided location field to any part of a string
                    pst.setString(1, "%" + requestLocationField.getText() + "%");
                    ResultSet rs = pst.executeQuery();

                    filteredProperties = new ArrayList<>();
                    // return Property objects from result set
                    while (rs.next()) {
                        filteredProperties.add(Property.selectProperty(rs));
                    }

                    // so a new set of filtered properties are displayed on the GUI
                    this.remove(filteredPropertiesPanel);
                    filteredPropertiesPanel = new JPanel();
                    filteredPropertiesPanel.setLayout(new BoxLayout(filteredPropertiesPanel, BoxLayout.Y_AXIS));
                    for (Property p : filteredProperties) {
                        // create a bookmark for each filtered property matching the requested location
                        filteredPropertiesPanel.add(new SearchPropertyPanelBookmarkPanel(this, p));
                    }
                    add(filteredPropertiesPanel);

                    revalidate();
                    repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();

                    dialogMessage = "An error occurred searching for properties.";
                    JOptionPane.showMessageDialog(this, dialogMessage,"Search Property",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    public String validateSearchCriteria() {
        if (requestLocationField.getText().equals("") || requestStartDateField.getText().equals("") ||
                requestEndDateField.getText().equals("")) {
            return "Please fill in all search criterions.";
        } else {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate = LocalDate.parse(requestStartDateField.getText(), dtf);
                LocalDate endDate = LocalDate.parse(requestEndDateField.getText(), dtf);

                if (!startDate.isBefore(endDate)) {
                    return "End date cannot be on or before start date.";
                }

                if (!startDate.isAfter(LocalDate.now())) {
                    return "Please search for a date in the future.";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return "Dates cannot be parsed. Please enter date in yyyy-MM-dd format.";
            }
        }

        return null;
    }
}
