package com.runapp.eventservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.runapp.eventservice.dto.request.EventStatusRequestDto;
import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.dto.response.EventStatusResponseDto;
import com.runapp.eventservice.exception.GlobalExceptionHandler;
import com.runapp.eventservice.model.EventStatus;
import com.runapp.eventservice.service.EventStatusService;
import com.runapp.eventservice.service.dto.mapper.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventStatusControllerTest {

    @Mock
    private EventStatusService eventStatusService;

    @Mock
    private DtoMapper<EventStatus, EventStatusRequestDto, EventStatusResponseDto> eventStatusDtoMapper;

    @InjectMocks
    private EventStatusController eventStatusController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventStatusController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testAddEventStatus() {
        EventStatusRequestDto requestDto = new EventStatusRequestDto("name", "description");
        EventStatusResponseDto responseDto = new EventStatusResponseDto();
        EventStatus eventStatus = new EventStatus();
        when(eventStatusDtoMapper.toModel(requestDto)).thenReturn(eventStatus);
        when(eventStatusService.add(eventStatus)).thenReturn(eventStatus);
        when(eventStatusDtoMapper.toDto(eventStatus)).thenReturn(responseDto);

        ResponseEntity<EventStatusResponseDto> response = eventStatusController.add(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(eventStatusService, times(1)).add(any(EventStatus.class)); // Verify that the update method is called
    }

    @Test
    void testGetEventStatusById() throws Exception {
        Long id = 1L;
        EventStatusResponseDto responseDto = new EventStatusResponseDto();
        EventStatus eventStatus = new EventStatus();
        when(eventStatusService.getById(id)).thenReturn(eventStatus);
        when(eventStatusDtoMapper.toDto(eventStatus)).thenReturn(responseDto);

        mockMvc.perform(get("/event-statuses/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(responseDto.getId())); // Assuming field1 exists in the response DTO

        verify(eventStatusService, times(1)).getById(id);
    }

    @Test
    void testGetAllEventStatuses() throws Exception {
        List<EventStatusResponseDto> responseDtos = List.of(new EventStatusResponseDto(), new EventStatusResponseDto());
        when(eventStatusService.getAll()).thenReturn(List.of(new EventStatus(), new EventStatus()));
        when(eventStatusDtoMapper.toDto(any(EventStatus.class))).thenReturn(responseDtos.get(0), responseDtos.get(1));

        mockMvc.perform(get("/event-statuses/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(responseDtos.get(0).getId()));

        verify(eventStatusService, times(1)).getAll();
    }

    @Test
    void testUpdateEventStatus() throws Exception {
        Long id = 1L;
        EventStatusRequestDto requestDto = new EventStatusRequestDto("name", "description");

        // Mock the behavior of toModel and toDto methods
        EventStatus eventStatus = new EventStatus(1L, "name", "description");
        EventStatusResponseDto eventStatusResponse = new EventStatusResponseDto();
        eventStatusResponse.setId(1L);
        eventStatusResponse.setName("name");
        eventStatusResponse.setDescription("description");

        when(eventStatusDtoMapper.toModel(any())).thenReturn(eventStatus);
        when(eventStatusService.update(any(EventStatus.class))).thenReturn(eventStatus); // Return the updated EventStatus object
        when(eventStatusDtoMapper.toDto(eventStatus)).thenReturn(eventStatusResponse); // Map the updated EventStatus to response DTO

        ResponseEntity<EventStatusResponseDto> response = eventStatusController.put(1L,requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventStatusResponse, response.getBody());
        verify(eventStatusService, times(1)).update(any(EventStatus.class)); // Verify that the update method is called
    }

    @Test
    void testDeleteEventStatus() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/event-statuses/{id}", id))
                .andExpect(status().isNoContent());

        verify(eventStatusService, times(1)).deleteById(id);
    }

    @Test
    void testHandleNoEntityFoundException() throws Exception {
        Long id = 1L;
        when(eventStatusService.getById(id)).thenThrow(new NoEntityFoundException("EventStatus with id: " + id + " doesn't exist"));

        mockMvc.perform(get("/event-statuses/{id}", id))
                .andExpect(status().isNotFound());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
