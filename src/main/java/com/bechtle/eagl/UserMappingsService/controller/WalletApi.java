package com.bechtle.eagl.UserMappingsService.controller;

import com.bechtle.eagl.UserMappingsService.model.Mapping;
import com.bechtle.eagl.UserMappingsService.services.WalletService;
import com.bechtle.eagl.UserMappingsService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/wallet")
public class WalletApi {


    private final UserService userService;
    private final WalletService enmeshedConnectorService;

    public WalletApi(@Autowired UserService userService,
                     @Autowired WalletService enmeshedConnectorService) {
        this.userService = userService;
        this.enmeshedConnectorService = enmeshedConnectorService;
    }


    @GetMapping("/mappings")
    public Flux<Mapping> list()  {
        return this.userService.listAllUsers();
    }



    @GetMapping(value = "/mappings/token", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<byte[]> generateToken() {
        return this.enmeshedConnectorService.generateToken();
    }






}
