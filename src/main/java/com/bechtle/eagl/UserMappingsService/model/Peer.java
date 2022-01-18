package com.bechtle.eagl.UserMappingsService.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a specific device
 */
@Data
@Builder
public class Peer {

    String peerId;
    String deviceId;

}
