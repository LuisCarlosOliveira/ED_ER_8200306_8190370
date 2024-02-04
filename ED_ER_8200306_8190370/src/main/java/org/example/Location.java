package org.example;

import org.example.Structures.Implementations.ArrayUnorderedList;

import java.util.Objects;

public class Location implements Comparable<Location> {
    private int coordinateX;
    private int coordinateY;


    public Location(int id, int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;

    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    @Override
    public String toString() {
        return "Location " + " - " + "(" + coordinateX + ", " + coordinateY + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return coordinateX == location.coordinateX && coordinateY == location.coordinateY;
    }

    @Override
    public int hashCode() {
        return Objects.hash( coordinateX, coordinateY);
    }

    @Override
    public int compareTo(Location other) {
        int xComparison = Integer.compare(this.coordinateX, other.coordinateX);

        if (xComparison == 0) {
            return Integer.compare(this.coordinateY, other.coordinateY);
        }

        return xComparison;
    }

}