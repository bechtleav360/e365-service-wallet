package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.responses;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Message;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Relationship;
import lombok.Data;

import java.util.List;

@Data
public class Changes {
    List<Relationship> relationships;
    List<Message> messages;
}
