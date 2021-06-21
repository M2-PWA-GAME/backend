package com.ynov.master.mobile.game.medieval.warfare.dto;

import com.ynov.master.mobile.game.medieval.warfare.model.Position;
import com.ynov.master.mobile.game.medieval.warfare.model.Weapon;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
public class PlayerStateResponserDTO {
    @BsonProperty("_id")
    private String id;

    @BsonProperty("position")
    Position position;

    @BsonProperty("health")
    Integer health;

    @BsonProperty("armor")
    Integer armor;

    @BsonProperty("weapon")
    WeaponResponseDTO weapon;
}
