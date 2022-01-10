package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.enums.RelationshipStatus;
import lombok.Data;

import java.util.List;

@Data
public class Relationship {

    String id;
    RelationshipStatus status;
    RelationshipTemplate template;
    String peer;
    List<RelationshipChange> changes;


}
