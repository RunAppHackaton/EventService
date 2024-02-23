package com.runapp.eventservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runapp.eventservice.dto.request.EventTypeRequestDto;
import com.runapp.eventservice.dto.response.EventStatusResponseDto;
import com.runapp.eventservice.dto.response.EventTypeResponseDto;
import com.runapp.eventservice.exception.GlobalExceptionHandler;
import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.model.EventType;
import com.runapp.eventservice.service.EventTypeService;
import com.runapp.eventservice.service.dto.mapper.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EventTypeControllerTest {

    @Mock
    private EventTypeService eventTypeService;

    @Mock
    private DtoMapper<EventType, EventTypeRequestDto, EventTypeResponseDto> eventTypeDtoMapper;

    @InjectMocks
    private EventTypeController eventTypeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(eventTypeController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    @Test
    void testGetEventTypeById() throws Exception {
        Long id = 1L;
        EventTypeResponseDto responseDto = new EventTypeResponseDto();
        responseDto.setId(id);
        when(eventTypeDtoMapper.toDto(any())).thenReturn(responseDto);
        when(eventTypeService.getById(id)).thenReturn(new EventType());

        mockMvc.perform(get("/event-types/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id));

        verify(eventTypeService, times(1)).getById(id);
    }

    @Test
    void testGetAllEventTypes() throws Exception {
        when(eventTypeService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/event-types/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        verify(eventTypeService, times(1)).getAll();
    }

    @Test
    void testAddEventType() throws Exception {
        Long id = 1L;
        EventTypeResponseDto responseDto = new EventTypeResponseDto();
        responseDto.setId(id);
        when(eventTypeService.add(any())).thenReturn(new EventType());
        when(eventTypeDtoMapper.toDto(any())).thenReturn(responseDto);

        mockMvc.perform(post("/event-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id));

        verify(eventTypeService, times(1)).add(any());
    }

    @Test
    void testUpdateEventType() throws Exception {
        Long id = 1L;
        EventTypeRequestDto requestDto = new EventTypeRequestDto();
        EventTypeResponseDto responseDto = new EventTypeResponseDto();
        responseDto.setId(1L);

        // Mock EventType and set its ID
        EventType eventType = new EventType();
        eventType.setId(1L);

        when(eventTypeDtoMapper.toModel(any())).thenReturn(eventType);
        when(eventTypeService.update(any())).thenReturn(eventType);
        when(eventTypeDtoMapper.toDto(any())).thenReturn(responseDto);

        ResponseEntity<EventTypeResponseDto> response = eventTypeController.update(1L, requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());

        // Verify that the update method was called with a non-null argument
        verify(eventTypeService, times(1)).update(any(EventType.class));


    }

    @Test
    void testDeleteEventType() throws Exception {
        Long id = 1L;
        doNothing().when(eventTypeService).deleteById(id);

        mockMvc.perform(delete("/event-types/{id}", id))
                .andExpect(status().isNoContent());

        verify(eventTypeService, times(1)).deleteById(id);
    }

    // Additional test to handle exception scenario
    @Test
    void testGetEventTypeById_NotFound() throws Exception {
        Long id = 1L;
        when(eventTypeService.getById(id)).thenThrow(NoEntityFoundException.class);

        mockMvc.perform(get("/event-types/{id}", id))
                .andExpect(status().isNotFound());

        verify(eventTypeService, times(1)).getById(id);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

