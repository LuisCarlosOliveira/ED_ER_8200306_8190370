package org.example;

import org.example.Structures.Implementations.ArrayUnorderedList;
import org.example.Structures.Implementations.LinkedQueue;
import java.util.Scanner;

/**
 * Represents a player in the game.
 */
public class Player {
    private String name;
    private Location base;
    private ArrayUnorderedList<Bot> bots;

    /**
     * Constructs a player with the given name.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.base = null;
        this.bots = new ArrayUnorderedList<>();
    }

    /**
     * Sets the base location for the player.
     *
     * @param base the base location to set
     */
    public void setBase(Location base) {
        this.base = base;
    }

    /**
     * Gets the base location of the player.
     *
     * @return the base location
     */
    public Location getBase() {
        return base;
    }

    /**
     * Gets the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of bots associated with the player.
     *
     * @return the list of bots
     */
    public ArrayUnorderedList<Bot> getBots() {
        return bots;
    }

    /**
     * Allows the player to select a base location from the given game map.
     *
     * @param gameMap the game map from which the player selects the base
     */
    public void selectBase(GameMap gameMap, Player otherPlayer) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(this.name + ", select the base:");
        System.out.println("\nLocations:");
        System.out.println(gameMap.getLocations().vertexToString());

        System.out.print("Enter the number of the desired location: ");
        int selectedLocationIndex = scanner.nextInt();

        if (selectedLocationIndex >= 1 && selectedLocationIndex <= gameMap.getLocations().size()) {
            // Adapt to obtain the location directly from the network
            Location selectedLocation = gameMap.getLocations().getVertex(selectedLocationIndex - 1);

            // Check if the selected location is already taken by the other player
            if (isLocationAvailable(selectedLocation, otherPlayer)) {
                System.out.println("Base of player " + this.name + " selected at: " + selectedLocation);

                // Store the location of the player's base
                setBase(selectedLocation);
            } else {
                System.out.println("Selected location is already taken. Choose another location.");
                selectBase(gameMap, otherPlayer); // Recursive call for another attempt.
            }
        } else {
            System.out.println("Invalid location number. Try again.");
            selectBase(gameMap, otherPlayer); // Recursive call in case of invalid input.
        }
    }

    /**
     * Checks if a given location is available for selection.
     *
     * @param selectedLocation The location to be checked for availability.
     * @param otherPlayer      The other player to compare the selected location with their base.
     * @return True if the location is available, false if it is already taken by the other player.
     */
    private boolean isLocationAvailable(Location selectedLocation, Player otherPlayer) {
        if (otherPlayer.getBase() != null && otherPlayer.getBase().equals(selectedLocation)) {
            return false; // Location is already taken by the other player
        }
        return true; // Location is available
    }

    /**
     * Returns a string representation of the player.
     *
     * @return a string representation of the player
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Player: ").append(name).append("\n");

        if (base != null) {
            result.append("Base: ").append(base.toString()).append("\n");
        } else {
            result.append("Base not selected.\n");
        }

        return result.toString();
    }

    public void addBot(Bot bot) {
        this.bots.addToFront(bot);
    }
}
