package com.runapp.eventservice.service;

import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.model.EventType;
import com.runapp.eventservice.repository.EventTypeRepository;
import com.runapp.eventservice.service.impl.EventTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventTypeServiceTest {

    @Mock
    private EventTypeRepository eventTypeRepository;

    @InjectMocks
    private EventTypeServiceImpl eventTypeService;

    @Test
    void testAddEventType() {
        EventType eventTypeToAdd = new EventType();
        when(eventTypeRepository.save(eventTypeToAdd)).thenReturn(eventTypeToAdd);

        EventType addedEventType = eventTypeService.add(eventTypeToAdd);

        assertEquals(eventTypeToAdd, addedEventType);
        verify(eventTypeRepository, times(1)).save(eventTypeToAdd);
    }

    @Test
    void testGetEventTypeById() {
        long id = 1L;
        EventType eventType = new EventType();
        when(eventTypeRepository.findById(id)).thenReturn(Optional.of(eventType));

        EventType retrievedEventType = eventTypeService.getById(id);

        assertEquals(eventType, retrievedEventType);
        verify(eventTypeRepository, times(1)).findById(id);
    }

    @Test
    void testGetEventTypeById_NotFound() {
        long id = 1L;
        when(eventTypeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoEntityFoundException.class, () -> eventTypeService.getById(id));

        verify(eventTypeRepository, times(1)).findById(id);
    }

    @Test
    void testGetAllEventTypes() {
        List<EventType> eventTypeList = new ArrayList<>();
        eventTypeList.add(new EventType());
        eventTypeList.add(new EventType());
        when(eventTypeRepository.findAll()).thenReturn(eventTypeList);

        List<EventType> retrievedEventTypes = eventTypeService.getAll();

        assertEquals(eventTypeList, retrievedEventTypes);
        verify(eventTypeRepository, times(1)).findAll();
    }

    @Test
    void testDeleteEventTypeById() {
        long id = 1L;
        when(eventTypeRepository.existsById(id)).thenReturn(true);

        eventTypeService.deleteById(id);

        verify(eventTypeRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteEventTypeById_NotFound() {
        long id = 1L;
        when(eventTypeRepository.existsById(id)).thenReturn(false);

        assertThrows(NoEntityFoundException.class, () -> eventTypeService.deleteById(id));

        verify(eventTypeRepository, times(1)).existsById(id);
        verifyNoMoreInteractions(eventTypeRepository);
    }

    @Test
    void testUpdateEventType() {
        EventType eventTypeToUpdate = new EventType();
        when(eventTypeRepository.existsById(eventTypeToUpdate.getId())).thenReturn(true);
        when(eventTypeRepository.save(eventTypeToUpdate)).thenReturn(eventTypeToUpdate);

        EventType updatedEventType = eventTypeService.update(eventTypeToUpdate);

        assertEquals(eventTypeToUpdate, updatedEventType);
        verify(eventTypeRepository, times(1)).save(eventTypeToUpdate);
    }

    @Test
    void testUpdateEventType_NotFound() {
        EventType eventTypeToUpdate = new EventType();
        when(eventTypeRepository.existsById(eventTypeToUpdate.getId())).thenReturn(false);

        assertThrows(NoEntityFoundException.class, () -> eventTypeService.update(eventTypeToUpdate));

        verify(eventTypeRepository, times(1)).existsById(eventTypeToUpdate.getId());
        verifyNoMoreInteractions(eventTypeRepository);
    }
}

