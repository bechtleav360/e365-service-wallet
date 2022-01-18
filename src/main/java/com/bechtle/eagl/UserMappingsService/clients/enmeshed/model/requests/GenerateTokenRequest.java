package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.requests;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class GenerateTokenRequest {
    String expiresAt;
    boolean ephemeral = true;

}
