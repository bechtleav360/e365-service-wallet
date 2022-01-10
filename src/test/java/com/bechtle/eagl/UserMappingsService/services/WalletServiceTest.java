package com.bechtle.eagl.UserMappingsService.services;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.EnmeshedConnectorClient;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Message;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Relationship;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.responses.SyncResult;
import com.bechtle.eagl.UserMappingsService.model.events.RelationshipCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@RecordApplicationEvents
class WalletServiceTest {
    @Autowired
    RelationshipService walletService;

    @Autowired
    private ApplicationEvents applicationEvents;

    @MockBean
    EnmeshedConnectorClient enmeshedConnectorClient;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testSync()  {
        Mockito.when(enmeshedConnectorClient.sync()).thenReturn(this.loadResponseAs(SyncResult.class, "SyncResponse.json"));
        Mockito.when(enmeshedConnectorClient.acceptChange(Mockito.anyString(), Mockito.anyString())).thenReturn(this.loadResponseAs(Relationship.class, "AcceptResponse.json"));
        Mockito.when(enmeshedConnectorClient.sendMessage(Mockito.any())).thenReturn(this.loadResponseAs(Message.class, "SentMessageResponse.json"));

        StepVerifier.Assertions assertions = StepVerifier.create(walletService.sync())
                .thenAwait(Duration.of(5, ChronoUnit.SECONDS))
                .expectComplete()
                .verifyThenAssertThat();

        assertions.hasNotDroppedElements();
        assertEquals(1, applicationEvents
                .stream(RelationshipCreatedEvent.class)
                .filter(event -> ((RelationshipCreatedEvent) event).getRelationship().getId().length() > 1)
                .count());
                /*
                .expectComplete().verifyThenAssertThat().unused -> {

                })
                .verifyComplete();*/

    }


    public <T> Mono<T> loadResponseAs(Class<T> cls, String jsonfile)  {
        return Mono.create(c -> {

            try {
                Resource file = new ClassPathResource(jsonfile);
                Assertions.assertNotNull(file);
                Assertions.assertTrue(file.isFile());
                ObjectReader objectReader = objectMapper.readerFor(cls);

                Object o = objectReader.readValue(file.getInputStream());
                c.success((T) o);

            } catch (IOException e) {
                c.error(e);
            }

        });




    }

}