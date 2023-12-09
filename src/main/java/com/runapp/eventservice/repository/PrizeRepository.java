package com.runapp.eventservice.repository;

import com.runapp.eventservice.model.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrizeRepository extends JpaRepository<Prize, Long> {
}
