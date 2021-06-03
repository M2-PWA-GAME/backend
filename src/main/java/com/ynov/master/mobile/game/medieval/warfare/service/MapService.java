package com.ynov.master.mobile.game.medieval.warfare.service;

import com.ynov.master.mobile.game.medieval.warfare.model.Map;
import com.ynov.master.mobile.game.medieval.warfare.model.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapService {

    public Map generateRandomMap(int xMax, int yMax) {

        Map map = new Map();
        map.setXMax(xMax);
        map.setYMax(yMax);

        List<Tile> tiles = new ArrayList<>();
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                Tile nTile = new Tile(x, y);
                tiles.add(nTile);
            }
        }

        int obstacleCount = xMax * yMax / 10;
        Collections.shuffle(tiles);

        for(int i = 0; i < obstacleCount; i++) {

        }



        return map;
    }

}
