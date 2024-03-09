package com.runapp.eventservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipationInEventsRequestDto {
    @NotNull(message = "can't be empty")
    private Long eventId;
    @NotNull(message = "can't be empty")
    private String userId;
}
