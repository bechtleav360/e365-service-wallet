package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.enums.ContentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class MessageContent {

    @JsonProperty("@type")
    ContentType type;

    @JsonProperty("to")
    @Singular
    List<String> recipients;

    String subject;

    String body;

}
