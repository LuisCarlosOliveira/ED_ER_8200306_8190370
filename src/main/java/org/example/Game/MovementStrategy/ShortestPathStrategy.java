package org.example.Game.MovementStrategy;

import org.example.Game.Entities.FlagGameWGraph;
import org.example.Game.Entities.GameMap;
import org.example.Game.Entities.Interfaces.IBot;
import org.example.Game.Entities.Interfaces.IGameMap;
import org.example.Game.Entities.Interfaces.ILocation;
import org.example.Game.MovementStrategy.Interfaces.IMovementStrategy;
import org.example.Game.MovementStrategy.Interfaces.IShortestPathStrategy;

import java.util.Iterator;

/**
 * Strategy class that defines the movement behavior of a bot based on the shortest path algorithm.
 * Bots moving with this strategy will attempt to move towards their target (either the enemy flag or their own flag)
 * by the shortest path possible.
 */
public class ShortestPathStrategy extends MovementStrategy implements IShortestPathStrategy {

    /**
     * Determines the next move of the bot based on the shortest path algorithm.
     *
     * @param bot The bot whose movement is being determined.
     * @param gameMap The game map.
     * @return The next location for the bot to move to.
     */
    @Override
    public ILocation nextMove(IBot bot, IGameMap gameMap) {
        FlagGameWGraph<ILocation> graph = ((GameMap) gameMap).getLocations();
        ILocation currentLocation = bot.getBotLocation();
        ILocation targetLocation;

        if (bot.hasFlag()) {
            // If the bot has the flag, it aims for its player's base.
            targetLocation = bot.getPlayer().getBase();
            System.out.println("Bot " + bot.getId() + " has the flag, returning to base.");
        } else {
            // If the bot doesn't have the flag, it aims for the enemy flag.
            targetLocation = getEnemyFlagLocation(bot, gameMap);
            System.out.println("Bot " + bot.getId() + " is moving towards the enemy flag.");
        }

        Iterator<ILocation> pathIterator = graph.iteratorShortestPath(currentLocation, targetLocation);

        if (pathIterator != null && pathIterator.hasNext()) {
            ILocation nextLocation = pathIterator.next();

            // If the next location is the current one, attempt to advance to the next element, if any.
            if (nextLocation.equals(currentLocation) && pathIterator.hasNext()) {
                nextLocation = pathIterator.next();
            }

            System.out.println("Bot " + bot.getId() + " moving to " + nextLocation);
            return nextLocation;
        } else {
            // If there is no valid path, the bot remains at its current location.
            System.out.println("Bot " + bot.getId() + " stays at " + currentLocation + " due to lack of valid path.");
            return currentLocation;
        }
    }

    /**
     * Retrieves the location of the enemy flag.
     *
     * @param bot The bot whose enemy flag location is being determined.
     * @param gameMap The game map.
     * @return The location of the enemy flag.
     */
    public ILocation getEnemyFlagLocation(IBot bot, IGameMap gameMap) {
        if (bot.getPlayer().getBase().equals(gameMap.getPlayerOneFlagLocation())) {
            return gameMap.getPlayerTwoFlagLocation();
        } else {
            return gameMap.getPlayerOneFlagLocation();
        }
    }
}




