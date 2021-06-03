package com.ynov.master.mobile.game.medieval.warfare.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.model.ReturnDocument;
import com.ynov.master.mobile.game.medieval.warfare.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GameRepository {

    @Autowired
    private MongoCollection<Game> collection;


    public Game findGameById(String gameId) throws Exception {
        return collection.find(Filters.eq("_id", new ObjectId(gameId))).first();
    }


    public void save(Game game) {
        collection.insertOne(game, new InsertOneOptions());
    }

    public Game update(Game game) throws Exception {
        if (game.getId() == null) {
            throw new Exception("Cannot update a game without an _id");
        }
        FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
        return collection.findOneAndReplace(Filters.eq("_id", game.getId()), game, returnDocAfterReplace);
    }
}
