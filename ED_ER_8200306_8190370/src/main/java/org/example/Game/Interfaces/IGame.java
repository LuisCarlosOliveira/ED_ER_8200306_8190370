package org.example.Game.Interfaces;

/**
 * Represents the interface for managing and controlling the game.
 */
public interface IGame {

    /**
     * Starts the game, including setting up the map, player bases, and bots.
     */
    void startGame();

    /**
     * Converts the game map, players, and bots into a string representation.
     *
     * @return A string representation of the game.
     */
    @Override
    String toString();
}
