package com.ynov.master.mobile.game.medieval.warfare.model;

import com.ynov.master.mobile.game.medieval.warfare.exception.CustomException;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;

import java.util.List;


@Data
public class Turn {
    @BsonProperty("actions")
    List<Action> actions;

    @BsonProperty("players_states")
    List<PlayerState> playersStates;

    @BsonProperty("player_id")
    private ObjectId playerId;



    public void addAction(Action action) throws Exception {
        if(getActions().size() == 2) {
            throw new Exception("You cannot play more than twice");
        }
        this.actions.add(action);
    }

}
