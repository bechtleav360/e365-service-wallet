package com.bechtle.eagl.UserMappingsService.model;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.bechtle.eagl.UserMappingsService.model.enums.UserFlags;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Document("users")
@Data
@Builder(toBuilder = true)
public class User {

    @ArangoId
    @JsonIgnore
    private String id;

    @Id
    private String login;

    private String relationshipId;

    @Singular
    @JsonIgnore
    private Set<UserFlags> flags;

}
