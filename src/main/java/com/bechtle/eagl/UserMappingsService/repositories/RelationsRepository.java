package com.bechtle.eagl.UserMappingsService.repositories;

import com.arangodb.springframework.repository.ArangoRepository;
import com.bechtle.eagl.UserMappingsService.model.Relation;
import com.bechtle.eagl.UserMappingsService.model.User;

public interface RelationsRepository extends ArangoRepository<Relation, String> {
}
