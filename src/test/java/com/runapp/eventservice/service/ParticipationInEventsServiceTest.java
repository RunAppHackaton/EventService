package com.runapp.eventservice.service;

import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.model.ParticipationInEvents;
import com.runapp.eventservice.repository.ParticipationInEventsRepository;
import com.runapp.eventservice.service.impl.ParticipationInEventsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipationInEventsServiceTest {

    @Mock
    private ParticipationInEventsRepository participationInEventsRepository;

    @InjectMocks
    private ParticipationInEventsServiceImpl participationInEventsService;

    @Test
    void testAddParticipationInEvents() {
        // Create a participationInEvents object
        ParticipationInEvents participationInEvents = new ParticipationInEvents();

        // Stub the repository method
        when(participationInEventsRepository.save(participationInEvents)).thenReturn(participationInEvents);

        // Call the service method
        ParticipationInEvents result = participationInEventsService.add(participationInEvents);

        // Verify the result
        assertNotNull(result);
        assertEquals(participationInEvents, result);

        // Verify that the save method of the repository was called once
        verify(participationInEventsRepository, times(1)).save(participationInEvents);
    }

    @Test
    void testGetParticipationInEventsById() {
        // Mock the id
        Long id = 1L;

        // Create a participationInEvents object
        ParticipationInEvents participationInEvents = new ParticipationInEvents();
        participationInEvents.setId(id);

        // Stub the repository method
        when(participationInEventsRepository.findById(id)).thenReturn(Optional.of(participationInEvents));

        // Call the service method
        ParticipationInEvents result = participationInEventsService.getById(id);

        // Verify the result
        assertNotNull(result);
        assertEquals(participationInEvents, result);

        // Verify that the findById method of the repository was called once with the specified id
        verify(participationInEventsRepository, times(1)).findById(id);
    }

    @Test
    void testGetAllParticipationInEvents() {
        // Create a list of participationInEvents
        List<ParticipationInEvents> participationInEventsList = new ArrayList<>();
        participationInEventsList.add(new ParticipationInEvents());
        participationInEventsList.add(new ParticipationInEvents());

        // Stub the repository method
        when(participationInEventsRepository.findAll()).thenReturn(participationInEventsList);

        // Call the service method
        List<ParticipationInEvents> result = participationInEventsService.getAll();

        // Verify the result
        assertNotNull(result);
        assertEquals(participationInEventsList.size(), result.size());

        // Verify that the findAll method of the repository was called once
        verify(participationInEventsRepository, times(1)).findAll();
    }

    @Test
    void testDeleteParticipationInEventsById() {
        // Mock the id
        Long id = 1L;

        when(participationInEventsRepository.existsById(any())).thenReturn(false);

        assertThrows(NoEntityFoundException.class, () -> participationInEventsService.deleteById(id));
    }

    @Test
    void testUpdateParticipationInEvents() {
        // Create a participationInEvents object
        ParticipationInEvents participationInEvents = new ParticipationInEvents();
        participationInEvents.setId(1L);

        // Stub the repository method
        when(participationInEventsRepository.existsById(participationInEvents.getId())).thenReturn(true);
        when(participationInEventsRepository.save(participationInEvents)).thenReturn(participationInEvents);

        // Call the service method
        ParticipationInEvents result = participationInEventsService.update(participationInEvents);

        // Verify the result
        assertNotNull(result);
        assertEquals(participationInEvents, result);

        // Verify that the save method of the repository was called once
        verify(participationInEventsRepository, times(1)).save(participationInEvents);
    }
}

