package com.ynov.master.mobile.game.medieval.warfare.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.WebpushConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties(FirebaseCloudMessagingConfigurationProperties.class)
public class FirebaseCloudMessagingConfiguration {

    @Bean
    public FirebaseApp firebaseConfig(FirebaseCloudMessagingConfigurationProperties properties) throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(properties.toCredentialStream())).build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public WebpushConfig.Builder webPushConfig(FirebaseCloudMessagingConfigurationProperties properties){
        return WebpushConfig.builder().putHeader("ttl",properties.getTimeToLive());
    }
}
