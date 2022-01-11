package com.bechtle.eagl.UserMappingsService.config;

import com.bechtle.eagl.UserMappingsService.services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulersConfiguration {

    private final RelationshipService relationshipService;

    public SchedulersConfiguration(@Autowired RelationshipService relationshipService) {

        this.relationshipService = relationshipService;
    }


    @Scheduled(fixedDelay = 20000)
    public void regularSync() {
        this.relationshipService.sync().subscribe();
    }
}
