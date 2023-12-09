package com.runapp.eventservice.service.dto.mapper;

import com.runapp.eventservice.dto.request.EventStatusRequestDto;
import com.runapp.eventservice.dto.response.EventStatusResponseDto;
import com.runapp.eventservice.model.EventStatus;
import org.springframework.stereotype.Component;

@Component
public class EventStatusDtoMapper implements
        DtoMapper<EventStatus, EventStatusRequestDto, EventStatusResponseDto> {
    @Override
    public EventStatus toModel(EventStatusRequestDto dto) {
        EventStatus eventStatus = new EventStatus();
        eventStatus.setName(dto.getName());
        eventStatus.setDescription(dto.getDescription());
        return eventStatus;
    }

    @Override
    public EventStatusResponseDto toDto(EventStatus model) {
        EventStatusResponseDto dto = new EventStatusResponseDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        return dto;
    }
}
