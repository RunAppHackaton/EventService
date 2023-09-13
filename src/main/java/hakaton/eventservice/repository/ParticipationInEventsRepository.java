package hakaton.eventservice.repository;

import hakaton.eventservice.model.ParticipationInEvents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationInEventsRepository
        extends JpaRepository<ParticipationInEvents, Long> {
}
