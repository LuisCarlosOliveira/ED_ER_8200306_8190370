package org.example;

import org.example.MovementStrategy.MovementStrategy;

public class Bot {
    private MovementStrategy strategy;
    private final String playerName;
    private Location botLocation;

    private boolean hasFlag;

    public Bot(MovementStrategy strategy, String playerName, Location botLocation) {
        this.strategy = strategy;
        this.playerName = playerName;
        this.botLocation = botLocation;
    }

    public MovementStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(MovementStrategy strategy) {
        this.strategy = strategy;
    }

    public String getPlayerName() {
        return playerName;
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

    @Override
    public String toString() {
        return "Bot{" +
                "strategy=" + strategy +
                ", playerName='" + playerName + '\'' +
                ", botLocation=" + botLocation +
                '}';
    }
}

