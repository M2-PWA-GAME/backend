package com.ynov.master.mobile.game.medieval.warfare.repository;

import com.mongodb.client.MongoCollection;
import com.ynov.master.mobile.game.medieval.warfare.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GameRepository {

    @Autowired
    private MongoCollection<Game> collection;


    public void save(Game game) {
        collection.insertOne(game);
    }

}
