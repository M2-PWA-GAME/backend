package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
public class PlayerState {
    @BsonProperty("_id")
    private ObjectId id;

    @BsonProperty("position")
    Position position;

    @BsonProperty("health")
    Integer health;

    @BsonProperty("armor")
    Integer armor;

    @BsonProperty("weapon")
    Weapon weapon;
}
