package org.example.Game.Classes;

import org.example.Game.Interfaces.ILocation;

import java.util.Objects;

/**
 * Represents a location in the game map.
 */
public class Location implements ILocation {
    private int coordinateX;
    private int coordinateY;

    /**
     * Constructs a location with the given coordinates.
     *
     * @param coordinateX the X-coordinate of the location
     * @param coordinateY the Y-coordinate of the location
     */
    public Location(int id, int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;

    }

    /**
     * Gets the X-coordinate of the location.
     *
     * @return the X-coordinate
     */
    public int getCoordinateX() {
        return coordinateX;
    }

    /**
     * Gets the Y-coordinate of the location.
     *
     * @return the Y-coordinate
     */
    public int getCoordinateY() {
        return coordinateY;
    }

    /**
     * Returns a string representation of the location.
     *
     * @return a string representation of the location
     */
    @Override
    public String toString() {
        return "Location " + " - " + "(" + coordinateX + ", " + coordinateY + ")";
    }

    /**
     * Checks if this location is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return coordinateX == location.coordinateX && coordinateY == location.coordinateY;
    }

    /**
     * Computes the hash code for the location.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash( coordinateX, coordinateY);
    }

    /**
     * Compares this location to another location for sorting purposes.
     *
     * @param other the other location to compare
     * @return a negative integer, zero, or a positive integer as this location is less than, equal to,
     *         or greater than the specified location
     */
    @Override
    public int compareTo(ILocation other) {
        int xComparison = Integer.compare(this.coordinateX, other.getCoordinateX());

        if (xComparison == 0) {
            return Integer.compare(this.coordinateY, other.getCoordinateY());
        }

        return xComparison;
    }

}