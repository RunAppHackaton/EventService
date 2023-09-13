package hakaton.eventservice.service.impl;

import hakaton.eventservice.exception.NoEntityException;
import hakaton.eventservice.model.Event;
import hakaton.eventservice.repository.EventRepository;
import hakaton.eventservice.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Event add(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> {
            throw new NoEntityException("Event with id: " + id + " doesn't exist");
        });
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Event update(Event event) {
        if (!eventRepository.existsById(event.getId())) {
            throw new NoEntityException("Event: " + event + " doesn't exist");
        }
        return eventRepository.save(event);
    }
}
