package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.responses;

import lombok.Data;

@Data
public class Result<T> {

    T result;
}
