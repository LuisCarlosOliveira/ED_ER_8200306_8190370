package org.example.Game.Entities.Interfaces;

import org.example.Game.MovementStrategy.Interfaces.IMovementStrategy;

/**
 * Represents the interface for a bot in the game.
 */
public interface IBot {

    /**
     * Gets the unique ID of the bot.
     *
     * @return the bot's ID
     */
    int getId();

    /**
     * Gets the movement strategy of the bot.
     *
     * @return the movement strategy
     */
    IMovementStrategy getStrategy();

    /**
     * Sets the movement strategy of the bot.
     *
     * @param strategy the new movement strategy to set
     */
    void setStrategy(IMovementStrategy strategy);

    /**
     * Gets the player to whom the bot belongs.
     *
     * @return the player
     */
    IPlayer getPlayer();

    /**
     * Gets the current location of the bot.
     *
     * @return the current location
     */
    ILocation getBotLocation();

    /**
     * Sets the current location of the bot.
     *
     * @param botLocation the new location to set
     */
    void setBotLocation(ILocation botLocation);

    /**
     * Executes the movement of the bot based on its strategy.
     *
     * @param gameMap the game map on which the movement occurs
     */
    void executeMovement(IGameMap gameMap);

    /**
     * Checks if the bot has captured the flag.
     *
     * @return true if the bot has the flag, false otherwise
     */
    boolean hasFlag();

    /**
     * Sets whether the bot has the flag.
     *
     * @param hasFlag true if the bot has the flag, false otherwise
     */
    void setHasFlag(boolean hasFlag);

    /**
     * Returns a string representation of the bot.
     *
     * @return a string representation of the bot
     */
    @Override
    String toString();
}
