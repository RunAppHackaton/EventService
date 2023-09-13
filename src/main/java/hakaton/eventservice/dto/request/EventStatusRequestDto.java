package hakaton.eventservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventStatusRequestDto {
    private String name;
    private String description;
}
