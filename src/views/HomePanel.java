package views;

import javafx.util.Pair;
import models.Booking;
import models.Property;

import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static database.OpenConnection.getConnection;

public class HomePanel extends JPanel {
    private MainFrame mainFrame;

    private HashMap<Integer, Booking> bookingsMap = new HashMap<>();
    private HashMap<Integer[], Booking> bookingsMapTuple = new HashMap<>();
    private ArrayList<Booking> bookingsList = new ArrayList<>();
    private ArrayList<Property> bookedPropertiesList = new ArrayList<>();


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

                bookedPropertiesList = Property.selectProperties(rs);

                query = "SELECT * FROM Bookings WHERE UserID = ?";
                pst = con.prepareStatement(query);
                pst.setInt(1, MainFrame.loggedInUser.getUserID());
                rs = pst.executeQuery();

                while (rs.next()) {
                    int guestID = rs.getInt("UserID");
                    int propertyID = rs.getInt("PropertyID");
                    LocalDate startDate = rs.getDate("StartDate").toLocalDate();
                    LocalDate endDate = rs.getDate("EndDate").toLocalDate();
                    boolean isAccepted = rs.getBoolean("IsAccepted");

                    bookingsMap.put(propertyID, new Booking(guestID, propertyID, startDate, endDate, isAccepted));
                }

                JPanel bookedPropertiesPanel = new JPanel();
                bookedPropertiesPanel.setLayout(new BoxLayout(bookedPropertiesPanel, BoxLayout.Y_AXIS));
                for (Property p : bookedPropertiesList) {
                    bookedPropertiesPanel.add(new PropertyBookmarkPanel(this, p));
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

                bookedPropertiesList = Property.selectProperties(rs);

                query = "SELECT * FROM Bookings as b, Properties AS p " +
                        "WHERE b.PropertyID = p.PropertyID" +
                        "  AND p.UserID = ?";
                pst = con.prepareStatement(query);
                pst.setInt(1, MainFrame.loggedInUser.getUserID());
                rs = pst.executeQuery();

                while (rs.next()) {
                    int guestID = rs.getInt("b.UserID");
                    int propertyID = rs.getInt("b.PropertyID");
                    LocalDate startDate = rs.getDate("StartDate").toLocalDate();
                    LocalDate endDate = rs.getDate("EndDate").toLocalDate();
                    boolean isAccepted = rs.getBoolean("IsAccepted");

                    bookingsMap.put(propertyID, new Booking(guestID, propertyID, startDate, endDate, isAccepted));
                    bookingsList.add(new Booking(guestID, propertyID, startDate, endDate, isAccepted));
                }

                JPanel bookedPropertiesPanel = new JPanel();
                bookedPropertiesPanel.setLayout(new BoxLayout(bookedPropertiesPanel, BoxLayout.Y_AXIS));
                for (Property p : bookedPropertiesList) {
                    bookedPropertiesPanel.add(new PropertyBookmarkPanel(this, p));
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

    public HashMap<Integer, Booking> getBookingsMap() {
        return bookingsMap;
    }

    public ArrayList<Booking> getBookingsList() {
        return bookingsList;
    }
}
