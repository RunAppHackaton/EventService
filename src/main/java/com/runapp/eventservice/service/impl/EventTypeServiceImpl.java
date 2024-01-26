package com.runapp.eventservice.service.impl;

import java.util.List;

import com.runapp.eventservice.service.EventTypeService;
import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.model.EventType;
import com.runapp.eventservice.repository.EventTypeRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventTypeServiceImpl implements EventTypeService {
    private final EventTypeRepository eventTypeRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventTypeServiceImpl.class);

    @Override
    public EventType add(EventType eventType) {
        LOGGER.info("EventType add: {}", eventType);
        return eventTypeRepository.save(eventType);
    }

    @Override
    public EventType getById(Long id) {
        LOGGER.info("EventType get by id: {}", id);
        return eventTypeRepository.findById(id).orElseThrow(() -> {
            throw new NoEntityFoundException("EventType with id: " + id + " doesn't exist");
        });
    }

    @Override
    public List<EventType> getAll() {
        LOGGER.info("EventType get all");
        return eventTypeRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("EventType delete by id: {}", id);
        if (!eventTypeRepository.existsById(id)) {
            throw new NoEntityFoundException("EventType with id: " + id + " doesn't exist");
        }
        eventTypeRepository.deleteById(id);
    }

    @Override
    public EventType update(EventType eventType) {
        LOGGER.info("EventType update: {}", eventType);
        if (!eventTypeRepository.existsById(eventType.getId())) {
            throw new NoEntityFoundException("EventType " + eventType + " doesn't exist");
        }
        return eventTypeRepository.save(eventType);
    }
}
