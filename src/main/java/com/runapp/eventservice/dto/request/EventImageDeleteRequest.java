package com.runapp.eventservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventImageDeleteRequest {
    private String file_uri;
    private Long event_id;
}
