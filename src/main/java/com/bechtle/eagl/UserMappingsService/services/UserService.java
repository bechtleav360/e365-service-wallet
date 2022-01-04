package com.bechtle.eagl.UserMappingsService.services;

import com.bechtle.eagl.UserMappingsService.model.*;
import com.bechtle.eagl.UserMappingsService.repositories.MappingRepository;
import com.bechtle.eagl.UserMappingsService.repositories.UserSkillsRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    final MappingRepository mappingRepository;
    final UserSkillsRepository userSkillsRepository;

    public UserService(
            @Autowired MappingRepository mappingRepository,
            @Autowired UserSkillsRepository userSkillsRepository) {

        this.mappingRepository = mappingRepository;
        this.userSkillsRepository = userSkillsRepository;
    }



    public Flux<Mapping> listAllUsers() {
        log.debug("Listing all users");
        return this.mappingRepository.findAll();
    }

    public Mono<Mapping> getUserMappingById(String userId) {
        log.debug("Get Mapping for user {}", userId);
        return this.mappingRepository.findByUserId(userId);
    }

    public Mono<Relation> getUserProfileById(String userId) {
        log.debug("Get User Profile for user {}", userId);
        Mono<Mapping> mappingMono = this.mappingRepository.findByUserId(userId)
                .switchIfEmpty(Mono.just(new Mapping(userId, "", "")));
        Mono<List<UserSkill>> skillsMono = this.userSkillsRepository.findByUserId(userId).collectList();

        return Mono.zip(mappingMono, skillsMono)
                .map(objects -> {
                    return new Relation(objects.getT1(), objects.getT2());
                });

    }

    public Flux<UserSkill> addSkillsToUser(String userId, Set<Skill> skills) {
        log.debug("Add skills {} to user {}", skills, userId);
        Flux<UserSkill> userSkillFlux = Flux.fromStream(skills.stream()
                .map(skill -> new UserSkill(userId, skill.getLabel(), Status.NEW)));
        return this.userSkillsRepository.saveAll(userSkillFlux);

    }

    public Mono<Void> deleteUser(String userId) {
        log.debug("Delete user {}", userId);
        Mono<Void> deleteMappingMono = this.mappingRepository.findByUserId(userId)
                .flatMap(this.mappingRepository::delete);
        Mono<Mono<Void>> deleteSkillsMono = this.userSkillsRepository.findByUserId(userId)
                .collectList()
                .map(this.userSkillsRepository::deleteAll);
        return Mono.zip(deleteMappingMono, deleteSkillsMono).thenEmpty(Mono.empty());

    }
}
