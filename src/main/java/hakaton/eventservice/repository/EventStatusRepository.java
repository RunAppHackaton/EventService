package hakaton.eventservice.repository;

import hakaton.eventservice.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStatusRepository extends JpaRepository<EventStatus, Long> {
}
