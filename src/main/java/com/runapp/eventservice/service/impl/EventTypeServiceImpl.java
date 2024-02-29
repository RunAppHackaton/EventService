package com.runapp.eventservice.service.impl;

import java.util.List;

import com.runapp.eventservice.service.EventTypeService;
import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.model.EventType;
import com.runapp.eventservice.repository.EventTypeRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventTypeServiceImpl implements EventTypeService {
    private final EventTypeRepository eventTypeRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventTypeServiceImpl.class);

    @Override
    @Caching(put = {@CachePut(value = "eventTypes", key = "#eventType.id")},
            evict = {@CacheEvict(value = "eventTypes", allEntries = true)})
    public EventType add(EventType eventType) {
        LOGGER.info("EventType add: {}", eventType);
        return eventTypeRepository.save(eventType);
    }

    @Override
    @Cacheable(value = "eventTypes", key = "#id")
    public EventType getById(Long id) {
        LOGGER.info("EventType get by id: {}", id);
        return eventTypeRepository.findById(id).orElseThrow(() -> {
            throw new NoEntityFoundException("EventType with id: " + id + " doesn't exist");
        });
    }

    @Override
    @Cacheable("eventTypes")
    public List<EventType> getAll() {
        LOGGER.info("EventType get all");
        return eventTypeRepository.findAll();
    }

    @Override
    @CacheEvict(value = "eventTypes", key = "#id")
    public void deleteById(Long id) {
        LOGGER.info("EventType delete by id: {}", id);
        if (!eventTypeRepository.existsById(id)) {
            throw new NoEntityFoundException("EventType with id: " + id + " doesn't exist");
        }
        eventTypeRepository.deleteById(id);
    }

    @Override
    @Caching(put = {@CachePut(value = "eventTypes", key = "#eventType.id")},
            evict = {@CacheEvict(value = "eventTypes", allEntries = true)})
    public EventType update(EventType eventType) {
        LOGGER.info("EventType update: {}", eventType);
        if (!eventTypeRepository.existsById(eventType.getId())) {
            throw new NoEntityFoundException("EventType " + eventType + " doesn't exist");
        }
        return eventTypeRepository.save(eventType);
    }
}
