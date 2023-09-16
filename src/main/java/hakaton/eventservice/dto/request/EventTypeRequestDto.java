package hakaton.eventservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventTypeRequestDto {
    @NotEmpty(message = "can't be empty")
    private String text;
    @NotEmpty(message = "can't be empty")
    private String eventTypeName;
}
