package com.bechtle.eagl.UserMappingsService.model.events;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Message;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Relationship;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class MessageReceivedEvent extends ApplicationEvent {

    public MessageReceivedEvent(Message source) {
        super(source);
    }
}
