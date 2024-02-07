package org.example.Game.Classes;


import org.example.Game.Interfaces.IFlag;
import org.example.Game.Interfaces.IGameMap;
import org.example.Game.Interfaces.ILocation;

import java.io.*;
import java.util.Random;

/**
 * Class that represents the map for the Capture the Flag game.
 * The map is composed of locations and paths between them, represented by a flagGameWGraph.
 */
public class GameMap implements IGameMap {
    private final FlagGameWGraph<ILocation> locations;
    private IFlag playerOneFlag;
    private IFlag playerTwoFlag;

    /**
     * Constructs a new GameMap instance.
     */
    public GameMap() {
        this.locations = new FlagGameWGraph<>();
    }

    /**
     * Gets the FlagGameWGraph of locations that constitute the game map.
     *
     * @return The FlagGameWGraph of locations.
     */
    public FlagGameWGraph<ILocation> getLocations() {
        return locations;
    }

    /**
     * Generates a random map based on the provided parameters.
     *
     * @param quantityLocations Number of locations on the map.
     * @param bidirectional     If the paths are bidirectional.
     * @param densityEdges      Edge density in the graph.
     * @return The same instance of GameMap after the map creation.
     */
    public GameMap generateRandomMap(int quantityLocations, boolean bidirectional, double densityEdges) {
        if (quantityLocations < 1 || densityEdges < 0 || densityEdges > 1) {
            throw new IllegalArgumentException("Invalid parameters for map creation.");
        }

        Random random = new Random();

        // Temporary array to store the locations
        Location[] tempArray = new Location[quantityLocations];

        // Create random locations and add them to the FlagGameWGraph and the temporary array
        for (int i = 0; i < quantityLocations; i++) {
            int randomX = random.nextInt(100);
            int randomY = random.nextInt(100);
            Location newLocation = new Location(randomX, randomY);
            locations.addVertex(newLocation);
            tempArray[i] = newLocation;
        }

        // Add edges between locations based on edge density
        for (int i = 0; i < quantityLocations; i++) {
            Location location1 = tempArray[i];
            boolean hasEdge = false;

            for (int j = 0; j < quantityLocations; j++) {
                if (i == j) continue;
                Location location2 = tempArray[j];

                if (random.nextDouble() < densityEdges) {
                    int randomDistance = random.nextInt(15) + 1;
                    locations.addEdge(location1, location2, randomDistance);
                    hasEdge = true;

                    if (bidirectional) {
                        locations.addEdge(location2, location1, randomDistance);
                    }
                }
            }
            // Ensure minimal connectivity if no edge was added for this location
            if (!hasEdge) {
                int j = (i + 1) % quantityLocations; // Choose a different location
                Location location2 = tempArray[j];
                int randomDistance = random.nextInt(15) + 1;
                locations.addEdge(location1, location2, randomDistance);

                if (bidirectional) {
                    locations.addEdge(location2, location1, randomDistance);
                }
            }
        }

        return this;
    }

    /**
     * Exports the current map to a file.
     *
     * @param fileName The file name  for export.
     */
    public void exportMap(String fileName) {
        String currentDir = System.getProperty("user.dir");
        String fullPath = currentDir + File.separator + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
            // Use the toString method to get the flagGameWGraph's string representation
            String flagGameWGraphData = locations.toString();
            // Write this string to the file
            writer.write(flagGameWGraphData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imports a map from a file.
     *
     * @param fileName The file name to import from.
     */
    public void importMap(String fileName) {
        String filePath = System.getProperty("user.dir") + File.separator + fileName;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || !line.contains("->")) continue;

                String[] parts = line.split("->");
                String locationStr = parts[0].trim();
                Location location = parseLocation(locationStr);
                addUniqueVertex(location); // Adiciona o vértice se ele ainda não existir

                if (parts.length > 1) {
                    String[] neighbors = parts[1].split("Location");
                    for (String neighborStr : neighbors) {
                        neighborStr = neighborStr.trim();
                        if (neighborStr.isEmpty()) continue;
                        Location neighbor = parseLocation("Location " + neighborStr);
                        addUniqueVertex(neighbor); // Garante que a localização vizinha também seja adicionada como vértice
                        locations.addEdge(location, neighbor, calculateDistance(location, neighbor)); // Adiciona aresta com peso baseado na distância
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
        }
    }

    private void addUniqueVertex(Location location) {
        if (!locations.containsVertex(location)) {
            locations.addVertex(location);
        }
    }

    private Location parseLocation(String locationStr) {
        String[] parts = locationStr.split(" - ")[1].replaceAll("[()]", "").split(", ");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        return new Location(x, y); // Usa o construtor de Location com coordenadas x e y
    }

    private double calculateDistance(Location loc1, Location loc2) {
        // Calcula a distância euclidiana entre duas localizações
        return Math.sqrt(Math.pow(loc1.getCoordinateX() - loc2.getCoordinateX(), 2) + Math.pow(loc1.getCoordinateY() - loc2.getCoordinateY(), 2));
    }


    /**
     * Converts the game map into a string representation.
     *
     * @return A string representation of the game map.
     */
    @Override
    public String toString() {
        String result;
        result = this.locations.toString();
        return result.toString();
    }

    /**
     * Sets the flags for both players on the map.
     *
     * @param playerOneFlag The flag for player one.
     * @param playerTwoFlag The flag for player two.
     */
    public void setFlags(IFlag playerOneFlag, IFlag playerTwoFlag) {
        this.playerOneFlag = playerOneFlag;
        this.playerTwoFlag = playerTwoFlag;
    }

    /**
     * Retrieves the location of player one's flag on the map.
     *
     * @return The location of player one's flag.
     */
    public ILocation getPlayerOneFlagLocation () {
        return playerOneFlag.getCurrentLocation();
    }

    /**
     * Retrieves the location of player two's flag on the map.
     *
     * @return The location of player two's flag.
     */
    public ILocation  getPlayerTwoFlagLocation () {
        return playerTwoFlag.getCurrentLocation();
    }

}

