package hakaton.eventservice.repository;

import hakaton.eventservice.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {
}
