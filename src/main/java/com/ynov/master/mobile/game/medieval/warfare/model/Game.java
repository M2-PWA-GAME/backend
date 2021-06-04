package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class Game {

    @BsonProperty("_id")
    private ObjectId id;

    @BsonProperty("status")
    GameStatus status;

    @BsonProperty("name")
    String name;

    @BsonProperty("maxPlayers")
    Integer maxPlayers;

    @BsonProperty("users")
    List<String> users;

    @BsonProperty("turns")
    List<Turn> turns;

    @BsonProperty("weapons")
    List<Weapon> weapons;

    @BsonProperty("map")
    Map map;

    public Game addUser(User user) {
        this.users.add(user.getId().toString());
        return this;
    }


}
