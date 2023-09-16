package hakaton.eventservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrizeRequestDto {
    @NotEmpty(message = "can't be empty")
    private String name;
    @Min(value = 0, message = "can't store value less than 0")
    private int count;
    @NotNull(message = "can't be empty")
    @Min(value = 1)
    private Long eventId;
}
