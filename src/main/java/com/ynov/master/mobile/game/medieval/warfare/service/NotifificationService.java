package com.ynov.master.mobile.game.medieval.warfare.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NotifificationService {

    public void sendNotification(String userId ,String payload)
    {
      log.info("Notification send to " + userId + " with payload : " + payload );
    }

    public void sendNotifications(List<String> userIds , String payload)
    {
        userIds.forEach( id -> sendNotification(id,payload));
    }
}
