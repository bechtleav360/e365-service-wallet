package com.bechtle.eagl.UserMappingsService.model.events;

import com.bechtle.eagl.UserMappingsService.model.Relationship;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class RelationshipLinkedEvent extends ApplicationEvent {
    public RelationshipLinkedEvent(Relationship relation) {
        super(relation);

        log.info("(Event) User with login '{}' has been linked to relation with id '{}'", relation.getLogin(), relation.getRelationshipId());
    }

    public Relationship getRelation() {
        return (Relationship) super.getSource();
    }
}
