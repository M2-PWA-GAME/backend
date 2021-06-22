package com.ynov.master.mobile.game.medieval.warfare.service;

import com.google.firebase.messaging.*;
import com.ynov.master.mobile.game.medieval.warfare.model.Game;
import com.ynov.master.mobile.game.medieval.warfare.model.NotificationType;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class NotificationService {

    @Autowired
    private WebpushConfig.Builder configBuilder;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private FirebaseMessaging messageHandler;

    @SneakyThrows
    public void sendNotification(String userId, WebpushNotification notification, Map<String, String> data) {

            Message message = Message.builder().putAllData(data).setTopic(userId)
                    .setWebpushConfig(configBuilder.setNotification(notification).build()
                    ).build();

            String response = messageHandler.sendAsync(message).get();
            log.info("Sent message: " + response);
    }

    public void yourTurnNotification(String userId, String gameId) {
        User usr = userService.search(userId);
        Game game = gameService.getGame(gameId);

        WebpushNotification notification = new WebpushNotification(
                "Joue ton coup !",
                "C'est ton tour " + usr.getUsername() + ", à toi de jouer dans la partie " + game.getName()
        );

        HashMap<String, String> data = new HashMap<>();
        data.put("type", NotificationType.YOUR_TURN.toString());
        data.put("gameId", game.getId().toString());

        sendNotification(userId, notification, data);
    }

    public void gameStarted(List<String> usersId, String gameId) {
        Game game = gameService.getGame(gameId);

        WebpushNotification notification = new WebpushNotification(
                "La partie commence",
                "Tous les joueur ont rejoins la partie " + game.getName() + ", que le meilleur gagne !"
        );

        HashMap<String, String> data = new HashMap<>();
        data.put("type", NotificationType.GAME_STARTED.toString());
        data.put("gameId", game.getId().toString());

        usersId.forEach(id -> sendNotification(id, notification, data));
    }

    public void subscribe(String topic, String clientToken)
    {
        try {
            TopicManagementResponse response = messageHandler.subscribeToTopicAsync(Collections.singletonList(clientToken), topic).get();
            System.out
                    .println(response.getSuccessCount() + " tokens were subscribed successfully");
        }
        catch (InterruptedException | ExecutionException e) {
            log.error("subscribe",e);
        }
    }

}
