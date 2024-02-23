package com.runapp.eventservice.service;


import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.model.EventStatus;
import com.runapp.eventservice.repository.EventStatusRepository;
import com.runapp.eventservice.service.impl.EventStatusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventStatusServiceTest {

    @Mock
    private EventStatusRepository eventStatusRepository;

    @InjectMocks
    private EventStatusServiceImpl eventStatusService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEventStatus() {
        EventStatus eventStatus = new EventStatus();
        when(eventStatusRepository.save(eventStatus)).thenReturn(eventStatus);

        EventStatus savedEventStatus = eventStatusService.add(eventStatus);

        assertNotNull(savedEventStatus);
        assertEquals(eventStatus, savedEventStatus);
        verify(eventStatusRepository, times(1)).save(eventStatus);
    }

    @Test
    void testGetEventStatusById_ExistingEventStatus() {
        Long id = 1L;
        EventStatus eventStatus = new EventStatus();
        when(eventStatusRepository.findById(id)).thenReturn(Optional.of(eventStatus));

        EventStatus retrievedEventStatus = eventStatusService.getById(id);

        assertNotNull(retrievedEventStatus);
        assertEquals(eventStatus, retrievedEventStatus);
        verify(eventStatusRepository, times(1)).findById(id);
    }

    @Test
    void testGetEventStatusById_NonExistingEventStatus() {
        Long id = 1L;
        when(eventStatusRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoEntityFoundException.class, () -> eventStatusService.getById(id));
        verify(eventStatusRepository, times(1)).findById(id);
    }

    @Test
    void testGetAllEventStatuses() {
        List<EventStatus> eventStatuses = List.of(new EventStatus(), new EventStatus());
        when(eventStatusRepository.findAll()).thenReturn(eventStatuses);

        List<EventStatus> retrievedEventStatuses = eventStatusService.getAll();

        assertNotNull(retrievedEventStatuses);
        assertEquals(eventStatuses.size(), retrievedEventStatuses.size());
        verify(eventStatusRepository, times(1)).findAll();
    }

    @Test
    void testDeleteEventStatusById_ExistingEventStatus() {
        Long id = 1L;
        when(eventStatusRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> eventStatusService.deleteById(id));
        verify(eventStatusRepository, times(1)).existsById(id);
        verify(eventStatusRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteEventStatusById_NonExistingEventStatus() {
        Long id = 1L;
        when(eventStatusRepository.existsById(id)).thenReturn(false);

        assertThrows(NoEntityFoundException.class, () -> eventStatusService.deleteById(id));
        verify(eventStatusRepository, times(1)).existsById(id);
        verify(eventStatusRepository, never()).deleteById(id);
    }

    @Test
    void testUpdateEventStatus_ExistingEventStatus() {
        EventStatus eventStatus = new EventStatus();
        eventStatus.setId(1L);
        when(eventStatusRepository.existsById(eventStatus.getId())).thenReturn(true);
        when(eventStatusRepository.save(eventStatus)).thenReturn(eventStatus);

        EventStatus updatedEventStatus = eventStatusService.update(eventStatus);

        assertNotNull(updatedEventStatus);
        assertEquals(eventStatus, updatedEventStatus);
        verify(eventStatusRepository, times(1)).existsById(eventStatus.getId());
        verify(eventStatusRepository, times(1)).save(eventStatus);
    }

    @Test
    void testUpdateEventStatus_NonExistingEventStatus() {
        EventStatus eventStatus = new EventStatus();
        eventStatus.setId(1L);
        when(eventStatusRepository.existsById(eventStatus.getId())).thenReturn(false);

        assertThrows(NoEntityFoundException.class, () -> eventStatusService.update(eventStatus));
        verify(eventStatusRepository, times(1)).existsById(eventStatus.getId());
        verify(eventStatusRepository, never()).save(eventStatus);
    }
}

