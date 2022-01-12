package com.bechtle.eagl.UserMappingsService.repositories;

import com.arangodb.springframework.repository.ArangoRepository;
import com.bechtle.eagl.UserMappingsService.model.Relationship;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface RelationshipRepository extends ArangoRepository<Relationship, String> {
    Optional<Relationship> findByLinkingCode(String linkingCode);


}
