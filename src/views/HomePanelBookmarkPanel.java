package views;

import models.Booking;
import models.ChargeBand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.Duration;
import java.time.LocalDate;

import static database.OpenConnection.getConnection;

public class HomePanelBookmarkPanel extends JPanel implements ActionListener {
    private HomePanel backReferencePanel;
    private Booking booking;

    public HomePanelBookmarkPanel(HomePanel backReferencePanel, Booking booking) {
        this.backReferencePanel = backReferencePanel;
        this.booking = booking;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel(booking.getProperty().getName()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel(booking.getProperty().getLocation().getPlace()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel(booking.isAccepted() ? "Booking accepted!" : "Booking awaiting acceptance from host."),gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Start date"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(new JLabel(String.valueOf(booking.getStartDate())),gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("End date"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(new JLabel(String.valueOf(booking.getEndDate())),gbc);

        JButton moreDetailsButton = new JButton("More Details");
        moreDetailsButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(moreDetailsButton,gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        if (MainFrame.loggedInUser.getRole().equals("host") && !booking.isAccepted()) {
            JButton acceptBookingButton = new JButton("Accept");
            acceptBookingButton.addActionListener(this);
            add(acceptBookingButton, gbc);
        }
        if (MainFrame.loggedInUser.getRole().equals("guest") && booking.isAccepted()
                && LocalDate.now().isAfter(booking.getEndDate())) {
            JButton reviewPropertyButton = new JButton("Review");
            reviewPropertyButton.addActionListener(this);
            gbc.gridx = 2;
            gbc.gridy = 5;
            add(reviewPropertyButton, gbc);
        }
        JButton cancelBookingButton = new JButton("Cancel");
        cancelBookingButton.addActionListener(this);
        gbc.gridx = 3;
        gbc.gridy = 5;
        add(cancelBookingButton, gbc);
    }

    public HomePanel getBackReferencePanel() {
        return backReferencePanel;
    }

    public Booking getBooking() {
        return booking;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("More Details")) {
            // More Details button displays more details for an property, host and facilities on a new frame
            new MorePropertyDetailsFrame(this);
        } else if (command.equals("Cancel")) {
            try (Connection con = getConnection()) {
                String query = "DELETE FROM Bookings WHERE UserID = ? AND PropertyID = ?";
                PreparedStatement pst = con.prepareStatement(query);
                if (MainFrame.loggedInUser.getRole().equals("guest")) {
                    pst.setInt(1, MainFrame.loggedInUser.getUserID());
                } else {
                    pst.setInt(1, booking.getGuest().getUserID());
                }
                pst.setInt(2, booking.getProperty().getPropertyID());

                pst.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            MainFrame mainFrame = backReferencePanel.getMainFrame();
            mainFrame.getTabbedPane().remove(0);
            mainFrame.getTabbedPane().insertTab("Home",null,new HomePanel(mainFrame),null,0);
        } else if (command.equals("Accept")) {
            try (Connection con = getConnection()) {
                String query = "UPDATE Bookings SET IsAccepted = ? WHERE UserID = ? AND PropertyID = ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setBoolean(1, true);
                pst.setInt(2, booking.getGuest().getUserID());
                pst.setInt(3, booking.getProperty().getPropertyID());

                pst.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            MainFrame mainFrame = backReferencePanel.getMainFrame();
            mainFrame.getTabbedPane().remove(0);
            mainFrame.getTabbedPane().insertTab("Home",null,new HomePanel(mainFrame),null,0);
        } else if (command.equals("Review")) {
            new PropertyReviewDetailsFrame(this, booking);
        }
    }

    public long getNumberOfNights() {
        return Duration.between(booking.getStartDate().atStartOfDay(), booking.getEndDate().atStartOfDay()).toDays();
    }

    public BigDecimal calculateTotalServiceCharge() {
        BigDecimal totalServiceCharge = new BigDecimal(0);

        for (ChargeBand cb : booking.getProperty().getChargeBands()) {
            if ((booking.getStartDate().isBefore(cb.getEndDate()) || booking.getStartDate().isEqual(cb.getEndDate())) &&
                    (booking.getEndDate().isAfter(cb.getStartDate()) || booking.getEndDate().isEqual(cb.getStartDate()))) {
                LocalDate maxStartDate =  booking.getStartDate().isAfter(cb.getStartDate()) ? booking.getStartDate() : cb.getStartDate();
                LocalDate minEndDate = booking.getEndDate().isAfter(cb.getEndDate()) ? cb.getEndDate() : booking.getEndDate();
                long overlappingDays = Duration.between(maxStartDate.atStartOfDay(), minEndDate.atStartOfDay()).toDays();

                totalServiceCharge = totalServiceCharge.add(cb.getServiceCharge().multiply(BigDecimal.valueOf(Math.abs(overlappingDays))));
            }
        }

        return totalServiceCharge;
    }

    public BigDecimal calculateTotalCleaningCharge() {
        BigDecimal totalCleaningCharge = new BigDecimal(0);

        for (ChargeBand cb : booking.getProperty().getChargeBands()) {
            if ((booking.getStartDate().isBefore(cb.getEndDate()) || booking.getStartDate().isEqual(cb.getEndDate())) &&
                    (booking.getEndDate().isAfter(cb.getStartDate()) || booking.getEndDate().isEqual(cb.getStartDate()))) {
                LocalDate maxStartDate =  booking.getStartDate().isAfter(cb.getStartDate()) ? booking.getStartDate() : cb.getStartDate();
                LocalDate minEndDate = booking.getEndDate().isAfter(cb.getEndDate()) ? cb.getEndDate() : booking.getEndDate();
                long overlappingDays = Duration.between(maxStartDate.atStartOfDay(), minEndDate.atStartOfDay()).toDays();

                totalCleaningCharge = totalCleaningCharge.add(cb.getCleaningCharge().multiply(BigDecimal.valueOf(Math.abs(overlappingDays))));
            }
        }

        return totalCleaningCharge;
    }

    public BigDecimal calculateTotalCost() {
        BigDecimal totalCost = booking.getProperty().getPricePerNight().multiply(BigDecimal.valueOf(getNumberOfNights()));
        totalCost = totalCost.add(calculateTotalServiceCharge());
        totalCost = totalCost.add(calculateTotalCleaningCharge());
        return totalCost;
    }
}
