package org.example.Game.Entities.Interfaces;

/**
 * Interface representing a flag in a game.
 */
public interface IFlag {
    /**
     * Gets the player who owns the flag.
     *
     * @return The player who owns the flag.
     */
    IPlayer getOwner();

    /**
     * Gets the current location of the flag.
     *
     * @return The current location of the flag.
     */
    ILocation getCurrentLocation();

    /**
     * Sets the current location of the flag.
     *
     * @param location The new location for the flag.
     */
    void setCurrentLocation(ILocation location);

    String toString();

}
