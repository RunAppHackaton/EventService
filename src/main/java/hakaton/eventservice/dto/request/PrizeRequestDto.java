package hakaton.eventservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrizeRequestDto {
    private String name;
    private int count;
    @NotNull
    private Long eventId;
}
