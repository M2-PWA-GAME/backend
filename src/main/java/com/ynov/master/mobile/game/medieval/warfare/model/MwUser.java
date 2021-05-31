package com.ynov.master.mobile.game.medieval.warfare.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MwUser extends User {

    @Getter
    @Setter
    private ObjectId id;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String email;

    public MwUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Document toDocument() {
        return new Document("_id",this.getId())
            .append("username",username)
            .append("password",password)
            .append("email",email);
    }

}
