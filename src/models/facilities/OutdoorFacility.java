package models.facilities;

public class OutdoorFacility extends Facility {
    private boolean hasFreeOnsiteParking;
    private boolean hasOnRoadParking;
    private boolean hasPaidCarPark;
    private boolean hasPatio;
    private boolean hasBarbecue;

    public OutdoorFacility(boolean hasFreeOnsiteParking, boolean hasOnRoadParking, boolean hasPaidCarPark, boolean hasPatio, boolean hasBarbecue) {
        this.hasFreeOnsiteParking = hasFreeOnsiteParking;
        this.hasOnRoadParking = hasOnRoadParking;
        this.hasPaidCarPark = hasPaidCarPark;
        this.hasPatio = hasPatio;
        this.hasBarbecue = hasBarbecue;
    }

    public boolean hasFreeOnsiteParking() {
        return hasFreeOnsiteParking;
    }

    public boolean hasOnRoadParking() {
        return hasOnRoadParking;
    }

    public boolean hasPaidCarPark() {
        return hasPaidCarPark;
    }

    public boolean hasPatio() {
        return hasPatio;
    }

    public boolean hasBarbecue() {
        return hasBarbecue;
    }
}
