package hakaton.eventservice.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "event_status")
public class EventStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "event_status_name")
    private String name;
    private String description;

    public EventStatus(Long id) {
        this.id = id;
    }
}
