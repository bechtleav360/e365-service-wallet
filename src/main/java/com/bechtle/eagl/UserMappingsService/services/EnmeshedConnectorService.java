package com.bechtle.eagl.UserMappingsService.services;

import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

public class EnmeshedConnectorService {
/*
API Key kBsgrCkYfi5D89o9CO0k

http://eagl-enmeshed.germanywestcentral.azurecontainer.io/docs/json

RLT8aiygj5lNj28IsCKZ

 */

    @Value("endpoints.enmeshed.connector")
    String url;


    public Mono<Void> getRelationByWalletId(String wid) {
        // get
        return Mono.empty();
    }
}
