package org.example.Game.Entities;

import org.example.Game.Entities.Interfaces.ILocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LocationTest {

    @Test
    void testLocationInitialization() {
        int x = 5;
        int y = 10;
        Location location = new Location(x, y);

        assertEquals(x, location.getCoordinateX());
        assertEquals(y, location.getCoordinateY());
    }

    @Test
    void testEquals() {
        Location location1 = new Location(1, 1);
        Location location2 = new Location(1, 1);
        Location location3 = new Location(2, 2);

        assertEquals(location1, location2);
        assertNotEquals(location1, location3);
    }

    @Test
    void testHashCode() {
        Location location1 = new Location(1, 1);
        Location location2 = new Location(1, 1);
        Location location3 = new Location(2, 2);

        assertEquals(location1.hashCode(), location2.hashCode());
        assertNotEquals(location1.hashCode(), location3.hashCode());
    }

    @Test
    void testCompareTo() {
        Location location1 = new Location(1, 1);
        Location location2 = new Location(2, 2);
        Location location3 = new Location(1, 2);

        assertEquals(0, location1.compareTo(location1));

        assertEquals(-1, location1.compareTo(location2));
        assertEquals(1, location2.compareTo(location1));

        assertEquals(-1, location1.compareTo(location3));
        assertEquals(1, location3.compareTo(location1));
    }
}
