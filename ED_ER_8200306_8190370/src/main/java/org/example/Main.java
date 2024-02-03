package org.example;


public class Main {
    public static void main(String[] args) {
        /*
        Game game = new Game();
        game.startGame();
        System.out.println(game);
        System.out.println(game.getBots().toString());
        */

        // Step 1: Create and populate a GameMap
        GameMap originalMap = new GameMap();
        originalMap.generateRandomMap(5, true, 0.5); // Example parameters

        // Step 2: Export the map to a file
        String filePath = "exportedMap.txt";
        originalMap.exportMap(filePath);

        // Step 3: Create a new GameMap instance for import
        GameMap importedMap = new GameMap();

        // Step 4: Import the map from the file
        importedMap.importMap(filePath);

        // Step 5: Verify the import (conceptual - depends on available methods)
        // This step might involve comparing the adjacency matrices, vertices, etc.
        // Since we don't have direct access methods to compare, this step is more conceptual
        // You might print out both maps using toString() and manually verify for testing purposes
        System.out.println("Original Map:\n" + originalMap);
        System.out.println("Imported Map:\n" + importedMap);
    }
}