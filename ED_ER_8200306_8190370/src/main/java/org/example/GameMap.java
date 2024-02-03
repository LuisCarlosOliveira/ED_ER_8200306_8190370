package org.example;

import org.example.Interfaces.IGameMap;
import org.example.Structures.Implementations.Network;

import java.io.*;
import java.util.Random;

/**
 * Class that represents the map for the Capture the Flag game.
 * The map is composed of locations and paths between them, represented by a network.
 */
class GameMap implements IGameMap {
    private final Network<Location> locations;

    public GameMap() {
        this.locations = new Network<>();
    }

    public Network<Location> getLocations() {
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

        // Create random locations and add them to the network and the temporary array
        for (int i = 0; i < quantityLocations; i++) {
            int randomX = random.nextInt(100);
            int randomY = random.nextInt(100);
            Location newLocation = new Location(i, randomX, randomY);
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
     * @param pathFile The file path for export.
     */
    public void exportMap(String pathFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile))) {
            // Use the toString method to get the network's string representation
            String networkData = locations.toString();
            // Write this string to the file
            writer.write(networkData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imports a map from a file.
     *
     * @param filePath The file path to import from.
     */
    public void importMap(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean parsingVertices = false, parsingEdges = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Check for section headers or separator lines and skip them
                if (line.isEmpty() || line.startsWith("----") || line.startsWith("index")) {
                    continue;
                }

                // Determine which section of the file we are currently parsing
                if (line.startsWith("Vertices values")) {
                    parsingVertices = true;
                    parsingEdges = false;
                    continue;
                } else if (line.startsWith("Weights")) {
                    parsingEdges = true;
                    parsingVertices = false;
                    continue;
                }

                if (parsingVertices) {

                    String[] parts = line.split("\t");
                    if (parts.length > 1) {
                        String[] locationParts = parts[1].split(" - ")[1].replaceAll("[()]", "").split(", ");
                        // Adjust for 0-based indexing
                        int id = Integer.parseInt(parts[0]) - 1;
                        int x = Integer.parseInt(locationParts[0]);
                        int y = Integer.parseInt(locationParts[1]);

                        locations.addVertex(new Location(id, x, y));
                    }
                } else if (parsingEdges) {

                    String[] parts = line.split("\t");
                    if (parts.length > 1) {
                        String[] edgeParts = parts[0].split(" to ");
                        double weight = Double.parseDouble(parts[1].replace(",", ".").replace("km", ""));
                        // Adjust for 0-based indexing
                        int vertex1 = Integer.parseInt(edgeParts[0]) - 1;
                        // Adjust for 0-based indexing
                        int vertex2 = Integer.parseInt(edgeParts[1]) - 1;
                        locations.addEdge(vertex1, vertex2, weight);
                        // Add the reverse edge if needed
                        locations.addEdge(vertex2, vertex1, weight);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        String result;
        result = this.locations.toString();
        return result.toString();
    }
}

