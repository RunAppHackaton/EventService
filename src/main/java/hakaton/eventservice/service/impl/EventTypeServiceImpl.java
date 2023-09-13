package hakaton.eventservice.service.impl;

import hakaton.eventservice.exception.NoEntityException;
import hakaton.eventservice.model.EventType;
import hakaton.eventservice.repository.EventTypeRepository;
import hakaton.eventservice.service.EventTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventTypeServiceImpl implements EventTypeService {
    private final EventTypeRepository eventTypeRepository;

    @Override
    public EventType add(EventType eventType) {
        return eventTypeRepository.save(eventType);
    }

    @Override
    public EventType getById(Long id) {
        return eventTypeRepository.findById(id).orElseThrow(() -> {
            throw new NoEntityException("EventType with id: " + id + " doesn't exist");
        });
    }

    @Override
    public List<EventType> getAll() {
        return eventTypeRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        eventTypeRepository.deleteById(id);
    }

    @Override
    public EventType update(EventType eventType) {
        if (!eventTypeRepository.existsById(eventType.getId())) {
            throw new NoEntityException("EventType " + eventType + " doesn't exist");
        }
        return eventTypeRepository.save(eventType);
    }
}
