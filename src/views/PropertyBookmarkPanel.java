package views;

import models.Booking;
import models.Property;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static database.OpenConnection.getConnection;

public class PropertyBookmarkPanel extends JPanel implements ActionListener {
    private JPanel backReferencePanel;
    private Property property;
    private HashMap<Integer, Booking> bookingsMap = new HashMap<>();

    public PropertyBookmarkPanel(JPanel backReferencePanel, Property property) {
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
        if (backReferencePanel instanceof SearchPropertyPanel) {
            String availabilityLabel = (isAvailableWithinRequestedPeriod() ? "Available" : "Not available") +
                    " from " + ((SearchPropertyPanel) backReferencePanel).getRequestedStartDate() +
                    " to " + ((SearchPropertyPanel) backReferencePanel).getRequestedEndDate();
            add(new JLabel(availabilityLabel),gbc);
        } else if (backReferencePanel instanceof HomePanel) {
            bookingsMap = ((HomePanel) backReferencePanel).getBookingsMap();
            add(new JLabel(isBookingAccepted() ? "Booking accepted!" : "Booking awaiting acceptance from host."),gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            add(new JLabel("Start date"),gbc);
            gbc.gridx = 1;
            gbc.gridy = 3;
            add(new JLabel(String.valueOf(bookingsMap.get(property.getPropertyID()).getStartDate())),gbc);
            gbc.gridx = 0;
            gbc.gridy = 4;
            add(new JLabel("End date"),gbc);
            gbc.gridx = 1;
            gbc.gridy = 4;
            add(new JLabel(String.valueOf(bookingsMap.get(property.getPropertyID()).getEndDate())),gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 5;
        JButton moreDetailsButton = new JButton("More Details");
        moreDetailsButton.addActionListener(this);
        add(moreDetailsButton,gbc);
        // only if a user is logged in they can book a property or cancel a booking
        if (MainFrame.loggedInUser.getUserID() != 0) {
            gbc.gridx = 1;
            gbc.gridy = 5;
            if (backReferencePanel instanceof SearchPropertyPanel) {
                JButton bookButton = new JButton("Book");
                bookButton.addActionListener(this);
                add(bookButton, gbc);
            } else if (backReferencePanel instanceof HomePanel) {
                if (MainFrame.loggedInUser.getRole().equals("host") && !isBookingAcceptedHostView()) {
                    JButton acceptBookingButton = new JButton("Accept");
                    acceptBookingButton.addActionListener(this);
                    add(acceptBookingButton, gbc);
                }
                gbc.gridx = 2;
                gbc.gridy = 5;
                JButton cancelBookingButton = new JButton("Cancel");
                cancelBookingButton.addActionListener(this);
                add(cancelBookingButton, gbc);
            }
        }
    }

    public boolean isBookingAccepted() {
        return bookingsMap.get(property.getPropertyID()).isAccepted();
    }

    public boolean isBookingAcceptedHostView() {
        for (Booking b : ((HomePanel) backReferencePanel).getBookingsList()) {
            if (b.equals(bookingsMap.get(property.getPropertyID()))) {
                return bookingsMap.get(property.getPropertyID()).isAccepted();
            }
        }
        return false;
    }

    public long getNumberOfNights() {
        if (backReferencePanel instanceof SearchPropertyPanel) {
            LocalDate requestedStartDate = ((SearchPropertyPanel) backReferencePanel).getRequestedStartDate();
            LocalDate requestedEndDate = ((SearchPropertyPanel) backReferencePanel).getRequestedEndDate();
            return Duration.between(requestedStartDate.atStartOfDay(), requestedEndDate.atStartOfDay()).toDays();
        } else if (backReferencePanel instanceof HomePanel) {
            LocalDate requestedStartDate = bookingsMap.get(property.getPropertyID()).getStartDate();
            LocalDate requestedEndDate = bookingsMap.get(property.getPropertyID()).getEndDate();
            return Duration.between(requestedStartDate.atStartOfDay(), requestedEndDate.atStartOfDay()).toDays();
        } else {
            return 0;
        }
    }

    public BigDecimal calculateTotalServiceCharge() {

        return new BigDecimal(0);
    }

    public BigDecimal calculateTotalCleaningCharge() {

        return new BigDecimal(0);
    }

    public JPanel getBackReferencePanel() {
        return backReferencePanel;
    }

    public boolean isAvailableWithinRequestedPeriod() {
        try (Connection con = getConnection()) {
            String query = "SELECT * FROM Bookings WHERE PropertyID = ? AND IsAccepted = 1 LIMIT 1";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, property.getPropertyID());

            ResultSet rs = pst.executeQuery();

            LocalDate requestedStartDate = ((SearchPropertyPanel) backReferencePanel).getRequestedStartDate();
            LocalDate requestedEndDate = ((SearchPropertyPanel) backReferencePanel).getRequestedEndDate();

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

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("More Details")) {
            // More Details button displays more details for an property, host and facilities on a new frame
            new MorePropertyDetailsFrame(this, property);
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
                        LocalDate requestedStartDate = ((SearchPropertyPanel) backReferencePanel).getRequestedStartDate();
                        LocalDate requestedEndDate = ((SearchPropertyPanel) backReferencePanel).getRequestedEndDate();

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

                MainFrame mainFrame = ((SearchPropertyPanel) backReferencePanel).getMainFrame();
                mainFrame.getTabbedPane().remove(0);
                mainFrame.getTabbedPane().insertTab("Home",null,new HomePanel(mainFrame),null,0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (command.equals("Cancel")) {
            try (Connection con = getConnection()) {
                String query = "DELETE FROM Bookings WHERE UserID = ? AND PropertyID = ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, MainFrame.loggedInUser.getUserID());
                pst.setInt(2, property.getPropertyID());

                pst.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            MainFrame mainFrame = ((HomePanel) backReferencePanel).getMainFrame();
            mainFrame.getTabbedPane().remove(0);
            mainFrame.getTabbedPane().insertTab("Home",null,new HomePanel(mainFrame),null,0);
        } else if (command.equals("Accept")) {
            try (Connection con = getConnection()) {
                String query = "UPDATE Bookings SET IsAccepted = ? WHERE UserID = ? AND PropertyID = ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setBoolean(1, true);
                pst.setInt(2, bookingsMap.get(property.getPropertyID()).getUserID());
                pst.setInt(3, property.getPropertyID());

                pst.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            MainFrame mainFrame = ((HomePanel) backReferencePanel).getMainFrame();
            mainFrame.getTabbedPane().remove(0);
            mainFrame.getTabbedPane().insertTab("Home",null,new HomePanel(mainFrame),null,0);
        }
    }
}
