package com.ynov.master.mobile.game.medieval.warfare.service;

import com.ynov.master.mobile.game.medieval.warfare.model.Map;
import com.ynov.master.mobile.game.medieval.warfare.model.Tile;
import com.ynov.master.mobile.game.medieval.warfare.model.TileType;
import com.ynov.master.mobile.game.medieval.warfare.util.PerlinNoise;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MapService {

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static final  double WATER_LIMIT = -200; // WATER IF NOISE < WATER_LIMIT
    public static final  double GRASS_LIMIT = 200; // GRAAS IF  WATER_LIMIT < NOISE < GRASS_LIMIT

    public Map generateMap(int xMax, int yMax, int seed)
    {

        PerlinNoise noiseHandler = new PerlinNoise();
        noiseHandler.setFrequency(0.3);
        noiseHandler.setAmplitude(800);
        noiseHandler.setOctaves(20);
        noiseHandler.setPersistence(0.3);
        noiseHandler.setSeed(seed);


        Map map = new Map();
        map.setXMax(xMax);
        map.setYMax(yMax);

        System.out.println("===== MAP =====");

        List<Tile> tiles = new ArrayList<>();
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {

                Tile nTile = new Tile(x, y);
                double noise = noiseHandler.getNoise(x,y);

                if(noise < WATER_LIMIT) {
                    nTile.setType(TileType.WATER);
                }
                else if(noise > WATER_LIMIT  && noise < GRASS_LIMIT) {
                    nTile.setType(TileType.GRASS);
                }
                else {
                    nTile.setType(TileType.ROCK);
                }
                tiles.add(nTile);

//                if(nTile.getType().equals(TileType.WATER)) {
//                    System.out.print(ANSI_BLUE + "■  " +ANSI_RESET );
//                }
//                if(nTile.getType().equals(TileType.GRASS)) {
//                    System.out.print(ANSI_GREEN + "■  " +ANSI_RESET );
//                }
//                if(nTile.getType().equals(TileType.ROCK)) {
//                    System.out.print(ANSI_RED + "■  " +ANSI_RESET );
//                }
//
//
//                if(x == xMax - 1)
//                {
//                    System.out.println();
//                }

            }
        }

        map.setTiles(tiles);
        return map;
    }


    public Map generateRandomMap(int xMax, int yMax) {
        int seed = (int) ((Math.random() * (30000 - 9000)) + 9000);
        return generateMap(xMax,yMax,seed);
    }

    public Map generateMapFromSeed(int xMax, int yMax, int seed) {
        return generateMap(xMax,yMax,seed);
    }
}
