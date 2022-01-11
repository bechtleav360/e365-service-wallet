package com.bechtle.eagl.UserMappingsService.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {

    @JsonProperty("new")
    NEW,
    @JsonProperty("learning")
    LEARNING,
    @JsonProperty("completed")
    COMPLETED

}
