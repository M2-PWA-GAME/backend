package com.ynov.master.mobile.game.medieval.warfare.dto;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
public class WeaponResponseDTO {
    @BsonProperty("_id")
    private String id;

    @BsonProperty("name")
    String name;

    @BsonProperty("damages")
    Integer damages;

    @BsonProperty("range")
    Integer range;
}
