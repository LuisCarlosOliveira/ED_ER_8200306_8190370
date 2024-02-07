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


    /**
     * Constructs a Flag object with the specified owner and starting location.
     *
     * @param owner The player who initially owns the flag.
     * @param startingLocation The starting location of the flag.
     */
    public Flag(final IPlayer owner, final ILocation startingLocation) {
        this.owner = owner;
        this.currentLocation = startingLocation;
    }

    /**
     * Retrieves the owner of the flag.
     *
     * @return The player who currently owns the flag.
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
     * @param newLocation The new location of the flag.
     */
    public void setCurrentLocation(final ILocation newLocation) {
        this.currentLocation = newLocation;
    }

    /**
     * Provides a string representation of the flag's state.
     *
     * @return A string detailing the owner, current location, and captured state of the flag.
     */
    @Override
    public String toString() {
        return "Flag{" +
                "owner=" + owner.getName() +
                ", currentLocation=" + currentLocation +
                '}';
    }
}
