package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;
@Data
public class Turn {
  @BsonProperty("_id")
  private ObjectId id;

  @BsonProperty("actions")
  List<Action> actions;

  @BsonProperty("player_states")
  List<PlayerState> playerStates;
}
