package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Tile {

  Position position;
  TileType type;
  Boolean isNavigable;

  public Tile(int x, int y) {
    this.position = new Position(x,y);
  }

  public void setType(TileType type) {
    List<TileType> notNavigable = List.of(TileType.ROCK,TileType.WATER);
    this.isNavigable = !notNavigable.contains(type);
    this.type = type;
  }

}
