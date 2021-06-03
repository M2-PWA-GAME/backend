package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    @BsonProperty("_id")
    private ObjectId id;

    @BsonProperty("username")
    private String username;

    @BsonProperty("email")
    private String email;

    @BsonProperty("password")
    private String password;

    @BsonProperty("roles")
    List<Role> roles;

    @BsonProperty("games")
    List<ObjectId> games;


    public void addGame(Game game) {
        if(this.games == null) this.games = new ArrayList<>();
        this.games.add(game.getId());
    }

}