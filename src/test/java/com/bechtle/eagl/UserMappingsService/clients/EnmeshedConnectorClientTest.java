package com.bechtle.eagl.UserMappingsService.clients;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.EnmeshedConnectorClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Profile({"integrationtests", "local"})
class EnmeshedConnectorClientTest {

    @Autowired
    EnmeshedConnectorClient client;

    @Test
    void getRelationshipTemplates() {
        StepVerifier.create(client.getRelationshipTemplates())
                .assertNext(relationshipTemplate -> Assertions.assertNotNull(relationshipTemplate.getId()))
                .thenCancel()
                .verify();
    }

    @Test
    void testGetRelationshipTemplate() {
        String rltid = "RLTGG2HW3sSrREQC7Ttr";
        StepVerifier.create(client.getRelationshipTemplate(rltid))
                .assertNext(relationshipTemplate -> Assertions.assertEquals(rltid, relationshipTemplate.getId()))
                .verifyComplete();
    }

    @Test
    void getToken() {
        String rltid = "RLTGG2HW3sSrREQC7Ttr";
        StepVerifier.create(client.getTokenImage(rltid))
                .assertNext(bytes -> {
                    Assertions.assertNotNull(bytes);
                    Assertions.assertTrue(bytes.length > 0);
                })
                .verifyComplete();
    }

    @Test
    void sync() {
    }

    @Test
    void initialize() {
    }


}