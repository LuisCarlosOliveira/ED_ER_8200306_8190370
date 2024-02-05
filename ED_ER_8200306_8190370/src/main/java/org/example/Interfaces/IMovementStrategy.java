package org.example.Interfaces;

import org.example.Bot;
import org.example.GameMap;
import org.example.Location;

public interface IMovementStrategy {
    public Location nextMove(Bot bot, GameMap gameMap);
}