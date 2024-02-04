package org.example.MovementStrategy;

import org.example.Bot;
import org.example.GameMap;
import org.example.Interfaces.IMovementStrategy;
import org.example.Location;

public abstract class MovementStrategy implements IMovementStrategy {
    @Override
    public abstract Location nextMove(Bot bot, GameMap gameMap);
}

