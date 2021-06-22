package com.ynov.master.mobile.game.medieval.warfare.dto;

import com.ynov.master.mobile.game.medieval.warfare.model.ActionType;
import com.ynov.master.mobile.game.medieval.warfare.model.Position;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class ActionResponseDTO {
    private String id;

    ActionType actionType;

    Position from;

    Position to;
}
