package com.bechtle.eagl.UserMappingsService.config;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableArangoRepositories(basePackages = "com.bechtle.eagl")
public class ArangoConfiguration implements com.arangodb.springframework.config.ArangoConfiguration {
    @Value("${arango.url}")
    String url;
    @Value("${arango.user}")
    String user;
    @Value("${arango.password}")
    String password;

    @Override
    public ArangoDB.Builder arango() {
        return new ArangoDB.Builder().host(url, 8529).user(user).password(password);
    }

    @Override
    public String database() {
        return "eagl_user_db";
    }
}
