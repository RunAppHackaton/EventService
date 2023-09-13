package hakaton.eventservice.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "participation_in_events")
public class ParticipationInEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "Event_id")
    @ManyToOne
    private Event event;
    @JoinColumn(name = "User_id")
    private Long userId;
}
