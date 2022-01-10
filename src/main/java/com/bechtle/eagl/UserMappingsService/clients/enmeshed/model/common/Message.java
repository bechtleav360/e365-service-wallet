package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class Message {

    String id;
    String createdBy;
    String createdByDevice;
    String createdAt;

    List<Address> relationshipIds;

    JsonNode recipients;
    JsonNode content;
    JsonNode attachments;
}
