package com.runapp.eventservice.dto.request;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventRequestDto {
    private String name;
    private LocalDateTime date;
    private boolean adminPermission;
    private String eventImageUrl;
    private String eventWebsite;
    private String eventOrganizer;
    private String location;
    @Future
    private LocalDateTime registrationDeadline;
    @PositiveOrZero
    private int registrationFee;
    private Long eventTypeId;
    private Long eventStatusId;
}
