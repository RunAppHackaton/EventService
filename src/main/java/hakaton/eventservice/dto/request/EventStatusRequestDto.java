package hakaton.eventservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventStatusRequestDto {
    @NotEmpty(message = "can't be empty")
    private String name;
    @Size(max = 200, message = "can't exceed length of 200 characters")
    private String description;
}
