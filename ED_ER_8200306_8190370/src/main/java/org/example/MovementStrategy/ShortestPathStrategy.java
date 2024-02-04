package org.example.MovementStrategy;

import org.example.Bot;
import org.example.GameMap;
import org.example.Location;
import org.example.Structures.Implementations.Network;

import java.util.Iterator;

public class ShortestPathStrategy extends MovementStrategy {

    private final boolean movingTowardsEnemyFlag;

    public ShortestPathStrategy(boolean movingTowardsEnemyFlag) {
        this.movingTowardsEnemyFlag = movingTowardsEnemyFlag;
    }
    @Override
    public Location nextMove(Bot bot, GameMap gameMap) {

        Location currentLocation = bot.getBotLocation();
        Location targetLocation = movingTowardsEnemyFlag ? gameMap.getPlayerTwoFlagLocation() : gameMap.getPlayerOneFlagLocation();

        Iterator<Location> pathIterator = gameMap.getLocations().iteratorShortestPath(currentLocation, targetLocation);

        // Skip the current location
        pathIterator.next();
        return pathIterator.hasNext() ? pathIterator.next() : currentLocation;
    }
}
