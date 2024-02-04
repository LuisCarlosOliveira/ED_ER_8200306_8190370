package org.example;

import org.example.Interfaces.IFlag;

public class Flag implements IFlag {
    private Player owner;
    private Location currentLocation;
    private boolean isCaptured;

    public Flag(Player owner, Location startingLocation) {
        this.owner = owner;
        this.currentLocation = startingLocation;
        this.isCaptured = false;
    }

    public Player getOwner() {
        return owner;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }

    @Override
    public String toString() {
        return "Flag{" +
                "owner=" + owner.getName() +
                ", currentLocation=" + currentLocation +
                ", isCaptured=" + isCaptured +
                '}';
    }
}
