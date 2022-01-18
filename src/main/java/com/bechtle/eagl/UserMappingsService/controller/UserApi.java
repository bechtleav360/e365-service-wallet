package com.bechtle.eagl.UserMappingsService.controller;

import com.bechtle.eagl.UserMappingsService.model.User;
import com.bechtle.eagl.UserMappingsService.services.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
@Api( tags = "Clients")
@Slf4j
public class UserApi {


    private final UserService userService;

    public UserApi(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public Mono<User> get(@PathVariable String userId)  {
        log.debug("(Request) Get user '{}'", userId);
        return this.userService.getUser(userId);
    }

    @PostMapping()
    public Mono<User> create(@RequestBody User user)  {
        log.debug("(Request) Create user '{}'", user.getLogin());
        return this.userService.createUser(user.getLogin());
    }


    /*
    @PostMapping("/{userId}/skills")
    public Mono<Relation> create(@PathVariable String userId, @RequestBody Set<Skill> skills) {
        return this.userService.addSkillsToUser(userId, skills)
                .then(this.userService.getUserProfileById(userId));

    }*/

    @DeleteMapping("/{userId}")
    public Mono<Void> delete(@PathVariable String userId) {
        log.debug("(Request) Delete user '{}'", userId);
        return this.userService.deleteUser(userId);

    }
}
