package com.bechtle.eagl.UserMappingsService.model.events;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.ChangeRelationship;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.RelationshipChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class RelationshipAcceptedEvent extends ApplicationEvent {

    private RelationshipChange change;
    private String linkingCode;

    public RelationshipAcceptedEvent(ChangeRelationship source, RelationshipChange change, String linkingCode) {
        super(source);
        this.change = change;
        this.linkingCode = linkingCode;
        log.debug("(Event) Change of type '{}' for relationship '{}' was accepted", change.getType(), source.getId());
    }

    public ChangeRelationship getRelationship() {
        return (ChangeRelationship) super.getSource();
    }

    public String getLinkingCode() {
        return linkingCode;
    }
}
