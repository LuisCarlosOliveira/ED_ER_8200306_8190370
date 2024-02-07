package org.example.Game.Entities;

import org.example.Game.Entities.Interfaces.IBot;
import org.example.Game.Entities.Interfaces.IFlag;
import org.example.Game.Entities.Interfaces.IGameMap;
import org.example.Game.Entities.Interfaces.ILocation;
import org.example.Game.MovementStrategy.RandomMovementStrategy;
import org.example.Structures.Implementations.ArrayUnorderedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlayerTest {

    private Player player;
    private IFlag flagBase;
    private ILocation location;
    private IGameMap gameMap;

    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer");
        flagBase = new Flag(player, new Location(0, 0));
        location = new Location(1, 1);
        gameMap = new GameMap();
    }

    @Test
    void testPlayerInitialization() {
        assertNotNull(player);
        assertEquals("TestPlayer", player.getName());
        assertEquals(0, player.getBots().size());
        assertEquals(null, player.getFlag());
    }

    @Test
    void testSetFlagBase() {
        player.setFlagBase(flagBase);
        assertEquals(flagBase, player.getFlag());
    }

    @Test
    void testGetBase() {
        player.setFlagBase(flagBase);
        assertEquals(flagBase.getCurrentLocation(), player.getBase());
    }

    @Test
    void testSelectBase() {
        ArrayUnorderedList<ILocation> locations = new ArrayUnorderedList<>();
        locations.addToRear(location);
        GameMap gameMap = new GameMap();
        gameMap.getLocations().addVertex(location);

        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(in);

        try {
            player.selectBase(gameMap);
        } finally {
            System.setIn(sysInBackup);
        }

        assertEquals(location, player.getBase());
    }

    @Test
    void testAddBot() {
        IBot bot = new Bot(new RandomMovementStrategy(), player, location);
        player.addBot(bot);
        assertEquals(1, player.getBots().size());
        assertEquals(bot, player.getBots().first());
    }
}
