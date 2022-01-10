package com.bechtle.eagl.UserMappingsService.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;
import java.util.Optional;

@Data
@Builder
public class Relation {

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
