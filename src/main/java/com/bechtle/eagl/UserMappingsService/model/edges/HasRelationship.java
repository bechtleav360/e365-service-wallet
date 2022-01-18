package com.bechtle.eagl.UserMappingsService.model.edges;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.bechtle.eagl.UserMappingsService.model.Relationship;
import com.bechtle.eagl.UserMappingsService.model.User;
import lombok.Builder;
import lombok.Data;

@Edge("has_relationship")
@Data
@Builder
public class HasRelationship {

    @From
    private User user;

    @To
    private Relationship relationship;

}
