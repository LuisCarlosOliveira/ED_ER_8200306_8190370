package org.example.Game.MovementStrategy;

import org.example.Game.Entities.FlagGameWGraph;
import org.example.Game.Entities.GameMap;
import org.example.Game.Entities.Interfaces.IBot;
import org.example.Game.Entities.Interfaces.IGameMap;
import org.example.Game.Entities.Interfaces.ILocation;
import org.example.Game.MovementStrategy.Interfaces.IMovementStrategy;

import java.util.Iterator;

/**
 * Strategy class that defines the movement behavior of a bot based on the shortest path algorithm.
 * Bots moving with this strategy will attempt to move towards their target (either the enemy flag or their own flag)
 * by the shortest path possible.
 */
public class ShortestPathStrategy implements IMovementStrategy {

    @Override
    public ILocation nextMove(IBot bot, IGameMap gameMap) {
        FlagGameWGraph<ILocation> graph = ((GameMap) gameMap).getLocations();
        ILocation currentLocation = bot.getBotLocation();
        ILocation targetLocation;

        if (bot.hasFlag()) {
            // Bot heads towards its base when it has the enemy flag.
            targetLocation = bot.getPlayer().getBase();
            System.out.println("Bot " + bot.getId() + " has the flag, heading back to base.");
        } else {
            // Bot moves towards the enemy flag otherwise.
            targetLocation = getEnemyFlagLocation(bot, gameMap);
            System.out.println("Bot " + bot.getId() + " is moving towards the enemy flag.");
        }

        Iterator<ILocation> pathIterator = graph.iteratorShortestPath(currentLocation, targetLocation);

        if (pathIterator != null && pathIterator.hasNext()) {
            ILocation nextLocation = pathIterator.next();

            // Skip the current location if it's the first in the iterator.
            if (nextLocation.equals(currentLocation) && pathIterator.hasNext()) {
                nextLocation = pathIterator.next();
            }

            System.out.println("Bot " + bot.getId() + " moving to " + nextLocation);
            return nextLocation;
        }

        System.out.println("Bot " + bot.getId() + " stays at " + currentLocation);
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



