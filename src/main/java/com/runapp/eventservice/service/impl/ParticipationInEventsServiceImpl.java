package com.runapp.eventservice.service.impl;

import java.util.List;

import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.repository.ParticipationInEventsRepository;
import com.runapp.eventservice.model.ParticipationInEvents;
import com.runapp.eventservice.service.ParticipationInEventsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ParticipationInEventsServiceImpl implements ParticipationInEventsService {
    private final ParticipationInEventsRepository participationInEventsRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipationInEventsServiceImpl.class);

    @Override
    public ParticipationInEvents add(ParticipationInEvents participationInEvents) {
        LOGGER.info("ParticipationInEvents add: {}", participationInEvents);
        return participationInEventsRepository.save(participationInEvents);
    }

    @Override
    public ParticipationInEvents getById(Long id) {
        LOGGER.info("ParticipationInEvents get by id: {}", id);
        return participationInEventsRepository.findById(id).orElseThrow(() -> {
            throw new NoEntityFoundException("ParticipationInEvents with id: " + id + " doesn't exist");
        });
    }

    @Override
    public List<ParticipationInEvents> getAll() {
        LOGGER.info("ParticipationInEvents get all");
        return participationInEventsRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("ParticipationInEvents delete by id: {}", id);
        if (!participationInEventsRepository.existsById(id)){
            participationInEventsRepository.deleteById(id);
        }
        throw new NoEntityFoundException("ParticipationInEvents with id: " + id + " doesn't exist");
    }

    @Override
    public ParticipationInEvents update(ParticipationInEvents participationInEvents) {
        LOGGER.info("ParticipationInEvents update: {}", participationInEvents);
        if (!participationInEventsRepository.existsById(participationInEvents.getId())) {
            throw new NoEntityFoundException("ParticipationInEvents " + participationInEvents + " doesn't exist");
        }
        return participationInEventsRepository.save(participationInEvents);
    }
}
