package org.example.Game.Classes;

import org.example.Game.Interfaces.*;
import org.example.Game.MovementStrategy.MinimumSpanningTreeStrategy;
import org.example.Game.MovementStrategy.RandomMovementStrategy;
import org.example.Game.MovementStrategy.ShortestPathStrategy;
import org.example.Structures.Implementations.LinkedQueue;

import java.util.Locale;
import java.util.Scanner;

/**
 * Represents the main class for managing and controlling the game.
 */
public class Game {
    private GameMap gameMap;
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
    public void startGame() {
        setupGame();
        while (!isGameOver) { // Use the boolean variable to control the game loop
            processBotTurns();
            checkGameConditions(); // Method to check and update game conditions, including isGameOver
        }
        endGame();
    }

    /*
    MISSSING :
    LEITURA DE FICHEIRO
     */
    /**
     * Sets up the initial state of the game, including creating a new map or importing an existing one.
     * Also, allows players to select bases and initializes bots for both players.
     */
    private void setupGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to create a new map (1) or import an existing one (2)?");
        int choice = scanner.nextInt();

        if (choice == 1) {
            createNewMap();
        } else if (choice == 2) {
            System.out.println("Please enter the file name to import the map:");
            String fileName = scanner.next();
            this.gameMap.importMap(fileName);
        } else {
            System.out.println("Invalid option. Exiting the game.");
            return;
        }

        player1.selectBase(gameMap, player2);
        player2.selectBase(gameMap, player1);
        IFlag player1Flag = new Flag(player1, player1.getBase());
        IFlag player2Flag = new Flag(player2, player2.getBase());
        this.gameMap.setFlags(player1Flag, player2Flag);

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
            IBot currentBot = bots.dequeue();
            executeBotTurn(currentBot);
            // Re-queue the bot after its turn
            this.bots.enqueue(currentBot);
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

        // Verifica se o bot capturou a flag inimiga
        if (nextLocation.equals(gameMap.getPlayerOneFlagLocation()) && !player1.equals(bot.getPlayer().getName() )) {
            bot.setHasFlag(true);
            System.out.println("Bot " + bot.getId() + " (" + bot.getPlayer().getName() + ") captured Player 1's flag!");
        } else if (nextLocation.equals(gameMap.getPlayerTwoFlagLocation()) && !player2.equals(bot.getPlayer())) {
            bot.setHasFlag(true);
            System.out.println("Bot " + bot.getId() + " (" + bot.getPlayer().getName()  + ") captured Player 2's flag!");
        }

        System.out.println("Bot " + bot.getId() + " (" + bot.getPlayer().getName()  + ") moved to " + nextLocation);
    }

    /**
     * Checks the game conditions, including flag captures and determining the winner.
     */
    private void checkGameConditions() {
        for (IBot bot : this.player1.getBots()) {
            if (bot.hasFlag() && bot.getBotLocation().equals(player2.getBase())) {
                this.isGameOver = true;
                this.winner = this.player1.getName();
                break;
            }
        }

        for (IBot bot : player2.getBots()) {
            if (bot.hasFlag() && bot.getBotLocation().equals(player1.getBase())) {
                this.isGameOver = true;
                this.winner = this.player2.getName();
                break;
            }
        }
    }

    /**
     * Ends the game and displays the winner or declares a tie.
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
     */
    private void createNewMap() {
        System.out.println("Creating a new map...");
        int quantityLocations = getInputAsInt("Enter the number of locations: ");
        boolean bidirectional = getInputAsBoolean("Should the map be bidirectional? (true/false): ");
        double densityEdges = getInputAsDouble("Enter the density of edges (between 0 and 1): ");
        this.gameMap.generateRandomMap(quantityLocations, bidirectional, densityEdges);
    }


    /**
     * Sets up the specified number of bots for each player, allowing them to choose movement strategies.
     */
    public void setBotsForPlayers() {
        int player1Bots = getInputAsInt("Enter the number of bots for " + player1.getName() + ": ");
        int player2Bots = getInputAsInt("Enter the number of bots for " + player2.getName() + ": ");

        for (int i = 0; i < player1Bots; i++) {
            IMovementStrategy strategy = selectStrategy(player1);
            createAndAddBot(this.player1, strategy);
        }
        for (int i = 0; i < player2Bots; i++) {
            IMovementStrategy strategy = selectStrategy(player2);
            createAndAddBot(this.player2, strategy);
        }
    }

    /**
     * Allows the user to select a movement strategy for a bot.
     *
     * @return The selected movement strategy.
     */
    private IMovementStrategy selectStrategy(IPlayer player) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" + player.getName() + " chose your bot strategy: ");
        System.out.println("1. Shortest Path Strategy moving towards enemy flag");
        System.out.println("2. Minimum Spanning Tree Strategy moving towards enemy flag");
        System.out.println("3. Random Movement Strategy");
        // Add more strategies as needed
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return new ShortestPathStrategy(); // Alvo será definido fora deste método
            case 2:
                return new MinimumSpanningTreeStrategy(); // Alvo será definido fora deste método
            case 3:
                return new RandomMovementStrategy();
            default:
                System.out.println("Invalid choice, defaulting to Random Movement Strategy");
                return new RandomMovementStrategy();
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


    // Helper method to safely read an integer input
    private int getInputAsInt(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            scanner.next(); // Read and discard non-integer input
            System.out.print("Invalid input. Please enter a valid integer: ");
        }
        return scanner.nextInt();
    }

    // Helper method to safely read a boolean input
    private boolean getInputAsBoolean(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        while (!scanner.hasNextBoolean()) {
            scanner.next(); // Read and discard non-boolean input
            System.out.print("Invalid input. Please enter 'true' or 'false': ");
        }
        return scanner.nextBoolean();
    }

    // Helper method to safely read a double input
    private double getInputAsDouble(String message) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        System.out.print(message);
        while (!scanner.hasNextDouble()) {
            scanner.next(); // Lê e descarta a entrada não válida
            System.out.print("Invalid input. Please enter a valid number: ");
        }
        return scanner.nextDouble();
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