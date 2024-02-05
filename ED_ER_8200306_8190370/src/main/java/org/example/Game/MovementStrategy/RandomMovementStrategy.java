package org.example.Game.MovementStrategy;

import org.example.Game.Interfaces.IBot;
import org.example.Game.Interfaces.IGameMap;
import org.example.Game.Interfaces.ILocation;
import org.example.Structures.Implementations.ArrayUnorderedList;
import org.example.Structures.Implementations.Network;

import java.util.Iterator;
import java.util.Random;

/**
 * Movement strategy that selects the next bot move randomly from accessible locations on the game map.
 */
public class RandomMovementStrategy extends MovementStrategy {
    private final Random random;

    /**
     * Constructor for the RandomMovementStrategy class.
     */
    public RandomMovementStrategy() {
        this.random = new Random();
    }

    /**
     * Determines the bot's next move based on a random selection from accessible locations on the game map.
     *
     * @param bot      The bot that is moving.
     * @param gameMap  The current game map, used to determine accessible locations.
     * @return The next location the bot should move to.
     */
    @Override
    public ILocation nextMove(IBot bot, IGameMap gameMap) {
        ILocation currentLocation = bot.getBotLocation();
        ILocation baseLocation = bot.getPlayer().getBase();
        ILocation targetLocation;
        Network<ILocation> network = gameMap.getLocations();

        // Coleta as localizações acessíveis a partir da localização atual usando BFS
        ArrayUnorderedList<ILocation> accessibleLocations = new ArrayUnorderedList<>();
        Iterator<ILocation> bfsIterator = network.iteratorBFS(currentLocation);

        // Pula a localização atual do bot
        if (bfsIterator.hasNext()) {
            bfsIterator.next();  // Assume-se que o primeiro elemento é a localização atual
        }

        // Coleta um número limitado de localizações acessíveis
        while (bfsIterator.hasNext() && accessibleLocations.size() < 5) {
            ILocation next = bfsIterator.next();
            accessibleLocations.addToRear(next);
        }

        // Determine a target location com base na posse de bandeira do bot
        if (bot.hasFlag()) {
            if (baseLocation.equals(gameMap.getPlayerOneFlagLocation())) {
                targetLocation = baseLocation;
            } else {
                targetLocation = gameMap.getPlayerTwoFlagLocation();
            }
        } else {
            if (baseLocation.equals(gameMap.getPlayerTwoFlagLocation())) {
                targetLocation = baseLocation;
            } else {
                targetLocation = gameMap.getPlayerOneFlagLocation();
            }
        }

        // Verifica se a localização atual é a mesma que a localização da bandeira inimiga
        if (currentLocation.equals(targetLocation)) {
            return currentLocation; // Permanece na localização atual
        }

        // Escolhe uma localização aleatória das localizações acessíveis coletadas
        if (!accessibleLocations.isEmpty()) {
            int randomIndex = random.nextInt(accessibleLocations.size());
            return accessibleLocations.get(randomIndex);
        }

        // Se nenhuma localização acessível for encontrada, o bot permanece na localização atual
        return currentLocation;
    }
}
