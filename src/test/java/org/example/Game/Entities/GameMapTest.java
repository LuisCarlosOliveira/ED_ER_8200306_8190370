package org.example.Game.Entities;

import org.example.Game.Entities.Interfaces.IFlag;
import org.example.Game.Entities.Interfaces.ILocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameMapTest {

    private GameMap gameMap;

    @BeforeEach
    void setUp() {
        gameMap = new GameMap();
    }

    @Test
    void testGenerateRandomMap() {
        gameMap.generateRandomMap(10, true, 0.5);
        assertNotNull(gameMap.getLocations(), "Map should not be null after generation");
        assertEquals(10, gameMap.getLocations().size(), "Map should contain the specified number of locations");
    }

    @Test
    void testSetAndGetFlags() {
        IFlag flag1 = new Flag(null, null);
        IFlag flag2 = new Flag(null, null);
        ILocation location1 = new Location(1, 1);
        ILocation location2 = new Location(2, 2);

        GameMap gameMap = new GameMap();

        gameMap.setFlags(flag1, flag2);

        flag1.setCurrentLocation(location1);
        flag2.setCurrentLocation(location2);

        assertEquals(location1, gameMap.getPlayerOneFlagLocation(), "Player one flag location should match the defined location");
        assertEquals(location2, gameMap.getPlayerTwoFlagLocation(), "Player two flag location should match the defined location");
    }
}
