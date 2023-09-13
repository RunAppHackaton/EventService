package hakaton.eventservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventStatusResponseDto {
    private Long id;
    private String name;
    private String description;
}
