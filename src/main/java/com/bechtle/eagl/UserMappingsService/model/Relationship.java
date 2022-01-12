package com.bechtle.eagl.UserMappingsService.model;

import com.arangodb.springframework.annotation.ArangoId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
public class Relationship {

    @ArangoId
    @JsonIgnore
    private String id;

    @Id
    String relationshipId;

    String linkingCode;

    String login;

    @Singular
    List<String> peers;

    public String getAddress() {
        return this.peers.stream()
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Relation has no peer configured, address is available"));
    }
}
