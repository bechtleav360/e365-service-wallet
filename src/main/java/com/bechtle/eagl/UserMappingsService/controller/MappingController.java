package com.bechtle.eagl.UserMappingsService.controller;

import com.bechtle.eagl.UserMappingsService.model.Mapping;
import com.bechtle.eagl.UserMappingsService.repositories.MappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/mappings")
public class MappingController {


    private final MappingRepository mappingRepository;

    public MappingController(@Autowired MappingRepository mappingRepository) {
        this.mappingRepository = mappingRepository;
    }


    @GetMapping("")
    public Flux<Mapping> list()  {
        return this.mappingRepository.findAll();
    }

    @GetMapping("/{email}")
    public Mono<Mapping> get(@PathVariable String email) {
        return this.mappingRepository.findByEMail(email);
    }

    @PostMapping("")
    public Mono<Mapping> create(@RequestBody Mapping mapping) {
        return this.mappingRepository.save(mapping);
    }

    @DeleteMapping("/{email}")
    public Mono<Void> delete(@PathVariable String email) {
        return this.mappingRepository.findByEMail(email)
                .flatMap(mapping -> this.mappingRepository.deleteById(mapping.getId()));

    }


    @PutMapping("/{email}")
    public Mono<Mapping> replace(@PathVariable String email, @RequestBody Mapping mapping) {
        if(! email.equalsIgnoreCase(mapping.getEmail()))
            return Mono.error(new IllegalArgumentException("E-Mail in path does not match E-Mail in request body"));

        return this.mappingRepository.findByEMail(mapping.getEmail())
                .map(mappingWithId -> this.mappingRepository.deleteById(mappingWithId.getId()))
                .then(this.mappingRepository.save(mapping));

    }
}
