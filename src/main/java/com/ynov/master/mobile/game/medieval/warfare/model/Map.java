package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;

import java.util.List;

@Data
public class Map {

  private int xMax;

  private int yMax;

  List<Tile> tiles;
}
