package com.bechtle.eagl.UserMappingsService.model.events;

import com.bechtle.eagl.UserMappingsService.model.User;
import com.bechtle.eagl.UserMappingsService.model.enums.UserFlags;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserFlaggedEvent extends ApplicationEvent {
    private UserFlags flag;

    public UserFlaggedEvent(User user, UserFlags flag) {
        super(user);
        this.flag = flag;
    }
}
