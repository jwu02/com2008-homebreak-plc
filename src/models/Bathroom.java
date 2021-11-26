package models;

public class Bathroom {
    private Boolean hasToilet;
    private Boolean hasBath;
    private Boolean hasShower;
    private Boolean sharedWithHost;

    public Bathroom(Boolean hasToilet, Boolean hasBath, Boolean hasShower, Boolean sharedWithHost) {
        this.hasToilet = hasToilet;
        this.hasBath = hasBath;
        this.hasShower = hasShower;
        this.sharedWithHost = sharedWithHost;
    }

    public Boolean getHasToilet() {
        return hasToilet;
    }

    public Boolean getHasBath() {
        return hasBath;
    }

    public Boolean getHasShower() {
        return hasShower;
    }

    public Boolean getSharedWithHost() {
        return sharedWithHost;
    }
}
