package models;

import java.time.Duration;
import java.time.LocalDate;

public class Booking {
    private int userID;
    private int propertyID;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isAccepted;

    public Booking(int userID, int propertyID, LocalDate startDate, LocalDate endDate, boolean isAccepted) {
        this.userID = userID;
        this.propertyID = propertyID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isAccepted = isAccepted;
    }

    public int getUserID() {
        return userID;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public long getNumberOfNights() {
        return Duration.between(startDate, endDate).toDays();
    }


}
