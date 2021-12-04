package models;

import java.time.LocalDate;

public class Booking {
    private User guest;
    private Property property;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isAccepted;
    private Review review;

    public Booking(User guest, Property property, LocalDate startDate, LocalDate endDate, boolean isAccepted, Review review) {
        this.guest = guest;
        this.property = property;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isAccepted = isAccepted;
        this.review = review;
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

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
