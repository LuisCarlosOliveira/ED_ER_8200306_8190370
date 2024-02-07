package org.example.Game.Entities;

import org.example.Game.Entities.Interfaces.IFlag;
import org.example.Game.Entities.Interfaces.ILocation;
import org.example.Game.Entities.Interfaces.IPlayer;

/**
 * Represents a flag in the game that players can capture and defend.
 */
public class Flag implements IFlag {
    private IPlayer owner;
    private ILocation currentLocation;
    private boolean isCaptured;

    /**
     * Constructs a flag with the given owner and starting location.
     *
     * @param owner            The player who owns the flag.
     */
    public Flag(IPlayer owner, ILocation currentLocation) {
        this.owner = owner;
        this.currentLocation = currentLocation;
        this.isCaptured = false;
    }

    /**
     * Retrieves the owner of the flag.
     *
     * @return The player who owns the flag.
     */
    public IPlayer getOwner() {
        return owner;
    }

    /**
     * Retrieves the current location of the flag.
     *
     * @return The current location of the flag.
     */
    public ILocation getCurrentLocation() {
        return currentLocation;
    }

    
    /**
     * Sets the current location of the flag.
     *
     * @param location The new location to set for the flag.
     */
    public void setCurrentLocation(ILocation location) {
        this.currentLocation = location;
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
