package com.bechtle.eagl.UserMappingsService.config;

import com.bechtle.eagl.UserMappingsService.services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Configuration
@EnableScheduling
@Profile("!test")
public class SchedulersConfiguration {

    private final RelationshipService relationshipService;

    public SchedulersConfiguration(@Autowired RelationshipService relationshipService) {

        this.relationshipService = relationshipService;
    }


    @Scheduled(fixedDelay = 60000)
    public void regularSync() {
        Mono<Boolean> sync = this.relationshipService.sync();
        if(sync != null) sync.subscribeOn(Schedulers.newSingle("sync")).subscribe();
    }
}
