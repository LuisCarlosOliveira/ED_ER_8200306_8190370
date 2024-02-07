package org.example.Game.Entities;

import org.example.Game.Entities.Interfaces.*;
import org.example.Game.MovementStrategy.Interfaces.*;
import org.example.Game.MovementStrategy.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class BotTest {

    @Test
    void testGettersAndSetters() {
        IMovementStrategy strategy = new ShortestPathStrategy();
        IPlayer player = new Player("Player 1");
        ILocation location = new Location(0, 0);
        Bot bot = new Bot(strategy, player, location);

        assertEquals(strategy, bot.getStrategy());
        assertEquals(player, bot.getPlayer());
        assertEquals(location, bot.getBotLocation());

        IMovementStrategy newStrategy = new ShortestPathStrategy();
        ILocation newLocation = new Location(1, 1);

        bot.setStrategy(newStrategy);
        bot.setBotLocation(newLocation);

        assertEquals(newStrategy, bot.getStrategy());
        assertEquals(newLocation, bot.getBotLocation());
    }


    @Test
    void testFlagMethods() {
        Bot bot = new Bot(null, null, null);
        assertFalse(bot.hasFlag());

        bot.setHasFlag(true);
        assertTrue(bot.hasFlag());
    }

    @Test
    void testToString() {
        IMovementStrategy strategy = new ShortestPathStrategy();
        IPlayer player = new Player("Player 1");
        ILocation location = new Location(1, 1);
        Bot bot = new Bot(strategy, player, location);

        assertEquals("Bot{id=" + bot.getId() + ", strategy=" + strategy + ", playerName='" + player.getName() + "', botLocation=" + location + "}", bot.toString());
    }
}
