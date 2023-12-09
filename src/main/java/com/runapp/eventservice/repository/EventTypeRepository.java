package com.runapp.eventservice.repository;

import com.runapp.eventservice.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {
}
