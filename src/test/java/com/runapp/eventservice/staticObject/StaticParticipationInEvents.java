package com.runapp.eventservice.staticObject;


import com.runapp.eventservice.dto.request.ParticipationInEventsRequestDto;
import com.runapp.eventservice.dto.response.ParticipationInEventsResponseDto;
import com.runapp.eventservice.model.Event;
import com.runapp.eventservice.model.EventStatus;
import com.runapp.eventservice.model.ParticipationInEvents;
import org.joda.time.LocalDate;

import java.time.LocalDateTime;
import java.util.Date;

public class StaticParticipationInEvents {
    public static ParticipationInEventsRequestDto participationInEventsRequestDto1(){
        ParticipationInEventsRequestDto requestDto = new ParticipationInEventsRequestDto();
        requestDto.setEventId(1L);
        requestDto.setUserId("1");

        return requestDto;
    }

    public static ParticipationInEventsResponseDto participationInEventsResponseDto1(){
        ParticipationInEventsResponseDto responseDto = new ParticipationInEventsResponseDto();
        responseDto.setEventId(1L);
        responseDto.setUserId("1");
        responseDto.setId(1L);

        return responseDto;
    }

    public static ParticipationInEvents participationInEvents1(){
        Event event = new Event();
        event.setId(1L);
        event.setName("name");
        ParticipationInEvents participation = new ParticipationInEvents();
        participation.setUserId("1");
        participation.setId(1L);
        participation.setEvent(event);

        return participation;
    }
}
