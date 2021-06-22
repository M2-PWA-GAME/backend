package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;


@Data
public class Turn {
    @BsonProperty("actions")
    List<Action> actions = new ArrayList<>();

    @BsonProperty("players_states")
    List<PlayerState> playersStates;

    @BsonProperty("player_id")
    private ObjectId playerId;


    public void addAction(Action action) throws Exception {
        if (actions == null)
            this.setActions(new ArrayList<>());

        if (getActions().size() == 2) {
            throw new Exception("You cannot play more than twice");
        }

        this.actions.add(action);
    }

}
