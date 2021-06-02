package com.ynov.master.mobile.game.medieval.warfare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

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

}