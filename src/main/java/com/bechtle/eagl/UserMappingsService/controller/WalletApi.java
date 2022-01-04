package com.bechtle.eagl.UserMappingsService.controller;

import com.bechtle.eagl.UserMappingsService.model.Mapping;
import com.bechtle.eagl.UserMappingsService.model.Relation;
import com.bechtle.eagl.UserMappingsService.model.Skill;
import com.bechtle.eagl.UserMappingsService.repositories.MappingRepository;
import com.bechtle.eagl.UserMappingsService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class WalletApi {


    private final UserService userService;

    public WalletApi(@Autowired UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    public Flux<Mapping> list()  {
        return this.userService.listAllUsers();
    }

    @GetMapping("/user/{userId}")
    public Mono<Relation> get(@PathVariable String userId)  {
        return this.userService.getUserProfileById(userId);
    }

    @PostMapping("/user/{userId}/skills")
    public Mono<Relation> create(@PathVariable String userId, @RequestBody Set<Skill> skills) {
        return this.userService.addSkillsToUser(userId, skills)
                .then(this.userService.getUserProfileById(userId));

    }

    @DeleteMapping("/{userId}")
    public Mono<Void> delete(@PathVariable String userId) {
        return this.userService.deleteUser(userId);

    }


}
