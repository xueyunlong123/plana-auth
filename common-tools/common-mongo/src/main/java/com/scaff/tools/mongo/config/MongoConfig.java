package com.scaff.tools.mongo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Created by chenwen on 17/3/30.
 */
@Configuration
@ConfigurationProperties(prefix = "scaff.mongo")
@Data
public class MongoConfig {
    private String host;

    private Integer port;

    private String username;

    private String password;

    private String database;

    private boolean auth;

    private int maxWaitTime = 5000;

    private int connectionTimeout = 5000;

    private int connectionsPerHost = 500;

    private int threadsAllowedToBlockForConnectionMultiplier = 500;
}
