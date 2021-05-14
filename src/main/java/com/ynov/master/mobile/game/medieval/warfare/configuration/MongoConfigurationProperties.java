package com.ynov.master.mobile.game.medieval.warfare.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mongo")
public class MongoConfigurationProperties {
    
    private String connectionString;

}
