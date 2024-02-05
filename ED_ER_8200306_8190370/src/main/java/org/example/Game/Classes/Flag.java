package org.example.Game.Classes;

import org.example.Game.Interfaces.IFlag;

/**
 * Represents a flag in the game that players can capture and defend.
 */
public class Flag implements IFlag {
    private Player owner;
    private Location currentLocation;
    private boolean isCaptured;

    /**
     * Constructs a flag with the given owner and starting location.
     *
     * @param owner            The player who owns the flag.
     * @param startingLocation The initial location of the flag.
     */
    public Flag(Player owner, Location startingLocation) {
        this.owner = owner;
        this.currentLocation = startingLocation;
        this.isCaptured = false;
    }

    /**
     * Retrieves the owner of the flag.
     *
     * @return The player who owns the flag.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Retrieves the current location of the flag.
     *
     * @return The current location of the flag.
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Sets the current location of the flag.
     *
     * @param location The new location to set for the flag.
     */
    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    /**
     * Checks if the flag is captured by a player.
     *
     * @return True if the flag is captured, false otherwise.
     */
    public boolean isCaptured() {
        return isCaptured;
    }

    /**
     * Checks if the flag is captured by a player.
     *
     * @return True if the flag is captured, false otherwise.
     */
    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }

    /**
     * Provides a string representation of the flag.
     *
     * @return A string representation of the flag.
     */
    @Override
    public String toString() {
        return "Flag{" +
                "owner=" + owner.getName() +
                ", currentLocation=" + currentLocation +
                ", isCaptured=" + isCaptured +
                '}';
    }
}