package com.bechtle.eagl.UserMappingsService.services;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.EnmeshedConnectorClient;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Relationship;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.RelationshipChange;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.RelationshipTemplate;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.enums.RelationshipChangeStatus;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.responses.Changes;
import com.bechtle.eagl.UserMappingsService.model.Relation;
import com.bechtle.eagl.UserMappingsService.model.events.*;
import com.bechtle.eagl.UserMappingsService.repositories.RelationsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.OptionalInt;
import java.util.Random;

@Service
@Slf4j
public class RelationshipService {

    @Value("${enmeshed.id.rt}")
    String relationshipTemplateId;

    private final EnmeshedConnectorClient connectorClient;
    private final RelationsRepository relationsRepository;
    private final ApplicationEventPublisher eventPublisher;


    public RelationshipService(@Autowired EnmeshedConnectorClient connectorClient,
                               @Autowired RelationsRepository relationsRepository,
                               @Autowired ApplicationEventPublisher eventPublisher) {
        this.connectorClient = connectorClient;
        this.relationsRepository = relationsRepository;
        this.eventPublisher = eventPublisher;
    }


    public Mono<Relation> associateLogin(String userId, String code) {
        log.debug("Get relation with linking code '{}'", code);
        Relation example = Relation.builder().linkingCode(code).build();
        return this.relationsRepository.findByLinkingCode(code)
                .map(Mono::just)
                .orElseGet(Mono::empty)
                .map(relation -> {
                    relation.setLogin(userId);
                    return this.relationsRepository.save(relation);
                })
                .map(relation -> {
                    eventPublisher.publishEvent(new UserAssociatedEvent(relation));
                    return relation;
                });


    }


    public Mono<byte[]> generateToken() {
        return this.connectorClient.getRelationshipTemplate(relationshipTemplateId)
                .map(RelationshipTemplate::getId)
                .flatMap(this.connectorClient::getTokenImage);
    }

    public Mono<Void> sync() {
        log.trace("Syncing changes");
        Mono<Changes> sync = this.connectorClient.sync();

        Mono<Void> changesFlux = sync
                .map(changes -> {
                    for (Relationship relationship : changes.getRelationships()) {
                        for (RelationshipChange change : relationship.getChanges()) {
                            log.info("Change detected with type {} and status {} in relation with id {}", change.getType(), change.getStatus(), relationship.getId());

                            switch (change.getType()) {
                                case CREATION:
                                    eventPublisher.publishEvent(new RelationshipCreatedEvent(relationship, change));
                                    break;
                                case TERMINATION:
                                    eventPublisher.publishEvent(new RelationshipTerminatedEvent(relationship, change));
                                    break;
                            }
                        }
                    }


                    return changes;
                }).then();

        Mono<Void> messagesFlux = sync.flatMapMany(result -> Flux.fromIterable(result.getMessages()))
                .map(message -> {
                    eventPublisher.publishEvent(new MessageReceivedEvent(message));
                    return message;
                }).then();

        return Mono.zip(changesFlux, messagesFlux).thenEmpty(Mono.empty());

    }


    @EventListener(ApplicationReadyEvent.class)
    public void checkRelationTemplateExists(ApplicationReadyEvent event) {
        if (! event.getApplicationContext().getEnvironment().acceptsProfiles(Profiles.of("remote"))) {
            log.info("Checking configuration skipped, we are running tests");
            return;
        }


        log.info("Checking Enmeshed Connector configuration with relationship template id: {}", relationshipTemplateId);
        this.connectorClient.getRelationshipTemplate(relationshipTemplateId)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Configured Relationship Template with id {} does not exist. Please adapt configuration and restart the service.", relationshipTemplateId);
                    AvailabilityChangeEvent.publish(eventPublisher, this, LivenessState.BROKEN);
                    return Mono.empty();
                }));
    }

    @EventListener(RelationshipCreatedEvent.class)
    public void acceptCreatedRelationshipChange(RelationshipCreatedEvent event) {

        if(event.getChange().getStatus() == RelationshipChangeStatus.REVOKED) {
            log.info("A relationship creation has been revoked from the user");
            return;
        }

        // we create a linking code, which is sent to the user and which needs to be entered in the webapp
        OptionalInt asInt = new Random().ints(1000, 10000).findFirst();

        this.connectorClient.acceptChange(event.getRelationship().getId(), event.getChange().getId())
                .subscribe(relationship -> {
                    log.info("Accepted creation of new Relationship '{}'", event.getRelationship().getId());
                    eventPublisher.publishEvent(new RelationshipAcceptedEvent(relationship, event.getChange(), String.valueOf(asInt.orElse(5212))));
                });
    }

    @EventListener(RelationshipTerminatedEvent.class)
    public void acceptTerminatedRelationshipChange(RelationshipCreatedEvent event) {
        this.connectorClient.acceptChange(event.getRelationship().getId(), event.getChange().getId())
                .subscribe(relationship -> {
                    log.info("Accepted termination of Relationship with id {}", event.getRelationship().getId());
                    eventPublisher.publishEvent(new RelationshipTerminatedEvent(relationship, event.getChange()));
                });
    }


    @EventListener(RelationshipAcceptedEvent.class)
    public void saveRelation(RelationshipAcceptedEvent event) {
        Relation build = Relation.builder()
                .linkingCode(event.getLinkingCode())
                .relationshipId(event.getRelationship().getId())
                .peer(event.getRelationship().getPeer())
                .build();
        Relation save = this.relationsRepository.save(build);

        eventPublisher.publishEvent(new RelationshipStoredEvent(save));

    }




}
