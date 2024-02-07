package org.example.Game.MovementStrategy.Interfaces;

import org.example.Game.Entities.Interfaces.IBot;
import org.example.Game.Entities.Interfaces.IGameMap;
import org.example.Game.Entities.Interfaces.ILocation;

/**
 * Represents the interface for a minimum spanning tree strategy for movement.
 */
public interface IMinimumSpanningTreeStrategy extends IMovementStrategy {

    /**
     * Determines the next move of the bot based on the minimum spanning tree strategy.
     *
     * @param bot     The bot whose movement is being determined.
     * @param gameMap The game map.
     * @return The next location for the bot to move to.
     */
    @Override
    ILocation nextMove(IBot bot, IGameMap gameMap);
}
