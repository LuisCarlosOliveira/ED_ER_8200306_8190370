package org.example;

public enum StrategiesType {
    SHORTEST_PATH("Shortest Path"),
    MINIMUM_SPANNING_TREE("Minimum Spanning Tree"),
    A_STAR("A Star");

    private final String displayName;

    StrategiesType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
