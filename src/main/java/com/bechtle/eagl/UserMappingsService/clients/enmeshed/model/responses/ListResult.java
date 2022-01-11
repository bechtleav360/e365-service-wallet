package com.bechtle.eagl.UserMappingsService.clients.enmeshed.model.responses;

import lombok.Data;

import java.util.List;

@Data
public class ListResult<T> {

    List<T> result;
}
