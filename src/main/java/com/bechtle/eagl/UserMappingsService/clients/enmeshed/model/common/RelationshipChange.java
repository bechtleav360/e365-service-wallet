package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.enums.RelationshipChangeStatus;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.enums.RelationshipChangeType;
import lombok.Data;

@Data
public class RelationshipChange {

    String id;
    RelationshipChangeType type;
    RelationshipChangeStatus status;

    RelationshipChangeRequest request;
    RelationshipChangeResponse response;

}
