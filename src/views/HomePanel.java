package views;

import models.Booking;
import models.Property;
import models.Review;
import models.User;

import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import static database.OpenConnection.getConnection;

public class HomePanel extends JPanel {
    private MainFrame mainFrame;

    public HomePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Bookings"));

        if (MainFrame.loggedInUser.getRole().equals("guest")) {
            try (Connection con = getConnection()) {
                String query = "SELECT * FROM Bookings as b, Properties AS p, Users AS u, Addresses AS a " +
                        "WHERE b.PropertyID = p.PropertyID" +
                        "  AND p.UserID = u.UserID" +
                        "  AND p.AddressID = a.AddressID" +
                        "  AND b.UserID = ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, MainFrame.loggedInUser.getUserID());
                ResultSet rs = pst.executeQuery();

                JPanel bookedPropertiesPanel = new JPanel();
                bookedPropertiesPanel.setLayout(new BoxLayout(bookedPropertiesPanel, BoxLayout.Y_AXIS));

                while (rs.next()) {
                    LocalDate startDate = rs.getDate("StartDate").toLocalDate();
                    LocalDate endDate = rs.getDate("EndDate").toLocalDate();
                    boolean isAccepted = rs.getBoolean("IsAccepted");

                    Property property = Property.selectProperty(rs);

                    // get related review to a property if it exists
                    query = "SELECT * From Reviews WHERE UserID = ? AND PropertyID = ? LIMIT 1";
                    PreparedStatement pstReview = con.prepareStatement(query);
                    pstReview.setInt(1, MainFrame.loggedInUser.getUserID());
                    pstReview.setInt(2, property.getPropertyID());

                    Review review = null;
                    ResultSet rsReview = pstReview.executeQuery();
                    if (rsReview.next()) {
                        int checkInScore = rsReview.getInt("CheckInScore");
                        int accuracyScore = rsReview.getInt("AccuracyScore");
                        int locationScore = rsReview.getInt("LocationScore");
                        int valueScore = rsReview.getInt("ValueScore");
                        int cleaninessScore = rsReview.getInt("CleaninessScore");
                        int communicationScore = rsReview.getInt("CommunicationScore");

                        review = new Review(MainFrame.loggedInUser.getUserID(), property.getPropertyID(), checkInScore,
                                accuracyScore, locationScore, valueScore, cleaninessScore, communicationScore);
                    }

                    Booking booking = new Booking(MainFrame.loggedInUser, property, startDate, endDate, isAccepted, review);

                    bookedPropertiesPanel.add(new HomePanelBookmarkPanel(this, booking));
                }

                add(bookedPropertiesPanel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (MainFrame.loggedInUser.getRole().equals("host")) {
            try (Connection con = getConnection()) {
                String query = "SELECT * FROM Bookings as b, Properties AS p, Users AS u, Addresses AS a " +
                        "WHERE b.PropertyID = p.PropertyID" +
                        "  AND p.UserID = u.UserID" +
                        "  AND p.AddressID = a.AddressID" +
                        "  AND p.UserID = ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, MainFrame.loggedInUser.getUserID());
                ResultSet rs = pst.executeQuery();

                JPanel bookedPropertiesPanel = new JPanel();
                bookedPropertiesPanel.setLayout(new BoxLayout(bookedPropertiesPanel, BoxLayout.Y_AXIS));

                while (rs.next()) {
                    int guestID = rs.getInt("b.UserID");
                    LocalDate startDate = rs.getDate("StartDate").toLocalDate();
                    LocalDate endDate = rs.getDate("EndDate").toLocalDate();
                    boolean isAccepted = rs.getBoolean("IsAccepted");

                    query = "SELECT * FROM Users WHERE UserID = ?";
                    PreparedStatement pstGuest = con.prepareStatement(query);
                    pstGuest.setInt(1, guestID);
                    ResultSet rsGuest = pstGuest.executeQuery();
                    rsGuest.next();

                    String forename = rsGuest.getString("Forename");
                    String surname = rsGuest.getString("Surname");
                    String email = rsGuest.getString("Email");
                    String mobile = rsGuest.getString("Mobile");
                    String role = rsGuest.getString("Role");

                    User guest = new User(guestID, forename, surname, email, mobile, role);
                    Property property = Property.selectProperty(rs);
                    Booking booking = new Booking(guest, property, startDate, endDate, isAccepted, null);

                    bookedPropertiesPanel.add(new HomePanelBookmarkPanel(this, booking));
                }

                add(bookedPropertiesPanel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }
}
