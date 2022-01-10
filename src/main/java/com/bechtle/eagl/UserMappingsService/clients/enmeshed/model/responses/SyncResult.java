package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.responses;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Message;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Relationship;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

import javax.management.ObjectName;
import java.util.List;

@Data
public class SyncResult {
    List<Relationship> relationships;
    List<Message> messages;
}
