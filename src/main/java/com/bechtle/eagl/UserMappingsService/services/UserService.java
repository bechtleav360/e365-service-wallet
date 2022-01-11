package com.bechtle.eagl.UserMappingsService.services;

import com.bechtle.eagl.UserMappingsService.model.User;
import com.bechtle.eagl.UserMappingsService.model.enums.UserFlags;
import com.bechtle.eagl.UserMappingsService.model.events.UserDeletedEvent;
import com.bechtle.eagl.UserMappingsService.model.events.UserFlaggedEvent;
import com.bechtle.eagl.UserMappingsService.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UserService(
            @Autowired UserRepository userRepository,
            @Autowired ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }


    public Flux<User> listAllUsers() {
        log.debug("Listing all users");
        return Flux.fromIterable(this.userRepository.findAll());
    }

    public Mono<User> getUser(String login) {
        log.debug("Get user with login '{}'", login);
        User example = User.builder().login(login).build();
        return this.userRepository.findById(login)
        //return this.userRepository.findOne(Example.of(example))
                .map(Mono::just)
                .orElseGet(Mono::empty);

    }

    public Mono<User> createUser(String login) {
        log.debug("Create user with login '{}'", login);
        User built = User.builder()
                .login(login)
                .build();
        try {

            User saved = this.userRepository.save(built);
            return Mono.just(saved);
        } catch (Exception e) {
            log.error("Failed to save user", e);
            return Mono.error(e);
        }


    }
/*
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
*/
    public Mono<Void> deleteUser(String login) {
        log.debug("Delete user with login '{}'", login);
        return this.getUser(login).flatMap(user -> {
            this.userRepository.delete(user);
            eventPublisher.publishEvent(new UserDeletedEvent(user));
            return Mono.empty();
        });


    }

    public Mono<User> flagUser(String login, UserFlags flag) {
        return this.getUser(login)
                .map(user -> user.toBuilder().flag(flag).build())
                .map(this.userRepository::save)
                .map(user -> {
                    eventPublisher.publishEvent(new UserFlaggedEvent(user, flag));
                    return user;
                });


    }
}
