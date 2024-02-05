package org.example.MovementStrategy;

import org.example.Bot;
import org.example.GameMap;
import org.example.Interfaces.IMovementStrategy;
import org.example.Location;
import org.example.Structures.Implementations.ArrayUnorderedList;
import org.example.Structures.Implementations.Network;

import java.util.Iterator;
import java.util.Random;

public class RandomMovementStrategy extends MovementStrategy {

    private final Random random;

    public RandomMovementStrategy() {
        this.random = new Random();
    }

    @Override
    public Location nextMove(Bot bot, GameMap gameMap) {
        Location currentLocation = bot.getBotLocation();
        Location baseLocation = bot.getPlayer().getBase();
        Location targetLocation;
        Network<Location> network = gameMap.getLocations();

        // Coleta as localizações acessíveis a partir da localização atual usando BFS
        ArrayUnorderedList<Location> accessibleLocations = new ArrayUnorderedList<>();
        Iterator<Location> bfsIterator = network.iteratorBFS(currentLocation);

        // Pula a localização atual do bot
        if (bfsIterator.hasNext()) {
            bfsIterator.next();  // Assume-se que o primeiro elemento é a localização atual
        }

        // Coleta um número limitado de localizações acessíveis
        while (bfsIterator.hasNext() && accessibleLocations.size() < 5) {
            Location next = bfsIterator.next();
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
