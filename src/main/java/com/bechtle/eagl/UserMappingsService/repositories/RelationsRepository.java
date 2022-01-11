package com.bechtle.eagl.UserMappingsService.repositories;

import com.arangodb.springframework.repository.ArangoRepository;
import com.bechtle.eagl.UserMappingsService.model.Relation;
import com.bechtle.eagl.UserMappingsService.model.User;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;
import org.springframework.data.domain.Example;

import java.util.Optional;

public interface RelationsRepository extends ArangoRepository<Relation, String> {
    Optional<Relation> findByLinkingCode(String linkingCode);
}
