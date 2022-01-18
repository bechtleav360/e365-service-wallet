package com.bechtle.eagl.UserMappingsService.model.events;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.ChangeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class MessageReceivedEvent extends ApplicationEvent {

    public MessageReceivedEvent(ChangeMessage source) {
        super(source);
    }
}
