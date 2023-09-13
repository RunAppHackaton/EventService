package hakaton.eventservice.service.dto.mapper;

import hakaton.eventservice.dto.request.EventStatusRequestDto;
import hakaton.eventservice.dto.response.EventStatusResponseDto;
import hakaton.eventservice.model.EventStatus;
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
