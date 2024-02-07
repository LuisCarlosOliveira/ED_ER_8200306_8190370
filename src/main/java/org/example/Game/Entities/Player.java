package org.example.Game.Entities;

import org.example.Game.Entities.Interfaces.*;
import org.example.Structures.Implementations.ArrayUnorderedList;

import java.util.Scanner;

/**
 * Represents a player in the game.
 */
public class Player implements IPlayer {
    private String name;
    private IFlag flagBase;
    private ArrayUnorderedList<IBot> bots;

    /**
     * Constructs a player with the given name.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.flagBase = null;
        this.bots = new ArrayUnorderedList<>();
    }

    /**
     * Sets the base location for the player.
     *
     * @param flagBase the flag base location to set
     */
    @Override
    public void setFlagBase(IFlag flagBase) {
        this.flagBase = flagBase;
    }

    @Override
    public IFlag getFlag() {
        return this.flagBase;
    }


    /**
     * Gets the base location of the player.
     *
     * @return the base location
     */
    public ILocation getBase() {
        return this.flagBase.getCurrentLocation();
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
    public ArrayUnorderedList<IBot> getBots() {
        return bots;
    }

    /**
     * Allows the player to select a base location from the given game map.
     *
     * @param gameMap the game map from which the player selects the base
     */
    @Override
    public void selectBase(IGameMap gameMap) {
        Scanner scanner = new Scanner(System.in);
        boolean baseSelected = false;

        while (!baseSelected) {
            System.out.println(this.name + ", select the base:");
            System.out.println("\nLocations:");
            System.out.println(gameMap.getLocations().vertexToString());

            System.out.print("Enter the number of the desired location: ");
            String input = scanner.nextLine();

            // Verificar se a entrada é um número
            if (input.matches("\\d+")) {
                int selectedLocationIndex = Integer.parseInt(input);

                if (selectedLocationIndex >= 1 && selectedLocationIndex <= gameMap.getLocations().size()) {
                    ILocation selectedLocation = gameMap.getLocations().getVertex(selectedLocationIndex - 1);

                    // No check for location availability, assuming it's either ensured elsewhere or not required
                    System.out.println("Base of player " + this.name + " selected at: " + selectedLocation);
                    this.setFlagBase(new Flag(this, selectedLocation));
                    baseSelected = true;

                } else {
                    System.out.println("Invalid location number. Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
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

        if (flagBase != null) {
            result.append("Base: ").append(flagBase.getCurrentLocation().toString()).append("\n");
        } else {
            result.append("Base not selected.\n");
        }

        return result.toString();
    }

    /**
     * Adds a bot to the player's list of bots.
     *
     * @param bot the bot to be added
     */
    public void addBot(IBot bot) {
        this.bots.addToFront(bot);
    }
}
