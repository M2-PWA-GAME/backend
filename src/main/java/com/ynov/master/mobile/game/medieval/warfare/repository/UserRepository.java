package com.ynov.master.mobile.game.medieval.warfare.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserRepository {

    @Autowired
    private MongoCollection<User> collection;

    public User findByUsername(String username) {
        log.debug("Looking for user: " + username);
        return collection.find(Filters.eq("username", username)).first();
    }

    public User findById(String id) {
        log.debug("Looking for user by id : " + id);
        return collection.find(Filters.eq("_id", new ObjectId(id))).first();
    }

    public boolean existsByUsername(String username) {
        return collection.find(Filters.eq("username", username)).first() != null;
    }

    public void save(User user) {
        collection.insertOne(user);
    }

    public void deleteByUsername(String username) {
        collection.deleteOne(Filters.eq("username", username));
    }

    public void update(User user) throws RuntimeException {

        if (user.getId() == null) {
            throw new RuntimeException("Cannot update a user without an _id");
        }

        collection.findOneAndReplace(Filters.eq("_id", user.getId()), user);
    }
}
