package com.ynov.master.mobile.game.medieval.warfare.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.ynov.master.mobile.game.medieval.warfare.model.Game;
import com.ynov.master.mobile.game.medieval.warfare.model.NotificationType;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class NotifificationService {

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
                "C'est ton tour " + usr.getUsername() + ", à toi de jouer dans la partie " + game.getName(),
                "https://medieval-warfare-game.herokuapp.com/icon-512.png"
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

}
