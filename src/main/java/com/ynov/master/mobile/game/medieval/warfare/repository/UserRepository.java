package com.ynov.master.mobile.game.medieval.warfare.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {

    @Autowired
    private MongoCollection<User> collection;

    public User findByUsername(String username) {
        return collection.find(Filters.eq("username", username)).first();
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
}
