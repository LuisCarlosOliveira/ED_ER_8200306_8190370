package org.example.Game.Interfaces;

import org.example.Structures.Implementations.Network;

/**
 * Represents the interface for a game map in the context of the game.
 */
public interface IGameMap {

    /**
     * Generates a random map based on the provided parameters.
     *
     * @param quantityLocations Number of locations on the map.
     * @param bidirectional     Indicates if the paths are bidirectional.
     * @param densityEdges      Edge density in the graph.
     * @return The instance of IGameMap after the map creation.
     */
    IGameMap generateRandomMap(int quantityLocations, boolean bidirectional, double densityEdges);

    /**
     * Exports the current map to a file.
     *
     * @param pathFile Path to the file for exporting the map.
     */
    void exportMap(String pathFile);

    /**
     * Imports a map from a file.
     *
     * @param filePath Path to the file from which to import the map.
     */
    void importMap(String filePath);

    /**
     * Retrieves the network of locations that constitute the game map.
     *
     * @return The network of locations.
     */
    Network<ILocation> getLocations();

    /**
     * Retrieves the location of Player One's flag on the game map.
     *
     * @return The location of Player One's flag.
     */
    ILocation  getPlayerOneFlagLocation ();

    /**
     * Retrieves the location of Player Two's flag on the game map.
     *
     * @return The location of Player Two's flag.
     */
    ILocation  getPlayerTwoFlagLocation ();

    /**
     * Provides a string representation of the game map.
     *
     * @return A string representation of the game map.
     */
    @Override
    String toString();
}
