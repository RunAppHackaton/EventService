package hakaton.eventservice.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventResponseDto {
    private Long id;
    private String name;
    private LocalDateTime date;
    private boolean adminPermission;
    private String eventImageUrl;
    private String eventWebsite;
    private String eventOrganizer;
    private String location;
    private LocalDateTime registrationDeadline;
    private int registrationFee;
    private Long EventTypeId;
    private Long eventStatusId;
}
