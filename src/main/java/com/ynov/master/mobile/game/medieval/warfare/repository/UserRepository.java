package com.ynov.master.mobile.game.medieval.warfare.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRepository {

    @Autowired
    private MongoCollection<User> collection;

    public User findByUsername(String username) {
        log.debug("Looking for user: " + username);
        User usr = collection.find(Filters.eq("username", username)).first();
        if (usr != null) {
            log.debug("Find user : " + usr.getId());
        } else {
            log.debug("No user found.");
        }
        return usr;
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

    public User update(User user) throws Exception {
        if (user.getId() == null) {
            throw new Exception("Cannot update a user without an _id");
        }
        FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
        return collection.findOneAndReplace(Filters.eq("_id", user.getId()), user, returnDocAfterReplace);
    }
}
