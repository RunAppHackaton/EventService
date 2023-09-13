package hakaton.eventservice.service.dto.mapper;

import hakaton.eventservice.dto.request.PrizeRequestDto;
import hakaton.eventservice.dto.response.PrizeResponseDto;
import hakaton.eventservice.model.Event;
import hakaton.eventservice.model.Prize;
import org.springframework.stereotype.Component;

@Component
public class PrizeDtoMapper implements DtoMapper<Prize, PrizeRequestDto, PrizeResponseDto> {
    @Override
    public Prize toModel(PrizeRequestDto dto) {
        Prize prize = new Prize();
        prize.setName(dto.getName());
        prize.setCount(dto.getCount());
        prize.setEvent(new Event(dto.getEventId()));
        return prize;
    }

    @Override
    public PrizeResponseDto toDto(Prize model) {
        PrizeResponseDto dto = new PrizeResponseDto();
        dto.setId(dto.getId());
        dto.setName(dto.getName());
        dto.setCount(dto.getCount());
        dto.setEventId(model.getEvent().getId());
        return dto;
    }
}
