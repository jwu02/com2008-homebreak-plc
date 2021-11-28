package models.facilities;

import javax.swing.*;

public class UtilityFacility extends Facility {
    private boolean hasHeating;
    private boolean hasWashingMachine;
    private boolean hasDryingMachine;
    private boolean hasFireExtinguisher;
    private boolean hasSmokeAlarm;
    private boolean hasFirstAidKit;

    public UtilityFacility(boolean hasHeating, boolean hasWashingMachine, boolean hasDryingMachine, boolean hasFireExtinguisher, boolean hasSmokeAlarm, boolean hasFirstAidKit) {
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
}
