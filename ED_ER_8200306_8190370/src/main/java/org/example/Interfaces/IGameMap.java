package org.example.Interfaces;

import org.example.Location;
import org.example.Structures.Implementations.Network;

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
    Network<Location> getLocations();

    /**
     * Provides a string representation of the game map.
     *
     * @return A string representation of the game map.
     */
    @Override
    String toString();
}
