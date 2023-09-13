package hakaton.eventservice.service.dto.mapper;

import hakaton.eventservice.dto.request.EventTypeRequestDto;
import hakaton.eventservice.dto.response.EventTypeResponseDto;
import hakaton.eventservice.model.EventType;
import org.springframework.stereotype.Component;

@Component
public class EventTypeDtoMapper implements
        DtoMapper<EventType, EventTypeRequestDto, EventTypeResponseDto> {
    @Override
    public EventType toModel(EventTypeRequestDto dto) {
        EventType eventType = new EventType();
        eventType.setText(dto.getText());
        eventType.setEventTypeName(dto.getEventTypeName());
        return eventType;
    }

    @Override
    public EventTypeResponseDto toDto(EventType model) {
        EventTypeResponseDto dto = new EventTypeResponseDto();
        dto.setId(model.getId());
        dto.setText(model.getText());
        dto.setEventTypeName(model.getEventTypeName());
        return dto;
    }
}
