package org.example.Game.Interfaces;

import org.example.Game.Classes.Location;

/**
 * Interface representing a movement strategy for a bot in a game.
 */
public interface IMovementStrategy {
    /**
     * Computes the next move for the bot based on the specified game map.
     *
     * @param bot      The bot for which the next move is calculated.
     * @param gameMap  The game map providing information about the environment.
     * @return The next location to which the bot should move.
     */
    public ILocation nextMove(IBot bot, IGameMap gameMap);


}