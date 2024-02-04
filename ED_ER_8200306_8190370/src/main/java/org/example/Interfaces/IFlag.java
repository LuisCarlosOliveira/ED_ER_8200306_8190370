package org.example.Interfaces;

import org.example.Location;
import org.example.Player;

public interface IFlag {
    Player getOwner();

    Location getCurrentLocation();

    void setCurrentLocation(Location location);

    boolean isCaptured();

    void setCaptured(boolean captured);

    String toString();

}
