package com.runapp.eventservice.repository;

import com.runapp.eventservice.model.ParticipationInEvents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationInEventsRepository
        extends JpaRepository<ParticipationInEvents, Long> {
}
