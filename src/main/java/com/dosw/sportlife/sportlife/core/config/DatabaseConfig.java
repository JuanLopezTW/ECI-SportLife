package com.dosw.sportlife.sportlife.core.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.dosw.sportlife.sportlife.persistence.relational.repository"
)
@EnableMongoRepositories(
        basePackages = "com.dosw.sportlife.sportlife.persistence.nonrelational.repository",
        mongoTemplateRef = "mongoTemplate"
)
public class DatabaseConfig {
}