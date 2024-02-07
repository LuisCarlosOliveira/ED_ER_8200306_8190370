package org.example.Game.MovementStrategy;

import org.example.Game.Entities.FlagGameWGraph;
import org.example.Game.Entities.GameMap;
import org.example.Game.Entities.Interfaces.IBot;
import org.example.Game.Entities.Interfaces.IGameMap;
import org.example.Game.Entities.Interfaces.ILocation;
import org.example.Game.MovementStrategy.Interfaces.IMovementStrategy;
import org.example.Structures.Implementations.ArrayUnorderedList;

import java.util.Random;

/**
 * Movement strategy that selects the next bot move randomly from accessible locations on the game map.
 */
public class RandomMovementStrategy implements IMovementStrategy {

    private final Random random = new Random();

    @Override
    public ILocation nextMove(IBot bot, IGameMap gameMap) {
        FlagGameWGraph<ILocation> graph = ((GameMap) gameMap).getLocations();

        ILocation currentLocation = bot.getBotLocation();
        ArrayUnorderedList<ILocation> neighbors = graph.getNeighbors(currentLocation);

        if (neighbors.isEmpty()) {
            // Stay in the same location if there are no neighbors
            return currentLocation;
        }

        int nextIndex = random.nextInt(neighbors.size());
        ILocation nextLocation = neighbors.get(nextIndex);

        // Return the chosen location for the next move
        return nextLocation;
    }
}