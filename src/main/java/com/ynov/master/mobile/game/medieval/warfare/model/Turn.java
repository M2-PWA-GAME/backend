package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;


@Data
public class Turn {
    @BsonProperty("actions")
    List<Action> actions;

    @BsonProperty("players_states")
    List<PlayerState> playersStates;

    @BsonProperty("player_id")
    private ObjectId playerId;

}
