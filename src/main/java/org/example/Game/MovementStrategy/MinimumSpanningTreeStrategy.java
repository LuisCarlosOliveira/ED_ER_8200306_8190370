package org.example.Game.MovementStrategy;


import org.example.Game.Entities.FlagGameWGraph;
import org.example.Game.Entities.GameMap;
import org.example.Game.Entities.Interfaces.IBot;
import org.example.Game.Entities.Interfaces.IGameMap;
import org.example.Game.Entities.Interfaces.ILocation;
import org.example.Game.MovementStrategy.Interfaces.IMovementStrategy;
import org.example.Structures.Implementations.Network;

import java.util.Iterator;

/**
 * Movement strategy based on the Minimum Spanning Tree (MST) of the game map.
 * This strategy determines the bot's movement based on the shortest path within the MST leading to either the enemy flag or its own flag,
 * depending on the bot's objective.

 */
public class MinimumSpanningTreeStrategy implements IMovementStrategy {

    @Override
    public ILocation nextMove(IBot bot, IGameMap gameMap) {
        FlagGameWGraph<ILocation> graph = ((GameMap) gameMap).getLocations();
        Network<ILocation> mstNetwork = graph.minimumSpanningTreeNetwork();

        ILocation currentLocation = bot.getBotLocation();
        //Sets the target location to the player's base if the bot has the flag, otherwise sets it to the enemy flag location
        ILocation targetLocation = bot.hasFlag() ? bot.getPlayer().getBase() : getEnemyFlagLocation(bot, gameMap);

        Iterator<ILocation> pathIterator = mstNetwork.iteratorShortestPath(currentLocation, targetLocation);

        if (pathIterator != null && pathIterator.hasNext()) {
            ILocation nextLocation = pathIterator.next();
            if (nextLocation.equals(currentLocation) && pathIterator.hasNext()) {
                // Skips the current location if it's the first in the iteration
                nextLocation = pathIterator.next();
            }
            return nextLocation;
        }

        return currentLocation;
    }

    private ILocation getEnemyFlagLocation(IBot bot, IGameMap gameMap) {
        if (bot.getPlayer().getBase().equals(gameMap.getPlayerOneFlagLocation())) {
            return gameMap.getPlayerTwoFlagLocation();
        } else {
            return gameMap.getPlayerOneFlagLocation();
        }
    }
}


