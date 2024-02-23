package com.runapp.eventservice.service;

import com.runapp.eventservice.dto.request.DeleteStorageRequest;
import com.runapp.eventservice.dto.request.EventImageDeleteRequest;
import com.runapp.eventservice.dto.response.StorageServiceResponse;
import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.feignClient.StorageServiceClient;
import com.runapp.eventservice.model.Event;
import com.runapp.eventservice.repository.EventRepository;
import com.runapp.eventservice.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private StorageServiceClient storageServiceClient;

    @InjectMocks
    private EventServiceImpl eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEvent() {
        Event event = new Event();
        when(eventRepository.save(event)).thenReturn(event);

        Event savedEvent = eventService.add(event);

        assertNotNull(savedEvent);
        assertEquals(event, savedEvent);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testGetEventById_ExistingEvent() {
        Long id = 1L;
        Event event = new Event();
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        Event retrievedEvent = eventService.getById(id);

        assertNotNull(retrievedEvent);
        assertEquals(event, retrievedEvent);
        verify(eventRepository, times(1)).findById(id);
    }

    @Test
    void testGetEventById_NonExistingEvent() {
        Long id = 1L;
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoEntityFoundException.class, () -> eventService.getById(id));
        verify(eventRepository, times(1)).findById(id);
    }

    @Test
    void testGetAllEvents() {
        List<Event> events = List.of(new Event(), new Event());
        when(eventRepository.findAll()).thenReturn(events);

        List<Event> retrievedEvents = eventService.getAll();

        assertNotNull(retrievedEvents);
        assertEquals(events.size(), retrievedEvents.size());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void testDeleteEventById_ExistingEvent() {
        Long id = 1L;
        when(eventRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> eventService.deleteById(id));
        verify(eventRepository, times(1)).existsById(id);
        verify(eventRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteEventById_NonExistingEvent() {
        Long id = 1L;
        when(eventRepository.existsById(id)).thenReturn(false);

        assertThrows(NoEntityFoundException.class, () -> eventService.deleteById(id));
        verify(eventRepository, times(1)).existsById(id);
        verify(eventRepository, never()).deleteById(id);
    }

    @Test
    void testUpdateEvent_ExistingEvent() {
        Event event = new Event();
        event.setId(1L);
        when(eventRepository.existsById(event.getId())).thenReturn(true);
        when(eventRepository.save(event)).thenReturn(event);

        Event updatedEvent = eventService.update(event);

        assertNotNull(updatedEvent);
        assertEquals(event, updatedEvent);
        verify(eventRepository, times(1)).existsById(event.getId());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testUpdateEvent_NonExistingEvent() {
        Event event = new Event();
        event.setId(1L);
        when(eventRepository.existsById(event.getId())).thenReturn(false);

        assertThrows(NoEntityFoundException.class, () -> eventService.update(event));
        verify(eventRepository, times(1)).existsById(event.getId());
        verify(eventRepository, never()).save(event);
    }

    // Testing the uploadEventImage method
    @Test
    void testUploadEventImage() {
        Long eventId = 1L;
        String storageDirectory = "/uploads";
        MultipartFile file = null; // You can initialize a mock MultipartFile if needed
        StorageServiceResponse storageServiceResponse = new StorageServiceResponse();
        storageServiceResponse.setFile_uri("https://example.com/image.jpg");

        Event event = new Event();
        event.setId(eventId);

        when(storageServiceClient.uploadFile(file, storageDirectory)).thenReturn(storageServiceResponse);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventRepository.save(event)).thenReturn(event);

        Event result = eventService.uploadEventImage(eventId, file, storageDirectory);

        assertNotNull(result);
        assertEquals(storageServiceResponse.getFile_uri(), result.getEventImageUrl());
        verify(storageServiceClient, times(1)).uploadFile(file, storageDirectory);
        verify(eventRepository, times(1)).findById(eventId);
        verify(eventRepository, times(1)).save(event);
    }

    // Testing the deleteEventImage method
    @Test
    void testDeleteEventImage_Success() {
        Long eventId = 1L;
        String storageDirectory = "/uploads";
        EventImageDeleteRequest eventImageDeleteRequest = new EventImageDeleteRequest("https://example.com/image.jpg",eventId);
        Event event = new Event();
        event.setId(eventId);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(storageServiceClient.deleteFile(any(DeleteStorageRequest.class))).thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(204)));

        ResponseEntity<Object> response = eventService.deleteEventImage(eventImageDeleteRequest, storageDirectory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(eventRepository, times(1)).findById(eventId);
        verify(storageServiceClient, times(1)).deleteFile(any(DeleteStorageRequest.class));
        verify(eventRepository, times(1)).save(event);
    }
}
