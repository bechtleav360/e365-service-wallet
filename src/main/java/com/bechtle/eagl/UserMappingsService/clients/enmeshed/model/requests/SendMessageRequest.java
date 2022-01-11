package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.requests;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.MessageContent;
import lombok.Data;

import java.util.List;

@Data
public class SendMessageRequest {

    List<String> recipients;
    MessageContent content;
    List<String> attachments;
}
