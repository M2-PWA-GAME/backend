package com.ynov.master.mobile.game.medieval.warfare.dto;

import com.ynov.master.mobile.game.medieval.warfare.model.ActionType;
import com.ynov.master.mobile.game.medieval.warfare.model.Position;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
public class ActionResponseDTO {

    ActionType actionType;

    Position from;

    Position to;
}
