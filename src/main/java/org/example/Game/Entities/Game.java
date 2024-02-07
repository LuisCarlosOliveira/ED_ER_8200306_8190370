package org.example.Game.Entities;

import org.example.Game.Entities.Interfaces.*;
import org.example.Game.MovementStrategy.Interfaces.IMovementStrategy;
import org.example.Game.MovementStrategy.MinimumSpanningTreeStrategy;
import org.example.Game.MovementStrategy.RandomMovementStrategy;
import org.example.Game.MovementStrategy.ShortestPathStrategy;
import org.example.Structures.Implementations.LinkedQueue;

import java.io.IOException;
import java.util.Scanner;

/**
 * Represents the main class for managing and controlling the game.
 */
public class Game implements IGame{
    private IGameMap gameMap;
    private IPlayer player1;
    private IPlayer player2;
    private LinkedQueue<IBot> bots;
    private boolean isGameOver;
    private String winner;

    /**
     * Constructs a new game with default settings.
     */
    public Game() {
        this.gameMap = new GameMap();
        this.player1 = new Player("Player 1");
        this.player2 = new Player("Player 2");
        this.bots = new LinkedQueue<>();
        this.isGameOver = false;
    }

    /**
     * Starts the game, including setting up the map, player bases, and bots.
     */
    public void startGame() throws IOException {
        setupGame();
        while (!isGameOver) {
            processBotTurns();
            checkGameConditions();
        }
        endGame();
    }

    /**
     * Sets up the initial state of the game, including creating a new map or importing an existing one.
     * Also, allows players to select bases and initializes bots for both players.
     */
    private void setupGame() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to create a new map (1) or import an existing one (2)?");
        int choice = scanner.nextInt();

        if (choice == 1) {
            createNewMap();
        } else if (choice == 2) {
            System.out.println("Please enter the file name to import the map:");
            scanner.nextLine();
            String fileName = scanner.next();
            this.gameMap.importMap(fileName);
        } else {
            System.out.println("Invalid option. Exiting the game.");
            return;
        }

        player1.selectBase(gameMap);
        boolean baseIsUnique;
        do {
            player2.selectBase(gameMap);
            baseIsUnique = !player1.getBase().equals(player2.getBase());
            if (!baseIsUnique) {
                System.out.println("The selected base is already taken by Player 1. Please choose a different base.");
            }
        } while (!baseIsUnique);
        this.gameMap.setFlags(player1.getFlag(), player2.getFlag());

        System.out.println("\n" + player1);
        System.out.println(player2);
        System.out.println(gameMap);

