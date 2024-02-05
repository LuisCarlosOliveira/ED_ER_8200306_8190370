package org.example.Game.Interfaces;

import org.example.Game.Classes.Location;
import org.example.Game.Classes.Player;

/**
 * Interface representing a flag in a game.
 */
public interface IFlag {
    /**
     * Gets the player who owns the flag.
     *
     * @return The player who owns the flag.
     */
    Player getOwner();

    /**
     * Gets the current location of the flag.
     *
     * @return The current location of the flag.
     */
    Location getCurrentLocation();

    /**
     * Sets the current location of the flag.
     *
     * @param location The new location for the flag.
     */
    void setCurrentLocation(Location location);

    /**
     * Checks if the flag is captured.
     *
     * @return True if the flag is captured, false otherwise.
     */
    boolean isCaptured();

    /**
     * Sets the captured status of the flag.
     *
     * @param captured True to set the flag as captured, false otherwise.
     */
    void setCaptured(boolean captured);

    /**
     * Returns a string representation of the flag.
     *
     * @return A string representation of the flag.
     */
    String toString();

}
