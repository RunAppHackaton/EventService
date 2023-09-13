package hakaton.eventservice.service.dto.mapper;

import hakaton.eventservice.dto.request.ParticipationInEventsRequestDto;
import hakaton.eventservice.dto.response.ParticipationInEventsResponseDto;
import hakaton.eventservice.model.Event;
import hakaton.eventservice.model.ParticipationInEvents;
import org.springframework.stereotype.Component;

@Component
public class ParticipationInEventsDtoMapper implements
        DtoMapper<ParticipationInEvents,
                ParticipationInEventsRequestDto,
                ParticipationInEventsResponseDto> {
    @Override
    public ParticipationInEvents toModel(ParticipationInEventsRequestDto dto) {
        ParticipationInEvents participation = new ParticipationInEvents();
        participation.setEvent(new Event(dto.getEventId()));
        participation.setUserId(dto.getUserId());
        return participation;
    }

    @Override
    public ParticipationInEventsResponseDto toDto(ParticipationInEvents model) {
        ParticipationInEventsResponseDto dto = new ParticipationInEventsResponseDto();
        dto.setId(model.getId());
        dto.setEventId(model.getEvent().getId());
        dto.setUserId(model.getUserId());
        return dto;
    }
}
