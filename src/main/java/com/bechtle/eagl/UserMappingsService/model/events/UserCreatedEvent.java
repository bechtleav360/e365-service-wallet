package com.bechtle.eagl.UserMappingsService.model.events;

import com.bechtle.eagl.UserMappingsService.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class UserCreatedEvent extends ApplicationEvent {

    public UserCreatedEvent(User source) {
        super(source);

        log.info("(Event) User with login '{}' was created in repository", source.getLogin());
    }
}
