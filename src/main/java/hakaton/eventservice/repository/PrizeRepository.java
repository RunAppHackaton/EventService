package hakaton.eventservice.repository;

import hakaton.eventservice.model.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrizeRepository extends JpaRepository<Prize, Long> {
}
