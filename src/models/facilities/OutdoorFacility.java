package models.facilities;

import java.util.LinkedHashMap;

public class OutdoorFacility extends Facility {
    private boolean hasFreeOnsiteParking;
    private boolean hasOnRoadParking;
    private boolean hasPaidCarPark;
    private boolean hasPatio;
    private boolean hasBarbecue;

    private LinkedHashMap<String, Boolean> outdoorFacilityMap = new LinkedHashMap<>();

    public OutdoorFacility(boolean hasFreeOnsiteParking, boolean hasOnRoadParking, boolean hasPaidCarPark,
                           boolean hasPatio, boolean hasBarbecue) {
        this.hasFreeOnsiteParking = hasFreeOnsiteParking;
        this.hasOnRoadParking = hasOnRoadParking;
        this.hasPaidCarPark = hasPaidCarPark;
        this.hasPatio = hasPatio;
        this.hasBarbecue = hasBarbecue;
    }

    public LinkedHashMap<String, Boolean> getFacilityMap() {
        outdoorFacilityMap.put("hasFreeOnsiteParking", hasFreeOnsiteParking);
        outdoorFacilityMap.put("hasOnRoadParking", hasOnRoadParking);
        outdoorFacilityMap.put("hasPaidCarPark", hasPaidCarPark);
        outdoorFacilityMap.put("hasPatio", hasPatio);
        outdoorFacilityMap.put("hasBarbecue", hasBarbecue);

        return outdoorFacilityMap;
    }
}
