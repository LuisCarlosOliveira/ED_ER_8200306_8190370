package org.example;

public class Bot {
    private final StrategiesType strategy;
    private final String playerName;
    private Location botLocation;

    public Bot(StrategiesType strategy, String playerName, Location botLocation) {
        this.strategy = strategy;
        this.playerName = playerName;
        this.botLocation = botLocation;
    }

    public StrategiesType getStrategy() {
        return strategy;
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

    public void executeMovement() {
        if (strategy != null) {

        }
    }

    @Override
    public String toString() {
        return "Bot: " +
                "\n Strategy - " + strategy.toString() +
                "\n Assigned - " + playerName +
                "\n " + botLocation + "\n";
    }
}
