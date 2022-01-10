package com.bechtle.eagl.UserMappingsService.model.events;

import com.bechtle.eagl.UserMappingsService.model.Relation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class UserAssociatedEvent extends ApplicationEvent {
    public UserAssociatedEvent(Relation relation) {
        super(relation);

        log.info("(Event) User with login '{}' has been linked to relation with id '{}'", relation.getLogin(), relation.getRelationshipId());
    }

    public Relation getRelation() {
        return (Relation) super.getSource();
    }
}
