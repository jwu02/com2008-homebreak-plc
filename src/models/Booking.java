package models;

import java.time.Duration;
import java.time.LocalDate;

public class Booking {
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isAccepted;
    private User guest;
    private Property property;

    public Booking(User guest, Property property, LocalDate startDate, LocalDate endDate, boolean isAccepted) {
        this.guest = guest;
        this.property = property;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isAccepted = isAccepted;
    }

    public User getGuest() {
        return guest;
    }

    public Property getProperty() {
        return property;
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

    public static void deleteExpiredBooking() {

    }
}
