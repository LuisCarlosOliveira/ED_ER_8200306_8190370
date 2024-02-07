package org.example.Game.Classes;

import org.example.Game.Interfaces.*;
//import org.example.Game.MovementStrategy.MinimumSpanningTreeStrategy;
import org.example.Game.MovementStrategy.MinimumSpanningTreeStrategy;
import org.example.Game.MovementStrategy.RandomMovementStrategy;
import org.example.Game.MovementStrategy.ShortestPathStrategy;
import org.example.Structures.Implementations.LinkedQueue;

import java.util.Scanner;

/**
 * Represents the main class for managing and controlling the game.
 */
public class Game {
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
    public void startGame() {
        setupGame();
        while (!isGameOver) {
            processBotTurns();
            checkGameConditions();
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
            this.bots.enqueue(currentBot);
            checkGameConditions();
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

        // Check for opponent bots with flags at the next location
        for (IBot otherBot : bots) {
            if (!otherBot.equals(bot) && otherBot.getBotLocation().equals(nextLocation)) {
                if (otherBot.hasFlag() && !otherBot.getPlayer().equals(bot.getPlayer())) {
                    System.out.println("Bot " + bot.getId() + " (" + bot.getPlayer().getName() + ") encountered an opponent bot with the flag at location " + nextLocation);
                    resetFlagToBase(otherBot.getPlayer().getFlag());
                    otherBot.setHasFlag(false);
                }
            }
        }
        // Check if the bot captured the enemy flag
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

    private boolean isFlagAvailableForCapture(ILocation flagLocation, IPlayer player) {
        for (IBot bot : bots) {
            if (bot.getPlayer().equals(player) && bot.hasFlag() && bot.getBotLocation().equals(flagLocation)) {
                return false;
            }
        }
        return true;
    }

    private void resetFlagToBase(IFlag flag) {
        flag.setCurrentLocation(flag.getOwner().getBase());
        System.out.println("The flag of " + flag.getOwner().getName() + " has been returned to the base.");
    }




    /**
     * Checks the game conditions, including flag captures and determining the winner.
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creating a new map...");
        int quantityLocations = getInputAsInt("Enter the number of locations: ");
        boolean bidirectional = getInputAsBoolean("Should the map be bidirectional? (true/false): ");
        double densityEdges = getInputAsDouble("Enter the density of edges (between 0 and 1): ");

        // Gera o mapa com os parâmetros fornecidos
        this.gameMap.generateRandomMap(quantityLocations, bidirectional, densityEdges);

        // Pergunta ao usuário se ele deseja exportar o mapa
        System.out.println("Do you want to export the map? (yes/no)");
        String exportAnswer = scanner.next().trim().toLowerCase();

        // Se a resposta for 'yes', pede o nome do arquivo e exporta o mapa
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
     *
     * @return The selected movement strategy.
     */
    private IMovementStrategy selectStrategy() {
        ILocation player1Flag = gameMap.getPlayerOneFlagLocation();
        ILocation player2Flag = gameMap.getPlayerTwoFlagLocation();
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
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        while (!scanner.hasNextDouble()) {
            scanner.next(); // Read and discard non-double input
            System.out.print("Invalid input. Please enter a valid number: ");
        }
        return scanner.nextDouble();
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            startGame();
            System.out.println("Do you want to play again? (yes/no)");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (!answer.equals("yes")) {
                playAgain = false;
                System.out.println("Thank you for playing!");
            } else {
                resetGame();
            }
        }
    }

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