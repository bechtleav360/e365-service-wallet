package com.bechtle.eagl.UserMappingsService.controller;

import com.bechtle.eagl.UserMappingsService.model.Relation;
import com.bechtle.eagl.UserMappingsService.model.User;
import com.bechtle.eagl.UserMappingsService.model.enums.UserFlags;
import com.bechtle.eagl.UserMappingsService.services.RelationshipService;
import com.bechtle.eagl.UserMappingsService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/api/wallet")
public class WalletApi {


    private final UserService userService;
    private final RelationshipService relationshipService;

    public WalletApi(@Autowired UserService userService,
                     @Autowired RelationshipService relationshipService) {
        this.userService = userService;

        this.relationshipService = relationshipService;
    }


    @GetMapping("/mappings")
    public Flux<User> list()  {
        return this.userService.listAllUsers();
    }

    @GetMapping(value = "/sync")
    public Mono<Void> sync() {
        return this.relationshipService.sync();
    }


    @GetMapping(value = "/{userId}/token", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<byte[]> generateToken(@PathVariable String userId) {
        return Mono.zip(
                this.userService.flagUser(userId, UserFlags.TOKEN_GENERATED),
                this.relationshipService.generateToken()
        ).map(Tuple2::getT2);
    }

    @PutMapping(value = "/{userId}/code", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Relation> setCode(@PathVariable String userId, @RequestBody String code) {
        return this.relationshipService.associateLogin(userId, code);

    }






}
