package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.responses;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.ChangeMessage;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.ChangeRelationship;
import lombok.Data;

import java.util.List;

@Data
public class Changes {
    List<ChangeRelationship> relationships;
    List<ChangeMessage> messages;
}
