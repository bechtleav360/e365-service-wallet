package com.bechtle.eagl.UserMappingsService.services;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.EnmeshedConnectorClient;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.ChangeMessage;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.ChangeRelationship;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.responses.Changes;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.responses.Result;
import com.bechtle.eagl.UserMappingsService.model.events.RelationshipCreatedEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.extern.slf4j.Slf4j;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@RecordApplicationEvents
@Slf4j
class WalletServiceTest {
    @Autowired
    RelationshipService walletService;

    @Autowired
    private ApplicationEvents applicationEvents;

    @MockBean
    EnmeshedConnectorClient enmeshedConnectorClient;

    @Autowired
    ObjectMapper objectMapper;

    @SuppressWarnings("Convert2Diamond")
    @Test
    public void testSync()  {

        Mockito.when(enmeshedConnectorClient.sync()).thenReturn(this.loadResult(new TypeReference<Result<Changes>>() {}, "json/SyncResponse.json"));
        Mockito.when(enmeshedConnectorClient.acceptChange(Mockito.anyString(), Mockito.anyString())).thenReturn(this.loadResult(new TypeReference<Result<ChangeRelationship>>() {}, "json/AcceptResponse.json"));
        Mockito.when(enmeshedConnectorClient.sendMessage(Mockito.any())).thenReturn(this.loadResult(new TypeReference<Result<ChangeMessage>>() {}, "json/SentMessageResponse.json"));

        StepVerifier.Assertions assertions = StepVerifier.create(walletService.sync())
                .thenAwait(Duration.of(5, ChronoUnit.SECONDS))
                .expectNext(true)
                .expectComplete()
                .verifyThenAssertThat();

        assertions.hasNotDroppedElements();
        assertEquals(1, applicationEvents
                .stream(RelationshipCreatedEvent.class)
                .filter(event -> event.getRelationship().getId().length() > 1)
                .count());
                /*
                .expectComplete().verifyThenAssertThat().unused -> {

                })
                .verifyComplete();*/

    }


    public <T> Mono<T> loadResult(TypeReference<Result<T>> typeReference, String jsonfile)  {
        return Mono.create(c -> {

            try {

                log.debug("Loading response file '{}' in test ", jsonfile);
                Resource file = new ClassPathResource(jsonfile);
                Assertions.assertNotNull(file);
                Assertions.assertTrue(file.isFile());
                ObjectReader objectReader = objectMapper.readerFor(typeReference);

                Result<T> o = objectReader.readValue(file.getInputStream());
                c.success(o.getResult());

            } catch (IOException e) {
                log.error("Failed to load and parse json file {}", jsonfile, e);
                c.error(e);
            }
        });
    }

    public <R> Mono<R> loadResponseAs(String jsonfile)  {
        return Mono.create(c -> {

            try {
                log.debug("Loading response file '{}' in test ", jsonfile);
                Resource file = new ClassPathResource(jsonfile);
                Assertions.assertNotNull(file);
                Assertions.assertTrue(file.isFile());
                ObjectReader objectReader = objectMapper.readerFor(new TypeReference<R>() {});

                R o = objectReader.readValue(file.getInputStream());
                c.success(o);

            } catch (IOException e) {
                c.error(e);
            }
        });
    }

}