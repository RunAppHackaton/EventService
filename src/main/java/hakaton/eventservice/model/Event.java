package hakaton.eventservice.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "event_name")
    private String name;
    @Column(name = "event_date")
    private LocalDateTime date;
    @Column(name = "admin_permission")
    private boolean adminPermission;
    @Column(name = "event_image_url")
    private String eventImageUrl;
    @Column(name = "event_website")
    private String eventWebsite;
    @Column(name = "event_organizer")
    private String eventOrganizer;
    private String location;
    @Column(name = "registration_deadline")
    private LocalDateTime registrationDeadline;
    @Column(name = "registration_fee")
    private int registrationFee;
    @JoinColumn(name = "event_type_id")
    @ManyToOne
    private EventType eventType;
    @JoinColumn(name = "EventStatus_id")
    @ManyToOne
    private EventStatus eventStatus;

    public Event(Long id) {
        this.id = id;
    }
}
