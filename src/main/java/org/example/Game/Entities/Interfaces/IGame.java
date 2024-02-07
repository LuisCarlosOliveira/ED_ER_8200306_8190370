package org.example.Game.Entities.Interfaces;

import java.io.IOException;

/**
 * Represents the interface for managing and controlling the game.
 */
public interface IGame {

    /**
     * Starts the game.
     *
     * @throws IOException if an I/O error occurs.
     */
    void startGame() throws IOException;

    /**
     * Prints the game map.
     */
    void printMap();

    /**
     * Sets bots for players.
     */
    void setBotsForPlayers();

    /**
     * Plays the game.
     *
     * @throws IOException if an I/O error occurs.
     */
    void playGame() throws IOException;
}


