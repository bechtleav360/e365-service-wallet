package com.bechtle.eagl.UserMappingsService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Mapping {

    @Id
    @JsonIgnore
    private Long id;

    private final String user_id;
    private final String wallet_id;
    private final String device_id;

    public Mapping(String uid, String wid, String deviceId) {
        this.user_id = uid;
        this.wallet_id = wid;
        this.device_id = deviceId;
    }

}
