package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RelationshipChangeType {
    @JsonProperty("Creation")
    CREATION,
    @JsonProperty("Termination")
    TERMINATION;

}
