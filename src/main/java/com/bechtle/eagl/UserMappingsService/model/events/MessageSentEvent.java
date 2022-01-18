package com.bechtle.eagl.UserMappingsService.model.events;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.MessageContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class MessageSentEvent extends ApplicationEvent {

    public MessageSentEvent(MessageContent content) {
        super(content);
        log.info("(Event) A message was sent to recipient(s) '{}' with subject '{}'", content.getRecipients(), content.getSubject());
    }
}
