package com.bechtle.eagl.UserMappingsService.controller;

import com.bechtle.eagl.UserMappingsService.model.Relationship;
import com.bechtle.eagl.UserMappingsService.model.User;
import com.bechtle.eagl.UserMappingsService.model.enums.UserFlags;
import com.bechtle.eagl.UserMappingsService.services.RelationshipService;
import com.bechtle.eagl.UserMappingsService.services.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/api/wallet")
@Api( tags = "Wallet")
@Slf4j
public class WalletApi {


    private final UserService userService;
    private final RelationshipService relationshipService;

    public WalletApi(@Autowired UserService userService,
                     @Autowired RelationshipService relationshipService) {
        this.userService = userService;

        this.relationshipService = relationshipService;
    }


    @GetMapping("/relations")
    public Flux<User> list()  {
        log.debug("(Request) All relations");
        return this.userService.listAllUsers();
    }

    @GetMapping(value = "/sync")
    public Mono<Boolean> sync() {
        log.debug("(Request) Sync changes");
        return this.relationshipService.sync();
    }


    @GetMapping(value = "/{userId}/token", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<byte[]> generateToken(@PathVariable String userId) {
        log.debug("(Request) Generate Token image for user '{}'", userId);
        return Mono.zip(
                this.userService.flagUser(userId, UserFlags.TOKEN_GENERATED),
                this.relationshipService.generateToken()
        ).map(Tuple2::getT2);
    }

    @PutMapping(value = "/{userId}/code", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Relationship> setCode(@PathVariable String userId, @RequestBody String code) {
        log.debug("(Request) Link user '{}' to relation with code '{}'", userId, code);
        return Mono.zip(
                this.userService.flagUser(userId, UserFlags.RELATIONSHIP_LINKED),
                this.relationshipService.associateLogin(userId, code)
        ).map(Tuple2::getT2);


    }






}
