package views;

import models.Booking;
import models.Property;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

import static database.OpenConnection.getConnection;

public class PropertyBookmarkPanel extends JPanel implements ActionListener {
    private JPanel backReferencePanel;
    private Property property;
    private HashMap<Integer, Booking> bookingsMap;

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
        add(new JLabel("<html>"+property.getDescription()+"</html>"),gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        if (backReferencePanel instanceof SearchPropertyPanel) {
            if (isAvailableWithinRequestedPeriod()) {
                add(new JLabel("Available within requested period."),gbc);
            } else {
                add(new JLabel("Not available within requested period."),gbc);
            }
        } else if (backReferencePanel instanceof HomePanel) {
            this.bookingsMap = ((HomePanel) backReferencePanel).getBookingsMap();

            if (isBookingAccepted()) {
                add(new JLabel("Booking accepted!"),gbc);
            } else {
                add(new JLabel("Booking awaiting acceptance from host."),gbc);
            }
        }

        gbc.gridx = 0;
        gbc.gridy = 4;
        JButton moreDetailsButton = new JButton("More Details");
        moreDetailsButton.addActionListener(this);
        add(moreDetailsButton,gbc);
        // only if a user is logged in they can book a property or cancel a booking
        if (MainFrame.loggedInUser.getUserID() != 0) {
            gbc.gridx = 1;
            gbc.gridy = 4;
            if (backReferencePanel instanceof SearchPropertyPanel) {
                JButton bookButton = new JButton("Book");
                bookButton.addActionListener(this);
                add(bookButton, gbc);
            } else if (backReferencePanel instanceof HomePanel) {
                JButton bookButton = new JButton("Cancel");
                bookButton.addActionListener(this);
                add(bookButton, gbc);
            }
        }
    }

    public boolean isBookingAccepted() {
        return bookingsMap.get(property.getPropertyID()).isAccepted();
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
            new MorePropertyDetailsFrame(property);
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
        }
    }
}
