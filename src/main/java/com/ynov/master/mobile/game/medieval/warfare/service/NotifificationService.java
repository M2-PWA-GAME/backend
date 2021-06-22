package com.ynov.master.mobile.game.medieval.warfare.service;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.ynov.master.mobile.game.medieval.warfare.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public void sendNotification(String userId, WebpushNotification notification, Map<String, String> data) {
        Message msg = Message.builder().putAllData(data).setTopic(userId)
                            .setWebpushConfig(configBuilder.setNotification(notification).build()
                        ).build()   ;
    }

    public void yourTurnNotification(String userId, String gameId )
    {
        User usr = userService.search()
    }



}
