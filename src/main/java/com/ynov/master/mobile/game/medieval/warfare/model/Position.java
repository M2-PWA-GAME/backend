package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
public class Position {
  @BsonProperty("x")
  Integer X;

  @BsonProperty("y")
  Integer Y;
}
