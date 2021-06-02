package com.ynov.master.mobile.game.medieval.warfare.dto;


import com.ynov.master.mobile.game.medieval.warfare.model.ActionType;
import com.ynov.master.mobile.game.medieval.warfare.model.Position;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
public class ActionDTO {

    @BsonProperty("action_type")
    ActionType actionType;

    @BsonProperty("from")
    Position from;

    @BsonProperty("to")
    Position to;
}
