package com.runapp.eventservice.repository;

import com.runapp.eventservice.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStatusRepository extends JpaRepository<EventStatus, Long> {
}
