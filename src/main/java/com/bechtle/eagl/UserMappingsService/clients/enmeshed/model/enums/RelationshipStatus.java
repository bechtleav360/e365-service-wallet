package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * The status of the relationship
 */
public enum RelationshipStatus {
    @JsonProperty("Pending")
    PENDING,
    @JsonProperty("Active")
    ACTIVE,
    @JsonProperty("Rejected")
    REJECTED,
    @JsonProperty("Revoked")
    REVOKED,
    @JsonProperty("Terminated")
    TERMINATED;




}
