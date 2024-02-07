package org.example.Game.MovementStrategy;

import org.example.Game.Entities.FlagGameWGraph;
import org.example.Game.Entities.GameMap;
import org.example.Game.Entities.Interfaces.IBot;
import org.example.Game.Entities.Interfaces.IGameMap;
import org.example.Game.Entities.Interfaces.ILocation;
import org.example.Game.MovementStrategy.Interfaces.IMovementStrategy;
import org.example.Game.MovementStrategy.Interfaces.IRandomMovementStrategy;
import org.example.Structures.Implementations.ArrayUnorderedList;

import java.util.Random;

/**
 * Movement strategy that selects the next bot move randomly from accessible locations on the game map.
 */
public class RandomMovementStrategy extends MovementStrategy implements IRandomMovementStrategy {

    /** A random number generator for selecting the next move. */
    private final Random random = new Random();

    /**
     * Determines the next move for the bot based on the current game map and bot location.
     *
     * @param bot The bot for which to determine the next move.
     * @param gameMap The game map containing the current state of the game.
     * @return The next location where the bot should move.
     */
    @Override
    public ILocation nextMove(IBot bot, IGameMap gameMap) {
        // Retrieve the graph representing the game map
        FlagGameWGraph<ILocation> graph = ((GameMap) gameMap).getLocations();

        // Get the current location of the bot
        ILocation currentLocation = bot.getBotLocation();

        // Get the list of neighboring locations that the bot can move to
        ArrayUnorderedList<ILocation> neighbors = graph.getNeighbors(currentLocation);

        // If there are no neighbors, stay in the same location
        if (neighbors.isEmpty()) {
            return currentLocation;
        }

        // Choose a random neighbor as the next location to move to
        int nextIndex = random.nextInt(neighbors.size());
        ILocation nextLocation = neighbors.get(nextIndex);

        // Return the chosen location for the next move
        return nextLocation;
    }
}