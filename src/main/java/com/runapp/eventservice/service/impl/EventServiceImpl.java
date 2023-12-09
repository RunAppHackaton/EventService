package com.runapp.eventservice.service.impl;

import java.util.List;

import feign.FeignException;
import com.runapp.eventservice.dto.request.DeleteStorageRequest;
import com.runapp.eventservice.dto.request.EventImageDeleteRequest;
import com.runapp.eventservice.dto.response.DeleteResponse;
import com.runapp.eventservice.dto.response.StorageServiceResponse;
import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.feignClient.StorageServiceClient;
import com.runapp.eventservice.model.Event;
import com.runapp.eventservice.repository.EventRepository;
import com.runapp.eventservice.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final StorageServiceClient storageServiceClient;

    @Override
    public Event add(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> {
            throw new NoEntityFoundException("Event with id: " + id + " doesn't exist");
        });
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (eventRepository.existsById(id)) {
            throw new NoEntityFoundException("Event with id: " + id + " doesn't exist");
        }
        eventRepository.deleteById(id);
    }

    @Override
    public Event update(Event event) {
        if (!eventRepository.existsById(event.getId())) {
            throw new NoEntityFoundException("Event: " + event + " doesn't exist");
        }
        return eventRepository.save(event);
    }

    public Event uploadEventImage(Long eventId, MultipartFile file, String storageDirectory) {
        StorageServiceResponse storageServiceResponse = storageServiceClient.uploadFile(file, storageDirectory);
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NoEntityFoundException("Event with id: " + eventId + " doesn't exist"));
        event.setEventImageUrl(storageServiceResponse.getFile_uri());
        eventRepository.save(event);
        return event;
    }

    public ResponseEntity<Object> deleteEventImage(EventImageDeleteRequest eventImageDeleteRequest, String storageDirectory) {
        Event event = eventRepository.findById(eventImageDeleteRequest.getEvent_id()).orElseThrow(
                () -> new NoEntityFoundException("Event with id: " + eventImageDeleteRequest.getEvent_id() + " doesn't exist"));
        event.setEventImageUrl("DEFAULT");
        try {
            storageServiceClient.deleteFile(new DeleteStorageRequest(eventImageDeleteRequest.getFile_uri(), storageDirectory));
            eventRepository.save(event);
            return ResponseEntity.ok().build();
        } catch (FeignException.InternalServerError e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DeleteResponse("the image does not exist or the data was transferred incorrectly"));
        }
    }
}
