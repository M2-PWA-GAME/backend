package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
public class Weapon {
    @BsonProperty("_id")
    private ObjectId id;

    @BsonProperty("name")
    String name;

    @BsonProperty("damages")
    Integer damages;

    @BsonProperty("range")
    Integer range;

    public Weapon(String name, int damages, int range) {
        this.setId(new ObjectId());
        this.setName(name);
        this.setDamages(damages);
        this.setRange(range);
    }

    public Weapon() {
    }
}
