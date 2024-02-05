package org.example.Game.MovementStrategy;

import org.example.Game.Interfaces.IBot;
import org.example.Game.Interfaces.IGameMap;
import org.example.Game.Interfaces.ILocation;

import java.util.Iterator;

/**
 * Strategy class that defines the movement behavior of a bot based on the shortest path algorithm.
 * Bots moving with this strategy will attempt to move towards their target (either the enemy flag or their own flag)
 * by the shortest path possible.
 */
public class ShortestPathStrategy extends MovementStrategy {

    /**
     * Constructor for the ShortestPathStrategy class.
     */
    public ShortestPathStrategy() {
    }

    /**
     * Determines the next move for the bot based on the shortest path to its target location.
     * The target location is determined by the {@code movingTowardsEnemyFlag} flag.
     *
     * @param bot     The bot for which to determine the next move.
     * @param gameMap The game map, providing context for the movement decision (e.g., locations, paths).
     * @return The next location for the bot to move to. If no move is possible, returns the bot's current location.
     */
    @Override
    public ILocation nextMove(IBot bot, IGameMap gameMap) {
        // Determine the bot's current location and its target location based on the strategy direction.
        ILocation currentLocation = bot.getBotLocation();
        ILocation baseLocation = bot.getPlayer().getBase();
        ILocation targetLocation;
        if (bot.hasFlag()) {
            if (baseLocation.equals(gameMap.getPlayerOneFlagLocation())) {
                targetLocation = baseLocation;
            } else {
                targetLocation = gameMap.getPlayerTwoFlagLocation();
            }
        } else {
            if (baseLocation.equals(gameMap.getPlayerTwoFlagLocation())) {
                targetLocation = baseLocation;
            } else {
                targetLocation = gameMap.getPlayerOneFlagLocation();
            }
        }

        // Use the game map's shortest path iterator to find the next step towards the target.
        Iterator<ILocation> pathIterator = gameMap.getLocations().iteratorShortestPath(currentLocation, targetLocation);

        // Skip the current location in the path.
        if (pathIterator.hasNext()) {
            pathIterator.next();  // Skip the current location
            // If there's a next step in the path, return it; otherwise, stay at the current location.
            if (pathIterator.hasNext()) {
                return pathIterator.next();
            }
        }
        return currentLocation;
    }
}
