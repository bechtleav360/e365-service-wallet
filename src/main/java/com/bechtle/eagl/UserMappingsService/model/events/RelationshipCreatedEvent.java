package com.bechtle.eagl.UserMappingsService.model.events;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Relationship;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.RelationshipChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class RelationshipCreatedEvent extends ApplicationEvent {

    private final RelationshipChange change;

    public RelationshipCreatedEvent(Relationship source, RelationshipChange change) {
        super(source);
        this.change = change;
        log.debug("(Event) A new relationship '{}' was created.", source.getId());
    }

    public Relationship getRelationship() {
        return (Relationship) super.getSource();
    }

    public RelationshipChange getChange() {
        return change;
    }
}
