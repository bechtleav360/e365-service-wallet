package com.bechtle.eagl.UserMappingsService.clients.enmeshed;

import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Message;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.MessageContent;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.Relationship;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common.RelationshipTemplate;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.requests.SendMessageRequest;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.responses.Result;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.requests.GenerateTokenRequest;
import com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.responses.SyncResult;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.concurrent.Flow;

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
        log.debug("Requesting new token from Enmeshed Connector for relationship template '{}'", rltid);
        GenerateTokenRequest request = GenerateTokenRequest.builder().expiresAt(Instant.now().truncatedTo(ChronoUnit.MILLIS).plus(730, ChronoUnit.DAYS).toString()).build();

        return this.webClient.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("RelationshipTemplates", "Own", rltid,"Token").build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.IMAGE_PNG)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(byte[].class);

    }

    public Mono<SyncResult> sync() {
        log.debug("Requesting sync updates from Enmeshed Connector");
        // http://eagl-enmeshed.germanywestcentral.azurecontainer.io/api/v1/Account/Sync
        return this.webClient.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("Account", "Sync").build())
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Result<SyncResult>>() {})
                .map(result -> (SyncResult) result.getResult());

    }

    public Mono<Relationship> acceptChange(String relationshipId, String changeId) {
        log.debug("Accepting a change update from Enmeshed Connector");

        return this.webClient.put()
                .uri(uriBuilder -> uriBuilder.pathSegment("Relationships", relationshipId, "Changes", changeId, "Accept").build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ObjectMapper().createObjectNode())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Result<Relationship>>() {})
                .map(result -> (Relationship) result.getResult());
    }

    public Mono<Message> sendMessage(SendMessageRequest request) {
        log.debug("Sending a message through Enmeshed Connector to recipient(s) '{}'", request.getRecipients());

        return this.webClient.put()
                .uri(uriBuilder -> uriBuilder.pathSegment("Messages").build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Result<Message>>() {})
                .map(result -> (Message) result.getResult());
    }
}
