package com.bechtle.eagl.UserMappingsService.repositories;

import com.arangodb.springframework.repository.ArangoRepository;
import com.bechtle.eagl.UserMappingsService.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends ArangoRepository<User, String> {


}
