package com.bechtle.eagl.UserMappingsService.model.events;

import com.bechtle.eagl.UserMappingsService.model.Relationship;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class RelationshipStoredEvent extends ApplicationEvent {
    public RelationshipStoredEvent(Relationship relation) {
        super(relation);
        log.debug("(Event) Relationship '{}' with linking code '{}' was created.", relation.getRelationshipId(), relation.getLinkingCode());
    }
}
