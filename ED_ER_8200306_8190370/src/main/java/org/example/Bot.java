package org.example;

import org.example.MovementStrategy.MovementStrategy;
import java.util.concurrent.atomic.AtomicInteger;

public class Bot {
    private static final AtomicInteger idGenerator = new AtomicInteger(0); // For unique ID generation
    private final int id; // Unique ID for each bot
    private MovementStrategy strategy;
    private final Player player;
    private Location botLocation;
    private boolean hasFlag;


    public Bot(MovementStrategy strategy, Player player, Location botLocation) {
        this.id = idGenerator.incrementAndGet(); // Assign and increment the unique ID
        this.strategy = strategy;
        this.player = player;
        this.botLocation = botLocation;
    }
    public MovementStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(MovementStrategy strategy) {
        this.strategy = strategy;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getBotLocation() {
        return botLocation;
    }

    public void setBotLocation(Location botLocation) {
        this.botLocation = botLocation;
    }

    public void executeMovement(GameMap gameMap) {
        if (strategy != null) {
            Location nextLocation = strategy.nextMove(this, gameMap);
            if (nextLocation != null) {
                setBotLocation(nextLocation);
                System.out.println("Bot moved to: " + nextLocation);
            }
        }
    }

    public boolean hasFlag() {
        return hasFlag;
    }

    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public int getId() {
        return id;
    }

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

