package com.bechtle.eagl.UserMappingsService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

public class Mapping {

    @Id
    @JsonIgnore
    private Long id;

    private final String email;
    private final String walletId;

    public Mapping(String email, String walletId) {
        this.email = email;
        this.walletId = walletId;
    }

    public String getEmail() {
        return email;
    }


    public String getWalletId() {
        return walletId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
