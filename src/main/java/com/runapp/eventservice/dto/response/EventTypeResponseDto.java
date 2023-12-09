package com.runapp.eventservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventTypeResponseDto {
    private Long id;
    private String text;
    private String eventTypeName;
}
