package com.ynov.master.mobile.game.medieval.warfare.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MongoConfigurationProperties.class)
public class MongoConfiguration {

    @Bean
    public MongoClient createMongoClient(MongoConfigurationProperties properties) {
        ConnectionString connString = new ConnectionString(properties.getConnectionString());
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();

        System.out.println("OK");

        return MongoClients.create(settings);
    }

    @Bean
    public MongoDatabase openDatabase(MongoClient client) {
        return client.getDatabase("game");
    }

    @Bean("helloCollection")
    public MongoCollection<Document> openHelloCollection(MongoDatabase db) {
        return db.getCollection("hello");
    }


}
