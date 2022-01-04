package com.bechtle.eagl.UserMappingsService.repositories;

import com.bechtle.eagl.UserMappingsService.model.Mapping;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MappingRepository extends ReactiveCrudRepository<Mapping, Long> {


    @Query("SELECT * FROM mapping WHERE user_id = :value")
    Mono<Mapping>  findByUserId(String uid);
}
