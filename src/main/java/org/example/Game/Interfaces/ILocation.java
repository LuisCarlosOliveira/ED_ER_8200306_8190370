package org.example.Game.Interfaces;

/**
 * Represents the interface for a location in the game map.
 */
public interface ILocation extends Comparable<ILocation> {

    /**
     * Gets the X-coordinate of the location.
     *
     * @return the X-coordinate
     */
    int getCoordinateX();

    /**
     * Gets the Y-coordinate of the location.
     *
     * @return the Y-coordinate
     */
    int getCoordinateY();

    /**
     * Returns a string representation of the location.
     *
     * @return a string representation of the location
     */
    @Override
    String toString();

    /**
     * Checks if this location is equal to another location.
     *
     * @param other the other location to compare
     * @return true if the locations are equal, false otherwise
     */
    @Override
    boolean equals(Object other);

    /**
     * Computes the hash code for the location.
     *
     * @return the hash code
     */
    @Override
    int hashCode();

    /**
     * Compares this location to another location for sorting purposes.
     *
     * @param other the other location to compare
     * @return a negative integer, zero, or a positive integer as this location is less than, equal to,
     * or greater than the specified location
     */
    @Override
    int compareTo(ILocation other);
}