        setBotsForPlayers();
    }

    /**
     * Processes the turns of all bots in the game.
     */
    private void processBotTurns() {
        int botsSize = bots.size();
        for (int i = 0; i < botsSize; i++) {
            if (isGameOver) {
                break;
            }
            IBot currentBot = bots.dequeue();
            executeBotTurn(currentBot);
            printMap();
            this.bots.enqueue(currentBot);
            checkGameConditions();
        }
    }

    /**
     * Prints the current state of the game map.
     */
    public void printMap() {
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;

        for (ILocation location : gameMap.getLocations().getAllVertices()) {
            minX = Math.min(minX, location.getCoordinateX());
            maxX = Math.max(maxX, location.getCoordinateX());
            minY = Math.min(minY, location.getCoordinateY());
            maxY = Math.max(maxY, location.getCoordinateY());
        }

        char[][] grid = new char[maxY - minY + 1][maxX - minX + 1];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = '.'; // Fill the grid with dots for empty spaces
            }
        }

        markLocationOnGrid(grid, player1.getBase(), "B1", minX, minY);
        markLocationOnGrid(grid, player2.getBase(), "B2", minX, minY);
        markLocationOnGrid(grid, gameMap.getPlayerOneFlagLocation(), "F1", minX, minY);
        markLocationOnGrid(grid, gameMap.getPlayerTwoFlagLocation(), "F2", minX, minY);

        for (IBot bot : bots) {
            String botMarker = bot.getPlayer() == player1 ? "P1B" + bot.getId() : "P2B" + bot.getId();
            markBotOnGrid(grid, bot.getBotLocation(), botMarker, minX, minY);
        }

        System.out.println("\nGame Map Overview:");
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Marks specific locations on the grid, such as bases and flags.
     *
     * @param grid The game map grid.
     * @param location The location to mark.
     * @param marker The marker symbol for the location.
     * @param minX The minimum X coordinate on the grid.
     * @param minY The minimum Y coordinate on the grid.
     */
    private void markLocationOnGrid(char[][] grid, ILocation location, String marker, int minX, int minY) {
        int x = location.getCoordinateX() - minX;
        int y = location.getCoordinateY() - minY;
        for (int i = 0; i < marker.length() && i + x < grid[y].length; i++) {
            grid[y][x + i] = marker.charAt(i);
        }
    }

    /**
     * Marks bots on the grid.
     *
     * @param grid The game map grid.
     * @param location The bot's location.
     * @param marker The marker symbol for the bot.
     * @param minX The minimum X coordinate on the grid.
     * @param minY The minimum Y coordinate on the grid.
     */
    private void markBotOnGrid(char[][] grid, ILocation location, String marker, int minX, int minY) {
        int x = location.getCoordinateX() - minX;
        int y = location.getCoordinateY() - minY;
        for (int i = 0; i < marker.length() && i + x < grid[y].length; i++) {
            grid[y][x + i] = marker.charAt(i);
        }
    }

    /**
     * Executes the turn for a specific bot, including updating its location and checking for flag captures.
     *
     * @param bot The bot whose turn is being executed.
     */
    private void executeBotTurn(IBot bot) {
        ILocation nextLocation = bot.getStrategy().nextMove(bot, gameMap);
        bot.setBotLocation(nextLocation);

        for (IBot otherBot : bots) {
            if (!otherBot.equals(bot) && otherBot.getBotLocation().equals(nextLocation)) {
                if (otherBot.hasFlag() && !otherBot.getPlayer().equals(bot.getPlayer())) {
                    System.out.println("Bot " + bot.getId() + " (" + bot.getPlayer().getName() + ") encountered an opponent bot with the flag at location " + nextLocation);
                    resetFlagToBase(otherBot.getPlayer().getFlag());
                    otherBot.setHasFlag(false);
                }
            }
        }
        if (!bot.hasFlag() && isFlagAvailableForCapture(nextLocation, bot.getPlayer())) {
            if (nextLocation.equals(gameMap.getPlayerOneFlagLocation()) && !player1.equals(bot.getPlayer())) {
                bot.setHasFlag(true);
                System.out.println("Bot " + bot.getId() + " (" + bot.getPlayer().getName() + ") captured Player 1's flag!");
            } else if (nextLocation.equals(gameMap.getPlayerTwoFlagLocation()) && !player2.equals(bot.getPlayer())) {
                bot.setHasFlag(true);
                System.out.println("Bot " + bot.getId() + " (" + bot.getPlayer().getName() + ") captured Player 2's flag!");
            }
        }

        System.out.println("Bot " + bot.getId() + " (" + bot.getPlayer().getName() + ") moved to " + nextLocation);
    }

    /**
     * Determines if a flag is available for capture at a given location by a specified player.
     *
     * @param flagLocation The location to check for flag availability.
     * @param player The player attempting to capture the flag.
     * @return {@code true} if the flag is available for capture; {@code false} otherwise.
     */
    private boolean isFlagAvailableForCapture(ILocation flagLocation, IPlayer player) {
        for (IBot bot : bots) {
            if (bot.getPlayer().equals(player) && bot.hasFlag() && bot.getBotLocation().equals(flagLocation)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Resets a flag to its owner's base location.
     *
     * @param flag The flag to be reset.checkGameConditions
     */
    private void resetFlagToBase(IFlag flag) {
        flag.setCurrentLocation(flag.getOwner().getBase());
        System.out.println("The flag of " + flag.getOwner().getName() + " has been returned to the base.");
    }

    /**
     * Checks the game conditions to determine if a player has won by returning the flag to their base.
     */
    private void checkGameConditions() {
        for (IBot bot : this.player1.getBots()) {
            if (bot.hasFlag() && bot.getBotLocation().equals(player1.getBase())) {
                System.out.println("Winning Bot: " + bot.getId() + " (Player 1), Location: " + bot.getBotLocation());
                isGameOver = true;
                winner = this.player1.getName();
                System.out.println("Player 1's bot returned the flag to their base.");
                return;
            }
        }

        if (!isGameOver) {
            for (IBot bot : player2.getBots()) {
                if (bot.hasFlag() && bot.getBotLocation().equals(player2.getBase())) {
                    System.out.println("Winning Bot: " + bot.getId() + " (Player 2), Location: " + bot.getBotLocation());
                    isGameOver = true;
                    winner = this.player2.getName();
                    System.out.println("Player 2's bot returned the flag to their base.");
                    return;
                }
            }
        }
    }

    /**
     * Ends the game and announces the winner.
     */
    private void endGame() {
        if (this.winner != null) {
            System.out.println(this.winner + " wins! The flag has been successfully returned.");
        } else {
            System.out.println("Game Over. No winner this time.");
        }
    }

    /**
     * Creates a new map for the game based on user input.
     * @throws IOException If an input or output exception occurred.
     */
    private void createNewMap() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creating a new map...");
        int quantityLocations = getInputAsInt("Enter the number of locations: ");
        boolean bidirectional = getInputAsBoolean("Should the map be bidirectional? (true/false): ");
        double densityEdges = getInputAsDouble("Enter the density of edges", 0.0, 1.0);

        this.gameMap.generateRandomMap(quantityLocations, bidirectional, densityEdges);

        System.out.println("Do you want to export the map? (yes/no)");
        String exportAnswer = scanner.next().trim().toLowerCase();
        if ("yes".equals(exportAnswer)) {
            System.out.println("Please enter the file name for exporting the map:");
            String fileName = scanner.next();
            this.gameMap.exportMap(fileName);
            System.out.println("Map exported successfully to " + fileName);
        }
    }

    /**
     * Sets up the specified number of bots for each player, allowing them to choose movement strategies.
     */
    public void setBotsForPlayers() {
        int player1Bots = getInputAsInt("Enter the number of bots for " + player1.getName() + ": ");
        int player2Bots = getInputAsInt("Enter the number of bots for " + player2.getName() + ": ");

        for (int i = 0; i < player1Bots; i++) {
            System.out.println("Select a movement strategy for bot " + (i+1) + " of " + player1.getName() + ":");
            IMovementStrategy strategy = selectStrategy();
            createAndAddBot(this.player1, strategy);
        }
        for (int i = 0; i < player2Bots; i++) {
            System.out.println("Select a movement strategy for bot " + (i+1) + " of " + player2.getName() + ":");
            IMovementStrategy strategy = selectStrategy();
            createAndAddBot(this.player2, strategy);
        }
    }

    /**
     * Allows the user to select a movement strategy for a bot.
     * @return The selected movement strategy.
     */
    private IMovementStrategy selectStrategy() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Shortest Path Strategy moving towards enemy flag");
            System.out.println("2. Minimum Spanning Tree Strategy moving towards enemy flag");
            System.out.println("3. Random Movement Strategy");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    return new ShortestPathStrategy();
                case 2:
                    return new MinimumSpanningTreeStrategy();
                case 3:
                    return new RandomMovementStrategy();
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        }
    }

    /**
     * Creates a new bot with the specified movement strategy and adds it to the specified player.
     *
     * @param player   The player to whom the bot belongs.
     * @param strategy The movement strategy for the bot.
     */
    private void createAndAddBot(IPlayer player, IMovementStrategy strategy) {
        IBot bot = new Bot(strategy, player, player.getBase());
        player.addBot(bot);
        bots.enqueue(bot);
    }


    /**
     * Reads an integer input safely from the console with a prompt message.
     * Re-prompts the user until a valid integer is entered.
     *
     * @param message The prompt message to display to the user.
     * @return The integer entered by the user.
     */
    private int getInputAsInt(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            scanner.next(); // Read and discard non-integer input
            System.out.print("Invalid input. Please enter a valid integer: ");
        }
        return scanner.nextInt();
    }

    /**
     * Reads a boolean input safely from the console with a prompt message.
     * Re-prompts the user until a valid boolean ('true' or 'false') is entered.
     *
     * @param message The prompt message to display to the user.
     * @return The boolean value entered by the user.
     */
    private boolean getInputAsBoolean(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        while (!scanner.hasNextBoolean()) {
            scanner.next(); // Read and discard non-boolean input
            System.out.print("Invalid input. Please enter 'true' or 'false': ");
        }
        return scanner.nextBoolean();
    }

    /**
     * Reads a double input safely from the console within a specified range, inclusive of min and max.
     * Re-prompts the user until a valid number within the range is entered.
     *
     * @param message The prompt message to display to the user.
     * @param min The minimum value allowed (inclusive).
     * @param max The maximum value allowed (inclusive).
     * @return The double value entered by the user within the specified range.
     */
    private double getInputAsDouble(String message, double min, double max) {
        Scanner scanner = new Scanner(System.in);
        double input;
        do {
            System.out.print(message + " (value must be > " + min + " and <= " + max + ") : ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid number, using comma instead of period.");
                scanner.next(); // Read and discard non-double input
                System.out.print(message + " (value must be > " + min + " and <= " + max + "): ");
            }
            input = scanner.nextDouble();
            if (input <= min || input > max) {
                System.out.println("Invalid range. The number must be > " + min + " and <= " + max + ". Please try again.");
            }
        } while (input <= min || input > max);
        return input;
    }

    /**
     * Main gameplay loop. Allows the user to play the game and choose to play again after each game ends.
     *
     * @throws IOException If an I/O error occurs during the game.
     */
    public void playGame() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            startGame();
            System.out.println("Do you want to play again? (yes/no)");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (!"yes".equals(answer)) {
                playAgain = false;
                System.out.println("Thank you for playing!");
            } else {
                resetGame();
            }
        }
    }

    /**
     * Resets the game to its initial state, preparing for a new game.
     */
    private void resetGame() {
        this.gameMap = new GameMap();
        this.player1 = new Player("Player 1");
        this.player2 = new Player("Player 2");
        this.bots.clear();
        this.isGameOver = false;
        this.winner = null;
    }
    /**
     * Converts the game map, players, and bots into a string representation.
     *
     * @return A string representation of the game.
     */
    @Override
    public String toString() {
        return this.gameMap.toString();
    }
}