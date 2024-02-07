package org.example.Game.Entities.Interfaces;

import org.example.Structures.Implementations.ArrayUnorderedList;

/**
 * Interface representing a player in the game.
 */
public interface IPlayer {

    IFlag getFlag();
    void setFlagBase(IFlag base);

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
     */
    void selectBase(IGameMap gameMap);

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
