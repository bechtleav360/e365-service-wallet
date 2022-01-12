package com.bechtle.eagl.UserMappingsService.repositories.edges;

import com.arangodb.springframework.repository.ArangoRepository;
import com.bechtle.eagl.UserMappingsService.model.edges.HasRelationship;

public interface HasRelationshipRepository extends ArangoRepository<HasRelationship, String> {
}
