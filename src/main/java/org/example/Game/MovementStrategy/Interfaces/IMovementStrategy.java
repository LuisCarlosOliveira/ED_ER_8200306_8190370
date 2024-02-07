package org.example.Game.MovementStrategy.Interfaces;

import org.example.Game.Entities.Interfaces.IBot;
import org.example.Game.Entities.Interfaces.IGameMap;
import org.example.Game.Entities.Interfaces.ILocation;

/**
 * Interface representing a movement strategy for a bot in a game.
 */
public interface IMovementStrategy {
    /**
     * Computes the next move for the bot based on the specified game map.
     *
     * @param bot     The bot for which the next move is calculated.
     * @param gameMap The game map providing information about the environment.
     * @return The next location to which the bot should move.
     */
     ILocation nextMove(IBot bot, IGameMap gameMap);

}