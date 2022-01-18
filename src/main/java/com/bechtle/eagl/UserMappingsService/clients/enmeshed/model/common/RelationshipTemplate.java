package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.common;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 *  {
 *       "id": "RLTwxQp024lESvah22ew",
 *       "isOwn": true,
 *       "createdBy": "id1MJD4bcpVXkPcXBr6bgv2Lq1sRo2vidAVc",
 *       "createdByDevice": "DVCvoN8iDoONnsMOSY5u",
 *       "createdAt": "2021-12-15T12:46:03.385Z",
 *       "content": {
 *         "attributes": [
 *           {
 *             "name": "Thing.name",
 *             "value": "Lernpfade im Web"
 *           }
 *         ],
 *         "request": {
 *           "required": [],
 *           "optional": [
 *             {
 *               "attribute": "Person.familyName"
 *             },
 *             {
 *               "attribute": "Person.givenName"
 *             }
 *           ]
 *         },
 *         "skills": []
 *       },
 *       "expiresAt": "2022-01-01T00:00:00.000Z",
 *       "maxNumberOfRelationships": 1
 *     }
 */
@Data
public class RelationshipTemplate {

    String id;
    int maxNumberOfRelationships;
    boolean isOwn;
    String createdBy;
    String createdByDevice;
    String createdAt;
    String expiresAt;

    JsonNode content;

}
