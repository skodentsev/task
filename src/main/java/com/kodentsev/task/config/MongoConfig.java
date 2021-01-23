package com.kodentsev.task.config;

import com.kodentsev.task.infrastructure.properties.MongoProperties;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.kodentsev.domain.repository")
@RequiredArgsConstructor
public class MongoConfig {

    private final MongoProperties properties;

    @Bean
    public MongoClient mongo() {
        System.out.println("/////////////////////////////////////////////   " + properties.getTest());
        final ConnectionString connectionString = new ConnectionString(properties.getUri());
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongo(), properties.getDatabase());
    }

}
