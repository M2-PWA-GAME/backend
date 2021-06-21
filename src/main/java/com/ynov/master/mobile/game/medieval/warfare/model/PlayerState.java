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

    public static PlayerState initialState(String playerId)
    {
        PlayerState state = new PlayerState();
        state.setId(new ObjectId(playerId));
        state.setHealth(20);
        state.setArmor(0);
        state.setWeapon(new Weapon("KNIFE",1,1));

        return state;
    }

    public PlayerState() {
    }
}
