package org.example.Game.Entities;

import org.example.Game.Entities.Interfaces.*;
import org.example.Game.MovementStrategy.Interfaces.IMovementStrategy;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a bot in the game.
 */
public class Bot implements IBot {
    private static final AtomicInteger idGenerator = new AtomicInteger(0); // For unique ID generation
    private final int id; // Unique ID for each bot
    private IMovementStrategy strategy;
    private final IPlayer player;
    private ILocation botLocation;
    private boolean hasFlag = false;

    /**
     * Constructs a bot with the specified movement strategy, player, and initial location.
     *
     * @param strategy    the movement strategy of the bot
     * @param player      the player to whom the bot belongs
     * @param botLocation the initial location of the bot
     */
    public Bot(IMovementStrategy strategy, IPlayer player, ILocation botLocation) {
        this.id = idGenerator.incrementAndGet(); // Assign and increment the unique ID
        this.strategy = strategy;
        this.player = player;
        this.botLocation = botLocation;
    }

    /**
     * Gets the movement strategy of the bot.
     *
     * @return the movement strategy
     */
    public IMovementStrategy getStrategy() {
        return strategy;
    }

    /**
     * Sets the movement strategy of the bot.
     *
     * @param strategy the new movement strategy to set
     */
    public void setStrategy(IMovementStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Gets the player to whom the bot belongs.
     *
     * @return the player
     */
    public IPlayer getPlayer() {
        return player;
    }

    /**
     * Gets the current location of the bot.
     *
     * @return the current location
     */
    public ILocation getBotLocation() {
        return botLocation;
    }

    /**
     * Sets the current location of the bot.
     *
     * @param botLocation the new location to set
     */
    public void setBotLocation(ILocation botLocation) {
        this.botLocation = botLocation;
    }

    /**
     * Executes the movement of the bot based on its strategy.
     *
     * @param gameMap the game map on which the movement occurs
     */
    public void executeMovement(IGameMap gameMap) {
        if (strategy != null) {
            ILocation nextLocation = strategy.nextMove(this, gameMap);
            if (nextLocation != null) {
                setBotLocation(nextLocation);
                System.out.println("Bot moved to: " + nextLocation);
            }
        }
    }

    /**
     * Checks if the bot has captured the flag.
     *
     * @return true if the bot has the flag, false otherwise
     */
    public boolean hasFlag() {
        return hasFlag;
    }

    /**
     * Sets whether the bot has the flag.
     *
     * @param hasFlag true if the bot has the flag, false otherwise
     */
    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    /**
     * Gets the unique ID of the bot.
     *
     * @return the bot's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a string representation of the bot.
     *
     * @return a string representation of the bot
     */
    @Override
    public String toString() {
        return "Bot{" +
                "id=" + id +
                ", strategy=" + strategy +
                ", playerName='" + player.getName() + '\'' +
                ", botLocation=" + botLocation +
                '}';
    }
}

