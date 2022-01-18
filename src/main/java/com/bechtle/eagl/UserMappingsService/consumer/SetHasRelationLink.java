package com.bechtle.eagl.UserMappingsService.consumer;

import com.bechtle.eagl.UserMappingsService.model.Relationship;
import com.bechtle.eagl.UserMappingsService.model.edges.HasRelationship;
import com.bechtle.eagl.UserMappingsService.model.events.RelationshipLinkedEvent;
import com.bechtle.eagl.UserMappingsService.repositories.RelationshipRepository;
import com.bechtle.eagl.UserMappingsService.repositories.UserRepository;
import com.bechtle.eagl.UserMappingsService.repositories.edges.HasRelationshipRepository;
import com.bechtle.eagl.UserMappingsService.services.RelationshipService;
import com.bechtle.eagl.UserMappingsService.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SetHasRelationLink implements ApplicationListener<RelationshipLinkedEvent> {

    private final RelationshipRepository relationshipRepository;
    private final UserRepository userRepository;
    private final HasRelationshipRepository hasRelationshipRepository;

    public SetHasRelationLink(
                              @Autowired RelationshipRepository relationshipRepository,
                              @Autowired UserRepository userRepository,
                              @Autowired HasRelationshipRepository hasRelationshipRepository) {
        this.relationshipRepository = relationshipRepository;
        this.userRepository = userRepository;
        this.hasRelationshipRepository = hasRelationshipRepository;
    }

    @Override
    public void onApplicationEvent(RelationshipLinkedEvent event) {
        if (!StringUtils.hasLength(event.getRelation().getLogin())) {
            log.error("(Event) Relation '{}' has been linked to user, but username is not stored in relation", event.getRelation().getRelationshipId());
            return;
        }

        Mono.zip(
                relationshipRepository.findById(event.getRelation().getRelationshipId())
                        .map(Mono::just)
                        .orElseGet(Mono::empty),
                userRepository.findById(event.getRelation().getLogin())
                        .map(Mono::just)
                        .orElseGet(Mono::empty)
        ).map(objects -> {
            HasRelationship build = HasRelationship.builder()
                    .relationship(objects.getT1())
                    .user(objects.getT2())
                    .build();
            return this.hasRelationshipRepository.save(build);
        }).subscribe(edge -> {
            log.debug("Successfully linked relation '{}' and user '{}'", edge.getRelationship().getRelationshipId(), edge.getUser().getLogin());
        });


    }
}
