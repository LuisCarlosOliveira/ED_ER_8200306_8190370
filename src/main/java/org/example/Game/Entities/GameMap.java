package org.example.Game.Entities;


import org.example.Game.Entities.Interfaces.IFlag;
import org.example.Game.Entities.Interfaces.IGameMap;
import org.example.Game.Entities.Interfaces.ILocation;
import org.example.Structures.Implementations.ArrayUnorderedList;

import java.io.*;
import java.util.Random;


/**
 * Represents the map for the Capture the Flag game, composed of locations and paths.
 */
public class GameMap implements IGameMap {
    private final FlagGameWGraph<ILocation> locations;
    private IFlag playerOneFlag;
    private IFlag playerTwoFlag;

    /**
     * Constructs a new GameMap instance with no locations or paths.
     */
    public GameMap() {
        this.locations = new FlagGameWGraph<>();
    }

    /**
     * Returns the graph of locations that constitute the game map.
     *
     * @return The graph of locations.
     */
    public FlagGameWGraph<ILocation> getLocations() {
        return locations;
    }

    /**
     * Validates the parameters for map generation.
     *
     * @param quantityLocations The number of locations to generate.
     * @param densityEdges      The density of paths between locations.
     * @throws IllegalArgumentException If the parameters are not valid.
     */
    private void validateMapParameters(int quantityLocations, double densityEdges) {
        if (quantityLocations < 1) {
            throw new IllegalArgumentException("Quantity of locations must be at least 1.");
        }
        if (densityEdges < 0 || densityEdges > 1) {
            throw new IllegalArgumentException("Edge density must be between 0 and 1.");
        }
    }

    /**
     * Generates a random map based on the specified parameters.
     *
     * @param quantityLocations The number of locations to generate.
     * @param bidirectional     Indicates if the paths are bidirectional.
     * @param densityEdges      The density of paths between locations.
     * @return The instance of this GameMap for method chaining.
     */
    public GameMap generateRandomMap(int quantityLocations, boolean bidirectional, double densityEdges) {
        validateMapParameters(quantityLocations, densityEdges);

        Random random = new Random();
        Location[] tempArray = new Location[quantityLocations];

        for (int i = 0; i < quantityLocations; i++) {
            Location newLocation = new Location(random.nextInt(100), random.nextInt(100));
            locations.addVertex(newLocation);
            tempArray[i] = newLocation;
        }

        for (int i = 0; i < quantityLocations; i++) {
            for (int j = 0; j < quantityLocations; j++) {
                if (i != j && random.nextDouble() < densityEdges) {
                    int distance = random.nextInt(15) + 1;
                    locations.addEdge(tempArray[i], tempArray[j], distance);
                    if (bidirectional) {
                        locations.addEdge(tempArray[j], tempArray[i], distance);
                    }
                }
            }
        }

        return this;
    }

    /**
     * Exports the current game map to a file.
     *
     * @param fileName The name of the file to export the map to.
     * @throws IOException If an I/O error occurs.
     */
    public void exportMap(String fileName) throws IOException {
        String fullPath = System.getProperty("user.dir") + File.separator + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
            writer.write(locations.toString());
        }
    }

    /**
     * Imports a game map from a specified file.
     *
     * @param fileName The name of the file to import the map from.
     * @throws IOException If an I/O error occurs.
     */
    public void importMap(String fileName) throws IOException {
        String filePath = System.getProperty("user.dir") + File.separator + fileName;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || !line.contains("->")) continue;

                String[] parts = line.split("->");
                String locationStr = parts[0].trim();
                Location location = parseLocation(locationStr);
                addUniqueVertex(location);

                if (parts.length > 1) {
                    String[] neighbors = parts[1].split("Location");
                    for (String neighborStr : neighbors) {
                        neighborStr = neighborStr.trim();
                        if (neighborStr.isEmpty()) continue;
                        Location neighbor = parseLocation("Location " + neighborStr);
                        addUniqueVertex(neighbor);
                        locations.addEdge(location, neighbor, calculateDistance(location, neighbor));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
        }
    }

    /**
     * Adds a vertex to the locations graph if it is not already present.
     *
     * @param location The location to add as a vertex.
     */
    private void addUniqueVertex(Location location) {
        if (!locations.containsVertex(location)) {
            locations.addVertex(location);
        }
    }

    /**
     * Parses a location from its string representation.
     *
     * @param locationStr The string representation of a location.
     * @return The parsed Location object.
     */
    private Location parseLocation(String locationStr) {
        String[] parts = locationStr.split(" - ")[1].replaceAll("[()]", "").split(", ");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        return new Location(x, y);
    }

    /**
     * Calculates the Euclidean distance between two locations.
     *
     * @param loc1 The first location.
     * @param loc2 The second location.
     * @return The Euclidean distance between loc1 and loc2.
     */
    private double calculateDistance(Location loc1, Location loc2) {
        return Math.sqrt(Math.pow(loc1.getCoordinateX() - loc2.getCoordinateX(), 2) + Math.pow(loc1.getCoordinateY() - loc2.getCoordinateY(), 2));
    }


    /**
     * Converts the game map into a string representation.
     *
     * @return A string representation of the game map.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        ArrayUnorderedList<ILocation> vertices = locations.getAllVertices();
        for (int i = 0; i < vertices.size(); i++) {
            ILocation location = vertices.get(i);
            result.append("Loc ").append(i + 1).append(": (").append(location.getCoordinateX()).append(", ").append(location.getCoordinateY()).append(")\n");
            ArrayUnorderedList<ILocation> neighbors = locations.getNeighbors(location);
            for (int j = 0; j < neighbors.size(); j++) {
                ILocation neighbor = neighbors.get(j);
                result.append(" -> Connect to Loc ").append(vertices.indexOf(neighbor) + 1).append("\n");
            }
        }
        return result.toString();
    }


    /**
     * Sets the flags for player one and player two on the map.
     *
     * @param playerOneFlag Flag for player one.
     * @param playerTwoFlag Flag for player two.
     */
    public void setFlags(IFlag playerOneFlag, IFlag playerTwoFlag) {
        this.playerOneFlag = playerOneFlag;
        this.playerTwoFlag = playerTwoFlag;
    }

    /**
     * Retrieves the location of player one's flag.
     *
     * @return The location of player one's flag.
     */
    public ILocation getPlayerOneFlagLocation() {
        return playerOneFlag.getCurrentLocation();
    }

    /**
     * Retrieves the location of player two's flag.
     *
     * @return The location of player two's flag.
     */
    public ILocation getPlayerTwoFlagLocation() {
        return playerTwoFlag.getCurrentLocation();
    }

}

