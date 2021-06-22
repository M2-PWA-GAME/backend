package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
public class Action {

    @BsonProperty("action_type")
    ActionType actionType;

    @BsonProperty("from")
    Position from;

    @BsonProperty("to")
    Position to;
}
