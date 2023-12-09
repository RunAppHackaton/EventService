package com.runapp.eventservice.service.dto.mapper;

import com.runapp.eventservice.dto.request.EventRequestDto;
import com.runapp.eventservice.dto.response.EventResponseDto;
import com.runapp.eventservice.model.Event;
import com.runapp.eventservice.model.EventStatus;
import com.runapp.eventservice.model.EventType;
import org.springframework.stereotype.Component;

@Component
public class EventDtoMapper implements
        DtoMapper<Event, EventRequestDto, EventResponseDto> {
    @Override
    public Event toModel(EventRequestDto dto) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setDate(dto.getDate());
        event.setAdminPermission(dto.isAdminPermission());
        event.setEventImageUrl("DEFAULT");
        event.setEventWebsite(dto.getEventWebsite());
        event.setEventOrganizer(dto.getEventOrganizer());
        event.setLocation(dto.getLocation());
        event.setRegistrationDeadline(dto.getRegistrationDeadline());
        event.setRegistrationFee(dto.getRegistrationFee());
        event.setEventType(new EventType(dto.getEventTypeId()));
        event.setEventStatus(new EventStatus(dto.getEventStatusId()));
        return event;
    }

    @Override
    public EventResponseDto toDto(Event model) {
        EventResponseDto dto = new EventResponseDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setDate(model.getDate());
        dto.setAdminPermission(model.isAdminPermission());
        dto.setEventImageUrl(model.getEventImageUrl());
        dto.setEventWebsite(model.getEventWebsite());
        dto.setEventOrganizer(model.getEventOrganizer());
        dto.setLocation(model.getLocation());
        dto.setRegistrationDeadline(model.getRegistrationDeadline());
        dto.setRegistrationFee(model.getRegistrationFee());
        dto.setEventTypeId(model.getEventType().getId());
        dto.setEventStatusId(model.getEventStatus().getId());
        return dto;
    }
}
