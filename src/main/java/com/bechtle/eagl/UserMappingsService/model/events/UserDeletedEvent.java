package com.bechtle.eagl.UserMappingsService.model.events;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Message;
import com.bechtle.eagl.UserMappingsService.model.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
@Getter
public class UserDeletedEvent extends ApplicationEvent {

    public UserDeletedEvent(User source) {
        super(source);

        log.info("(Event) User with login '{}' was deleted from repository", source.getLogin());

        // TODO: should terminate relation
    }
}
