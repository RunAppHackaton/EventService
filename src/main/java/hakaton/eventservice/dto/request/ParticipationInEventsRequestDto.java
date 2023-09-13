package hakaton.eventservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipationInEventsRequestDto {
    @NotNull
    private Long eventId;
    @NotNull
    private Long userId;
}
