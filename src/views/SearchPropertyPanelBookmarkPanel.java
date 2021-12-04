package views;

import models.ChargeBand;
import models.Property;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;

import static database.OpenConnection.getConnection;

public class SearchPropertyPanelBookmarkPanel extends JPanel implements ActionListener {
    private SearchPropertyPanel backReferencePanel;
    private Property property;

    public SearchPropertyPanelBookmarkPanel(SearchPropertyPanel backReferencePanel, Property property) {
        this.backReferencePanel = backReferencePanel;
        this.property = property;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel(property.getName()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel(property.getLocation().getPlace()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        String availabilityLabel = (isAvailableWithinRequestedPeriod() ? "Available" : "Not available") +
                " from " + backReferencePanel.getRequestedStartDate() +
                " to " + backReferencePanel.getRequestedEndDate();
        add(new JLabel(availabilityLabel),gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JButton moreDetailsButton = new JButton("More Details");
        moreDetailsButton.addActionListener(this);
        add(moreDetailsButton,gbc);
        // only if a user is logged in they can book a property or cancel a booking
        if (MainFrame.loggedInUser.getUserID() != 0) {
            gbc.gridx = 1;
            gbc.gridy = 5;
            JButton bookButton = new JButton("Book");
            bookButton.addActionListener(this);
            add(bookButton, gbc);
        }
    }

    public Property getProperty() {
        return property;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("More Details")) {
            // More Details button displays more details for an property, host and facilities on a new frame
            new MorePropertyDetailsFrame(this);
        } else if (command.equals("Book")) {
            // check if user has already booked the property, can't make more than 1 booking on the same property
            try (Connection con = getConnection()) {
                String query = "SELECT * FROM Bookings WHERE UserID = ? AND PropertyID = ? LIMIT 1";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, MainFrame.loggedInUser.getUserID());
                pst.setInt(2, property.getPropertyID());

                ResultSet rs = pst.executeQuery();

                // if user have no bookings on the property
                if (!rs.isBeforeFirst()) {
                    // proceed to check if property is available within requested period
                    if (isAvailableWithinRequestedPeriod()) {
                        LocalDate requestedStartDate = backReferencePanel.getRequestedStartDate();
                        LocalDate requestedEndDate = backReferencePanel.getRequestedEndDate();

                        query = "INSERT INTO Bookings VALUES (?, ?, ?, ?, ?)";
                        pst = con.prepareStatement(query);
                        pst.clearParameters();
                        pst.setInt(1, MainFrame.loggedInUser.getUserID());
                        pst.setInt(2, property.getPropertyID());
                        pst.setDate(3, Date.valueOf(requestedStartDate));
                        pst.setDate(4, Date.valueOf(requestedEndDate));
                        pst.setBoolean(5, false);
                        pst.executeUpdate();

                        JOptionPane.showMessageDialog(this, "Property booking has been received." +
                                "\nNow wait for host to accept booking");
                    } else {
                        String message = "Property not available within requested period.";
                        JOptionPane.showMessageDialog(this,message,"Book Property",JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    String message = "You have already made a booking on this property.";
                    JOptionPane.showMessageDialog(this,message,"Book Property",JOptionPane.WARNING_MESSAGE);
                }

                MainFrame mainFrame = backReferencePanel.getMainFrame();
                mainFrame.getTabbedPane().remove(0);
                mainFrame.getTabbedPane().insertTab("Home",null,new HomePanel(mainFrame),null,0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isAvailableWithinRequestedPeriod() {
        try (Connection con = getConnection()) {
            String query = "SELECT * FROM Bookings WHERE PropertyID = ? AND IsAccepted = 1 LIMIT 1";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, property.getPropertyID());

            ResultSet rs = pst.executeQuery();

            LocalDate requestedStartDate = backReferencePanel.getRequestedStartDate();
            LocalDate requestedEndDate = backReferencePanel.getRequestedEndDate();

            while (rs.next()) {
                LocalDate bookedStartDate = rs.getDate("StartDate").toLocalDate();
                LocalDate bookedEndDate = rs.getDate("EndDate").toLocalDate();

                // check if requested dates overlap with booked dates
                if ((requestedStartDate.isBefore(bookedEndDate) || requestedStartDate.isEqual(bookedEndDate)) &&
                        (requestedEndDate.isAfter(bookedStartDate) || requestedEndDate.isEqual(bookedStartDate))) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public long getNumberOfNights() {
        LocalDate requestedStartDate = backReferencePanel.getRequestedStartDate();
        LocalDate requestedEndDate = backReferencePanel.getRequestedEndDate();

        return Duration.between(requestedStartDate.atStartOfDay(), requestedEndDate.atStartOfDay()).toDays();
    }

    public BigDecimal calculateTotalServiceCharge() {
        BigDecimal totalServiceCharge = new BigDecimal(0);

        LocalDate requestedStartDate = backReferencePanel.getRequestedStartDate();
        LocalDate requestedEndDate = backReferencePanel.getRequestedEndDate();

        for (ChargeBand cb : property.getChargeBands()) {
            if ((requestedStartDate.isBefore(cb.getEndDate()) || requestedStartDate.isEqual(cb.getEndDate())) &&
                    (requestedEndDate.isAfter(cb.getStartDate()) || requestedEndDate.isEqual(cb.getStartDate()))) {
                LocalDate maxStartDate =  requestedStartDate.isAfter(cb.getStartDate()) ? requestedStartDate : cb.getStartDate();
                LocalDate minEndDate = requestedEndDate.isAfter(cb.getEndDate()) ? cb.getEndDate() : requestedEndDate;
                long overlappingDays = Duration.between(maxStartDate.atStartOfDay(), minEndDate.atStartOfDay()).toDays();

                totalServiceCharge = totalServiceCharge.add(cb.getServiceCharge().multiply(BigDecimal.valueOf(Math.abs(overlappingDays))));
            }
        }

        return totalServiceCharge;
    }

    public BigDecimal calculateTotalCleaningCharge() {
        BigDecimal totalCleaningCharge = new BigDecimal(0);

        LocalDate requestedStartDate = backReferencePanel.getRequestedStartDate();
        LocalDate requestedEndDate = backReferencePanel.getRequestedEndDate();

        for (ChargeBand cb : property.getChargeBands()) {
            if ((requestedStartDate.isBefore(cb.getEndDate()) || requestedStartDate.isEqual(cb.getEndDate())) &&
                    (requestedEndDate.isAfter(cb.getStartDate()) || requestedEndDate.isEqual(cb.getStartDate()))) {
                LocalDate maxStartDate =  requestedStartDate.isAfter(cb.getStartDate()) ? requestedStartDate : cb.getStartDate();
                LocalDate minEndDate = requestedEndDate.isAfter(cb.getEndDate()) ? cb.getEndDate() : requestedEndDate;
                long overlappingDays = Duration.between(maxStartDate.atStartOfDay(), minEndDate.atStartOfDay()).toDays();

                totalCleaningCharge = totalCleaningCharge.add(cb.getCleaningCharge().multiply(BigDecimal.valueOf(Math.abs(overlappingDays))));
            }
        }

        return totalCleaningCharge;
    }

    public BigDecimal calculateTotalCost() {
        BigDecimal totalCost = property.getPricePerNight().multiply(BigDecimal.valueOf(getNumberOfNights()));
        totalCost = totalCost.add(calculateTotalServiceCharge());
        totalCost = totalCost.add(calculateTotalCleaningCharge());
        return totalCost;
    }
}
