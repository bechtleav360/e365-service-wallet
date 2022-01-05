package com.bechtle.eagl.UserMappingsService.services;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.EnmeshedConnectorClient;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.RelationshipTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class WalletService {

    @Value("${enmeshed.identifier.relationshiptemplate}")
    String relationshipTemplateId;

    private final EnmeshedConnectorClient connectorClient;

    public WalletService(@Autowired EnmeshedConnectorClient connectorClient) {
        this.connectorClient = connectorClient;
    }

    public Mono<byte[]> generateToken() {
        return this.connectorClient.getRelationshipTemplate(relationshipTemplateId)
                .map(RelationshipTemplate::getId)
                .flatMap(this.connectorClient::getTokenImage);
    }


}
