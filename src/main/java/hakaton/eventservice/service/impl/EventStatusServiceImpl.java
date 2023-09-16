package hakaton.eventservice.service.impl;

import java.util.List;
import hakaton.eventservice.exception.NoEntityFoundException;
import hakaton.eventservice.model.EventStatus;
import hakaton.eventservice.repository.EventStatusRepository;
import hakaton.eventservice.service.EventStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventStatusServiceImpl implements EventStatusService {
    private final EventStatusRepository eventStatusRepository;

    @Override
    public EventStatus add(EventStatus eventStatus) {
        return eventStatusRepository.save(eventStatus);
    }

    @Override
    public EventStatus getById(Long id) {
        return eventStatusRepository.findById(id).orElseThrow(() -> {
            throw new NoEntityFoundException("EventStatus with id: " + id + " doesn't exist");
        });
    }

    @Override
    public List<EventStatus> getAll() {
        return eventStatusRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (!eventStatusRepository.existsById(id)) {
            throw new NoEntityFoundException("EventStatus with id: " + id + " doesn't exist");
        }
        eventStatusRepository.deleteById(id);
    }

    @Override
    public EventStatus update(EventStatus eventStatus) {
        if (!eventStatusRepository.existsById(eventStatus.getId())) {
            throw new NoEntityFoundException("EventStatus: " + eventStatus + " doesn't exist");
        }
        return eventStatusRepository.save(eventStatus);
    }
}
