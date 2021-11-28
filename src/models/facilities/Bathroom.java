package models.facilities;

public class Bathroom {
    private boolean hasToilet;
    private boolean hasBath;
    private boolean hasShower;
    private boolean sharedWithHost;

    public Bathroom(boolean hasToilet, boolean hasBath, boolean hasShower, boolean sharedWithHost) {
        this.hasToilet = hasToilet;
        this.hasBath = hasBath;
        this.hasShower = hasShower;
        this.sharedWithHost = sharedWithHost;
    }

    public boolean getHasToilet() {
        return hasToilet;
    }

    public boolean getHasBath() {
        return hasBath;
    }

    public boolean getHasShower() {
        return hasShower;
    }

    public boolean getSharedWithHost() {
        return sharedWithHost;
    }
}
