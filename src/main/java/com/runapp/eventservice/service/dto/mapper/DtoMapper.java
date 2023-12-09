package com.runapp.eventservice.service.dto.mapper;

public interface DtoMapper<M, R, S> {
    M toModel(R dto);

    S toDto(M model);
}
