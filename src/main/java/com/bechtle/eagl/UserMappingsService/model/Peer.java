package com.bechtle.eagl.UserMappingsService.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
/**
 * Represents a specific device
 */
@Builder
public class Peer {

    String peerId;
    String deviceId;

}
