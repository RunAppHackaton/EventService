package com.runapp.eventservice.service.impl;

import java.util.List;
import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.model.EventStatus;
import com.runapp.eventservice.repository.EventStatusRepository;
import com.runapp.eventservice.service.EventStatusService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventStatusServiceImpl implements EventStatusService {
    private final EventStatusRepository eventStatusRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventStatusServiceImpl.class);

    @Override
    public EventStatus add(EventStatus eventStatus) {
        LOGGER.info("EventStatus add: {}", eventStatus);
        return eventStatusRepository.save(eventStatus);
    }

    @Override
    public EventStatus getById(Long id) {
        LOGGER.info("EventStatus get by id: {}", id);
        return eventStatusRepository.findById(id).orElseThrow(() -> {
            throw new NoEntityFoundException("EventStatus with id: " + id + " doesn't exist");
        });
    }

    @Override
    public List<EventStatus> getAll() {
        LOGGER.info("EventStatus get all");
        return eventStatusRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("EventStatus delete by id: {}", id);
        if (!eventStatusRepository.existsById(id)) {
            throw new NoEntityFoundException("EventStatus with id: " + id + " doesn't exist");
        }
        eventStatusRepository.deleteById(id);
    }

    @Override
    public EventStatus update(EventStatus eventStatus) {
        LOGGER.info("EventStatus update: {}", eventStatus);
        if (!eventStatusRepository.existsById(eventStatus.getId())) {
            throw new NoEntityFoundException("EventStatus: " + eventStatus + " doesn't exist");
        }
        return eventStatusRepository.save(eventStatus);
    }
}
