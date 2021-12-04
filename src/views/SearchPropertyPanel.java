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
        if (command.equals("Search")) {
            if (requestLocationField.getText().equals("") || requestStartDateField.getText().equals("") ||
                    requestEndDateField.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill in all search criterions.");
            } else if (getRequestedStartDate().isAfter(getRequestedEndDate())) {
                JOptionPane.showMessageDialog(this, "Please enter a start date before the end date or vice versa.",
                        "Search property", JOptionPane.WARNING_MESSAGE);
            } else {
                try (Connection con = getConnection()) {
                    String query = "SELECT * FROM Properties AS p, Users AS u, Addresses AS a " +
                            "WHERE p.UserID = u.UserID " +
                            "AND p.AddressID = a.AddressID " +
                            "AND a.Place LIKE ?";

                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, "%" + requestLocationField.getText() + "%");
                    ResultSet rs = pst.executeQuery();

                    filteredProperties = new ArrayList<>();
                    while (rs.next()) {
                        filteredProperties.add(Property.selectProperty(rs));
                    }

                    this.remove(filteredPropertiesPanel);
                    filteredPropertiesPanel = new JPanel();
                    filteredPropertiesPanel.setLayout(new BoxLayout(filteredPropertiesPanel, BoxLayout.Y_AXIS));
                    for (Property p : filteredProperties) {
                        filteredPropertiesPanel.add(new SearchPropertyPanelBookmarkPanel(this, p));
                    }
                    add(filteredPropertiesPanel);

                    revalidate();
                    repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    String message = "Error searching for property. Please check input criterions.";
                    JOptionPane.showMessageDialog(this, message,"Search Property", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
