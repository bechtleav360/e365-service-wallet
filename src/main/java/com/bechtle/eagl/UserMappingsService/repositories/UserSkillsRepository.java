package com.bechtle.eagl.UserMappingsService.repositories;

import com.bechtle.eagl.UserMappingsService.model.Mapping;
import com.bechtle.eagl.UserMappingsService.model.UserSkill;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
 * Mapping from user id to skills and status
 *
 * Note: for production, we should build a clean db with primary keys etc.
 */
public interface UserSkillsRepository extends ReactiveCrudRepository<UserSkill, Long> {


    @Query("SELECT * FROM user_skill WHERE user_id = :value")
    Flux<UserSkill> findByUserId(String uid);
}
