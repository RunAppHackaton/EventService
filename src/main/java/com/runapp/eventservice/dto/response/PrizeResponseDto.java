package com.runapp.eventservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrizeResponseDto {
    private Long id;
    private String name;
    private int count;
    private Long eventId;
}
