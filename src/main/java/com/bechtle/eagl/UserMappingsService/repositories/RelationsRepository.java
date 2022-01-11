package com.bechtle.eagl.UserMappingsService.repositories;

import com.arangodb.springframework.repository.ArangoRepository;
import com.bechtle.eagl.UserMappingsService.model.Relation;

import java.util.Optional;

public interface RelationsRepository extends ArangoRepository<Relation, String> {
    Optional<Relation> findByLinkingCode(String linkingCode);
}
