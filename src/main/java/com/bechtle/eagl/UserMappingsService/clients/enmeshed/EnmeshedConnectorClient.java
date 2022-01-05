package com.bechtle.eagl.UserMappingsService.clients.enmeshed;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.RelationshipTemplate;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.Result;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.requests.GenerateTokenRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class EnmeshedConnectorClient {
    @Value("${enmeshed.connector.url}")
    String url;

    @Value("${enmeshed.connector.apikey}")
    String apikey;

    private WebClient webClient;

    public EnmeshedConnectorClient() {
    }

    @PostConstruct
    public void initialize() {
        this.webClient = WebClient
                .builder()
                .baseUrl(url)
                .defaultHeader("X-API-KEY", apikey)
                .build();
    }


    public Flux<RelationshipTemplate> getRelationshipTemplates() {
        log.debug("Requesting RelationshipTemplate from Enmeshed Connector");

        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.pathSegment("RelationshipTemplates").build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Result<RelationshipTemplate>>() {})
                .flatMapMany(result -> Flux.fromIterable(result.getResult()));
    }

    public Mono<RelationshipTemplate> getRelationshipTemplate(String rltId) {
        return this.getRelationshipTemplates()
                .filterWhen(relationshipTemplate -> Mono.just(relationshipTemplate.getId().equalsIgnoreCase(rltId)))
                .next();
    }

    public Mono<byte[]> getTokenImage(String rltid) {
        GenerateTokenRequest request = GenerateTokenRequest.builder().expiresAt(Instant.now().truncatedTo(ChronoUnit.MILLIS).plus(730, ChronoUnit.DAYS).toString()).build();

        return this.webClient.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("RelationshipTemplates", "Own", rltid,"Token").build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.IMAGE_PNG)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(byte[].class);

    }



    public void sync() {



    }
}
