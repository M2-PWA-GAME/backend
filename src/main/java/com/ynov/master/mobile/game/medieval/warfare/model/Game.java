package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class Game {

  @BsonProperty("_id")
  private ObjectId id;

  @BsonProperty("users")
  List<User> users;

  @BsonProperty("_id")
  List<Turn> turns;

  @BsonProperty("_id")
  List<Weapon> weapons;
}
