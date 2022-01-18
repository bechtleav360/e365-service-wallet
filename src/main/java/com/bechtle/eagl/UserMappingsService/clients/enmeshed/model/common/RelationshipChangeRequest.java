package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 *
 request* {
 createdBy*: Address
 createdByDevice*: DeviceID
 createdAt*: date-time 🆁

 A timestamp that describes when this RelationshipChange was created on the platform.
 content* {
 }
 }
 */
@Data
public class RelationshipChangeRequest {

    String createdBy;
    String createdByDevice;
    String createdAt;

    JsonNode content;

}
