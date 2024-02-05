package org.example.Game.MovementStrategy;

import org.example.Game.Interfaces.IBot;
import org.example.Game.Interfaces.IGameMap;
import org.example.Game.Interfaces.ILocation;
import org.example.Structures.Implementations.Network;

import java.util.Iterator;

/**
 * Movement strategy based on the Minimum Spanning Tree (MST) of the game map.
 * This strategy determines the bot's movement based on the shortest path within the MST leading to either the enemy flag or its own flag,
 * depending on the bot's objective.
 */
public class MinimumSpanningTreeStrategy extends MovementStrategy {

    /**
     * Constructs a new MinimumSpanningTreeStrategy instance.
     */
    public MinimumSpanningTreeStrategy() {}

    /**
     * Determines the bot's next move based on the MST of the game map.
     *
     * @param bot The bot that is moving.
     * @param gameMap The current game map, used to construct the MST and determine the path.
     * @return The next location the bot should move to.
     */
    @Override
    public ILocation nextMove(IBot bot, IGameMap gameMap) {
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

        // Build the MST from the current map
        Network<ILocation> mst = gameMap.getLocations().minimumSpanningTreeNetwork();

        // Get the indices of the locations in the MST
        int startIndex = mst.getIndex(currentLocation);
        int targetIndex = mst.getIndex(targetLocation);

        // Use iteratorShortestPath to find the path in the MST
        Iterator<ILocation> pathIterator = mst.iteratorShortestPath(startIndex, targetIndex);

        // Skip the bot's current location
        if (pathIterator.hasNext()) {
            pathIterator.next();  // Skip the current location
        }

        // Return the next step on the path, if available
        return pathIterator.hasNext() ? pathIterator.next() : currentLocation;
    }
}

