package org.example.Game.Entities.Interfaces;

import org.example.Structures.Implementations.ArrayUnorderedList;
import org.example.Structures.Interfaces.GraphADT;
import org.example.Structures.Interfaces.NetworkADT;

/**
 * Interface representing a weighted graph for a flag game.
 *
 * @param <T> The type of vertices in the graph.
 */
public interface IFlagGameWGraph<T> extends NetworkADT<T> {

    /**
     * Checks if the graph contains a specific vertex.
     *
     * @param vertex The vertex to check.
     * @return True if the graph contains the vertex, otherwise false.
     */
    boolean containsVertex(T vertex);

    /**
     * Retrieves a list of all vertices in the graph.
     *
     * @return A list of all vertices in the graph.
     */
    ArrayUnorderedList<T> getAllVertices();

    /**
     * Retrieves a list of neighbors of a specific vertex.
     *
     * @param vertex The vertex to retrieve neighbors for.
     * @return A list of neighbors of the specified vertex.
     */
    ArrayUnorderedList<T> getNeighbors(T vertex);

    /**
     * Checks if a given index is valid within the graph.
     *
     * @param index The index to check.
     * @return True if the index is valid, otherwise false.
     */
    boolean indexIsValid(int index);

    /**
     * Returns a string representation of the graph.
     *
     * @return A string representation of the graph.
     */
    String toString();
}
