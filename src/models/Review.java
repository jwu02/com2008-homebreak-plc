package models;

public class Review {
    int userID;
    int propertyID;
    int checkinScore;
    int accuracyScore;
    int locationScore;
    int valueScore;
    int cleaninessScore;
    int communicationScore;

    public Review(int userID, int propertyID, int checkinScore, int accuracyScore, int locationScore,
                  int valueScore, int cleaninessScore, int communicationScore) {
        this.userID = userID;
        this.propertyID = propertyID;
        this.checkinScore = checkinScore;
        this.accuracyScore = accuracyScore;
        this.locationScore = locationScore;
        this.valueScore = valueScore;
        this.cleaninessScore = cleaninessScore;
        this.communicationScore = communicationScore;
    }

    public int getUserID() {
        return userID;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public int getCheckinScore() {
        return checkinScore;
    }

    public int getAccuracyScore() {
        return accuracyScore;
    }

    public int getLocationScore() {
        return locationScore;
    }

    public int getValueScore() {
        return valueScore;
    }

    public int getCleaninessScore() {
        return cleaninessScore;
    }

    public int getCommunicationScore() {
        return communicationScore;
    }
}
