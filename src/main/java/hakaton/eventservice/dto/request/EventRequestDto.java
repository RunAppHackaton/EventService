package hakaton.eventservice.dto.request;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventRequestDto {
    @NotEmpty(message = "can't be empty")
    private String name;
    @NotNull(message = "can't be empty")
    private LocalDateTime date;
    private boolean adminPermission;
    @NotEmpty(message = "can't be empty")
    private String eventImageUrl;
    @NotEmpty(message = "can't be empty")
    private String eventWebsite;
    @NotEmpty(message = "can't be empty")
    private String eventOrganizer;
    @NotEmpty(message = "can't be empty")
    private String location;
    @NotNull(message = "can't be empty")
    private LocalDateTime registrationDeadline;
    @PositiveOrZero(message = "can't be negative")
    private int registrationFee;
    @NotNull(message = "can't be empty")
    private Long eventTypeId;
    @NotNull(message = "can't be empty")
    private Long eventStatusId;
}
