package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RelationshipChangeStatus {
    @JsonProperty("Pending")
    PENDING,
    @JsonProperty("Rejected")
    REJECTED,
    @JsonProperty("Revoked")
    REVOKED,
    @JsonProperty("Accepted")
    ACCEPTED;

}
