package com.bechtle.eagl.UserMappingsService.model.events;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Relationship;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.RelationshipChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class RelationshipTerminatedEvent extends ApplicationEvent {

    private RelationshipChange change;

    public RelationshipTerminatedEvent(Relationship source, RelationshipChange change) {
        super(source);
        this.change = change;
        log.debug("New RelationshipTerminatedEvent for relationship with id {} and change of type {}", source.getId(), change.getType());
    }

    public Relationship getRelationship() {
        return (Relationship) super.getSource();
    }

    public RelationshipChange getChange() {
        return change;
    }
}
