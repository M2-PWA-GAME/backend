package com.ynov.master.mobile.game.medieval.warfare.web;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloworldController {

    private final MongoCollection<Document> collection;

    public HelloworldController(@Qualifier("helloCollection") MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> helloWorld() {
        Document helloDoc = new Document("_id", new ObjectId());
        helloDoc.append("msg", "hello world");
        collection.insertOne(helloDoc);
        return ResponseEntity.status(HttpStatus.OK).body("Hello world [SAVED]");
    }

}
