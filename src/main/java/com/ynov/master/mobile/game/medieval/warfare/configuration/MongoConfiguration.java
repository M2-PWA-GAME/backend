package com.ynov.master.mobile.game.medieval.warfare.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.ynov.master.mobile.game.medieval.warfare.model.Game;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MongoConfigurationProperties.class)
public class MongoConfiguration {

    @Bean
    public MongoClient createMongoClient(MongoConfigurationProperties properties) {
        ConnectionString connString = new ConnectionString(properties.getConnectionString());
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())

        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .codecRegistry(pojoCodecRegistry)
                .retryWrites(true)
                .build();

        System.out.println("OK");

        return MongoClients.create(settings);
    }

    @Bean
    public MongoDatabase openDatabase(MongoClient client) {
        return client.getDatabase("game");
    }

    @Bean
    public MongoCollection<User> openUsersCollection(MongoDatabase db) {
        return db.getCollection("users", User.class);
    }

    @Bean
    public MongoCollection<Game> gameCollection(MongoDatabase db) {
        return db.getCollection("games", Game.class);
    }

}
