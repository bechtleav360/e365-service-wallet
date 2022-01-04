package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model;

import lombok.Data;

import java.util.List;

@Data
public class Result<T> {

    List<T> result;
}
