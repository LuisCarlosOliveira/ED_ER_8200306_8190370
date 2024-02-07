package org.example.Game.MovementStrategy;

import org.example.Game.Classes.FlagGameWGraph;
import org.example.Game.Classes.GameMap;
import org.example.Game.Interfaces.IBot;
import org.example.Game.Interfaces.IGameMap;
import org.example.Game.Interfaces.ILocation;
import org.example.Game.Interfaces.IMovementStrategy;
import org.example.Structures.Implementations.ArrayUnorderedList;
import org.example.Structures.Implementations.Network;

import java.util.Iterator;
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