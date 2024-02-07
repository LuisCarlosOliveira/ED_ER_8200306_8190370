package org.example.Game.Entities;

import org.junit.jupiter.api.Test;

import org.example.Game.Entities.Interfaces.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlagTest {

    @Test
    void testFlagInitialization() {
        IPlayer player = new Player("Player 1");
        ILocation location = new Location(0, 0);
        Flag flag = new Flag(player, location);

        assertEquals(player, flag.getOwner());
        assertEquals(location, flag.getCurrentLocation());
    }

    @Test
    void testSetCurrentLocation() {
        IPlayer player = new Player("Player 1");
        ILocation initialLocation = new Location(0, 0);
        ILocation newLocation = new Location(1, 1);
        Flag flag = new Flag(player, initialLocation);

        flag.setCurrentLocation(newLocation);

        assertEquals(newLocation, flag.getCurrentLocation());
    }

}
