package com.runapp.eventservice.service.dto.mapper;

import com.runapp.eventservice.dto.request.PrizeRequestDto;
import com.runapp.eventservice.dto.response.PrizeResponseDto;
import com.runapp.eventservice.model.Event;
import com.runapp.eventservice.model.Prize;
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
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setCount(model.getCount());
        dto.setEventId(model.getEvent().getId());
        return dto;
    }
}
