package models;

import java.time.LocalDate;

public class Booking {
    private int userID;
    private int propertyID;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isAccepted;

    public Booking(int propertyID, LocalDate startDate, LocalDate endDate, boolean isAccepted) {
        this.propertyID = propertyID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isAccepted = isAccepted;
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
}
