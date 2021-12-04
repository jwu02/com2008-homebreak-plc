package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static database.OpenConnection.getConnection;

public class User {
    private int userID;
    private String forename;
    private String surname;
    private String email;
    private String mobile;
    private String role;
    private boolean isSuperHost;

    public User() {
        // keep empty constructor for initial startup of application
        // when no users are logged in
        this.userID = 0;
        this.forename = "";
        this.surname = "";
        this.email = "";
        this.mobile = "";
        this.role = "";
    }

    public User(int userID, String forename, String surname, String email, String mobile, String role) {
        this.userID = userID;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.mobile = mobile;
        this.role = role;
        if (role.equals("host")) {
            // store result of isSuperHost function, so it's only evaluated once in one login session
            this.isSuperHost = isSuperHost();
        }
    }

    public int getUserID() {
        return userID;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getRole() {
        return role;
    }

    public boolean getIsSuperHost() {return isSuperHost;}

    public boolean isSuperHost() {
        try (Connection con = getConnection()) {
            String query = "SELECT SUM(CheckinScore + AccuracyScore + LocationScore + ValueScore + CleaninessScore + CommunicationScore) / (COUNT(*)*6) as AvgReviewScore" +
                    " FROM Reviews as r, Properties as p" +
                    " WHERE p.UserID = ?";

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userID);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double averageReviewScore = rs.getDouble("AvgReviewScore");
                if (averageReviewScore >= 4.7) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    };
}
