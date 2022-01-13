package com.bechtle.eagl.UserMappingsService.model.events;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.ChangeRelationship;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.RelationshipChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class RelationshipCreatedEvent extends ApplicationEvent {

    private final RelationshipChange change;

    public RelationshipCreatedEvent(ChangeRelationship source, RelationshipChange change) {
        super(source);
        this.change = change;
        log.debug("(Event) A new relationship '{}' was created.", source.getId());
    }

    public ChangeRelationship getRelationship() {
        return (ChangeRelationship) super.getSource();
    }

    public RelationshipChange getChange() {
        return change;
    }
}
