package com.runapp.eventservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipationInEventsResponseDto {
    private Long id;
    private Long eventId;
    private String userId;
}
