package org.example.Game.MovementStrategy;

import org.example.Game.Entities.Interfaces.IBot;
import org.example.Game.Entities.Interfaces.IGameMap;
import org.example.Game.Entities.Interfaces.ILocation;
import org.example.Game.MovementStrategy.Interfaces.IMovementStrategy;

/**
 * Abstract class representing a generic movement strategy for bots in the game.
 */
public abstract class MovementStrategy implements IMovementStrategy {

    /**
     * Determines the bot's next move based on the specific strategy.
     *
     * @param bot      The bot that is moving.
     * @param gameMap  The current game map, used to determine the path.
     * @return The next location the bot should move to.
     */
    @Override
    public abstract ILocation nextMove(IBot bot, IGameMap gameMap);
}

