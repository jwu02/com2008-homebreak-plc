package models.facilities;

import java.util.LinkedHashMap;

public class UtilityFacility extends Facility {
    private boolean hasHeating;
    private boolean hasWashingMachine;
    private boolean hasDryingMachine;
    private boolean hasFireExtinguisher;
    private boolean hasSmokeAlarm;
    private boolean hasFirstAidKit;

    private LinkedHashMap<String, Boolean> utilityFacilityMap = new LinkedHashMap<>();

    public UtilityFacility(boolean hasHeating, boolean hasWashingMachine, boolean hasDryingMachine,
                           boolean hasFireExtinguisher, boolean hasSmokeAlarm, boolean hasFirstAidKit) {
        this.hasHeating = hasHeating;
        this.hasWashingMachine = hasWashingMachine;
        this.hasDryingMachine = hasDryingMachine;
        this.hasFireExtinguisher = hasFireExtinguisher;
        this.hasSmokeAlarm = hasSmokeAlarm;
        this.hasFirstAidKit = hasFirstAidKit;
    }

    public boolean hasHeating() {
        return hasHeating;
    }

    public boolean hasWashingMachine() {
        return hasWashingMachine;
    }

    public boolean hasDryingMachine() {
        return hasDryingMachine;
    }

    public boolean hasFireExtinguisher() {
        return hasFireExtinguisher;
    }

    public boolean hasSmokeAlarm() {
        return hasSmokeAlarm;
    }

    public boolean hasFirstAidKit() {
        return hasFirstAidKit;
    }

    public LinkedHashMap<String, Boolean> getFacilityMap() {
        utilityFacilityMap.put("hasHeating", hasHeating);
        utilityFacilityMap.put("hasWashingMachine", hasWashingMachine);
        utilityFacilityMap.put("hasDryingMachine", hasDryingMachine);
        utilityFacilityMap.put("hasFireExtinguisher", hasFireExtinguisher);
        utilityFacilityMap.put("hasSmokeAlarm", hasSmokeAlarm);
        utilityFacilityMap.put("hasFirstAidKit", hasFirstAidKit);

        return utilityFacilityMap;
    }
}
