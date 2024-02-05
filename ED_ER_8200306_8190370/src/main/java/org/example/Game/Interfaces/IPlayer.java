package org.example.Game.Interfaces;

import org.example.Structures.Implementations.ArrayUnorderedList;

/**
 * Interface representing a player in the game.
 */
public interface IPlayer {

    /**
     * Sets the base location for the player.
     *
     * @param base the base location to set
     */
    void setBase(ILocation base);

    /**
     * Gets the base location of the player.
     *
     * @return the base location
     */
    ILocation getBase();

    /**
     * Gets the name of the player.
     *
     * @return the name of the player
     */
    String getName();

    /**
     * Gets the list of bots associated with the player.
     *
     * @return the list of bots
     */
    ArrayUnorderedList<IBot> getBots();

    /**
     * Allows the player to select a base location from the given game map.
     *
     * @param gameMap     the game map from which the player selects the base
     * @param otherPlayer the other player to prevent selecting the same base location
     */
    void selectBase(IGameMap gameMap, IPlayer otherPlayer);

    /**
     * Adds a bot to the player's list of bots.
     *
     * @param bot the bot to add
     */
    void addBot(IBot bot);

    /**
     * Returns a string representation of the player.
     *
     * @return a string representation of the player
     */
    String toString();
}
