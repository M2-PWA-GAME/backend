package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;

import java.util.List;

@Data
public class Map {

    private int seed;

    private int xMax;

    private int yMax;

    List<Tile> tiles;


    public Tile findTile(Integer posX, Integer posY) {
        return tiles.stream()
                .filter(tile -> tile.getPosition().getY() == posY && tile.getPosition().getX() == posX)
                .findFirst()
                .orElse(null);
    }

}
