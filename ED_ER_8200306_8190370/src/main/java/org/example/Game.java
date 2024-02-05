package org.example;

import org.example.MovementStrategy.MinimumSpanningTreeStrategy;
import org.example.MovementStrategy.MovementStrategy;
import org.example.MovementStrategy.RandomMovementStrategy;
import org.example.MovementStrategy.ShortestPathStrategy;
import org.example.Structures.Implementations.LinkedQueue;

import java.io.File;
import java.util.Scanner;

public class Game {
    private GameMap gameMap;
    private Player player1;
    private Player player2;
    private LinkedQueue<Bot> bots;
    private boolean isGameOver;

    private String winner;

    public Game() {
        this.gameMap = new GameMap();
        this.player1 = new Player("Player 1");
        this.player2 = new Player("Player 2");
        this.bots = new LinkedQueue<>();
        this.isGameOver = false;
    }

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

        System.out.println("\n" + player1);
        System.out.println(player2);
        System.out.println(gameMap);

        setBotsForPlayers();
    }

    private void processBotTurns() {
        int botsSize = bots.size();
        for (int i = 0; i < botsSize; i++) {
            Bot currentBot = bots.dequeue();
            executeBotTurn(currentBot);
            // Re-queue the bot after its turn
            this.bots.enqueue(currentBot);
        }
    }

    private void executeBotTurn(Bot bot) {
        Location nextLocation = bot.getStrategy().nextMove(bot, gameMap);
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


    private void checkGameConditions() {
        for (Bot bot : this.player1.getBots()) {
            if (bot.hasFlag() && bot.getBotLocation().equals(player2.getBase())) {
                this.isGameOver = true;
                this.winner = this.player1.getName();
                break;
            }
        }

        for (Bot bot : player2.getBots()) {
            if (bot.hasFlag() && bot.getBotLocation().equals(player1.getBase())) {
                this.isGameOver = true;
                this.winner = this.player2.getName();
                break;
            }
        }
    }


    private void endGame() {
        if (this.winner != null) {
            System.out.println(this.winner + " wins! The flag has been successfully returned.");
        } else {
            System.out.println("Game Over. No winner this time.");
        }
    }

    private void createNewMap() {
        System.out.println("Creating a new map...");
        int quantityLocations = getInputAsInt("Enter the number of locations: ");
        boolean bidirectional = getInputAsBoolean("Should the map be bidirectional? (true/false): ");
        double densityEdges = getInputAsDouble("Enter the density of edges (between 0 and 1): ");
        this.gameMap.generateRandomMap(quantityLocations, bidirectional, densityEdges);
    }

    public void setBotsForPlayers() {
        int player1Bots = getInputAsInt("Enter the number of bots for " + player1.getName() + ": ");
        int player2Bots = getInputAsInt("Enter the number of bots for " + player2.getName() + ": ");

        for (int i = 0; i < player1Bots; i++) {
            MovementStrategy strategy = selectStrategy();
            createAndAddBot(this.player1, strategy);
        }
        for (int i = 0; i < player2Bots; i++) {
            MovementStrategy strategy = selectStrategy();
            createAndAddBot(this.player2, strategy);
        }
    }

    private MovementStrategy selectStrategy() {
        Location player1Flag = gameMap.getPlayerOneFlagLocation();
        Location player2Flag = gameMap.getPlayerTwoFlagLocation();
        Scanner scanner = new Scanner(System.in);
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

    private void createAndAddBot(Player player, MovementStrategy strategy) {
        Bot bot = new Bot(strategy, player, player.getBase());
        player.addBot(bot);
        bots.enqueue(bot);
    }

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
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        while (!scanner.hasNextDouble()) {
            scanner.next(); // Read and discard non-double input
            System.out.print("Invalid input. Please enter a valid number: ");
        }
        return scanner.nextDouble();
    }

    @Override
    public String toString() {
        return this.gameMap.toString();
    }
}