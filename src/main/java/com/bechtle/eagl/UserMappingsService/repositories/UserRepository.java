package com.bechtle.eagl.UserMappingsService.repositories;

import com.arangodb.springframework.repository.ArangoRepository;
import com.bechtle.eagl.UserMappingsService.model.Mapping;
import com.bechtle.eagl.UserMappingsService.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ArangoRepository<User, String> {

}
