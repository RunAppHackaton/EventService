package com.runapp.eventservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runapp.eventservice.dto.request.ParticipationInEventsRequestDto;
import com.runapp.eventservice.dto.response.ParticipationInEventsResponseDto;
import com.runapp.eventservice.exception.GlobalExceptionHandler;
import com.runapp.eventservice.model.ParticipationInEvents;
import com.runapp.eventservice.service.ParticipationInEventsService;
import com.runapp.eventservice.service.dto.mapper.DtoMapper;
import com.runapp.eventservice.staticObject.StaticParticipationInEvents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ParticipationInEventsControllerTest {

    @Mock
    private ParticipationInEventsService participationInEventsService;

    @Mock
    private DtoMapper<ParticipationInEvents, ParticipationInEventsRequestDto, ParticipationInEventsResponseDto> participationInEventsDtoMapper;

    @InjectMocks
    private ParticipationInEventsController participationInEventsController;


    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(participationInEventsController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetParticipationInEventsById() throws Exception {
        Long id = 1L;
        ParticipationInEventsResponseDto responseDto = new ParticipationInEventsResponseDto();
        when(participationInEventsDtoMapper.toDto(any())).thenReturn(responseDto);
        when(participationInEventsService.getById(id)).thenReturn(new ParticipationInEvents());

        mockMvc.perform(get("/participation-in-events/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetAllParticipationInEvents() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(participationInEventsController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        when(participationInEventsService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/participation-in-events/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(participationInEventsService, times(1)).getAll();
        verifyNoMoreInteractions(participationInEventsService);
    }

    @Test
    void testAddInvalidParticipationInEvents() throws Exception {
        ParticipationInEventsRequestDto requestDto = new ParticipationInEventsRequestDto();

        mockMvc.perform(post("/participation-in-events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testAddParticipationInEvents() throws Exception {
        ParticipationInEventsRequestDto requestDto = StaticParticipationInEvents.participationInEventsRequestDto1();

        mockMvc.perform(post("/participation-in-events")
                        .contentType("application/json;charset=UTF-8")
                        .content(asJsonString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateValidParticipationInEvents() throws Exception {
        Long id = 1L;
        ParticipationInEventsRequestDto requestDto = StaticParticipationInEvents.participationInEventsRequestDto1();
        ParticipationInEventsResponseDto responseDto = StaticParticipationInEvents.participationInEventsResponseDto1();
        ParticipationInEvents participation = StaticParticipationInEvents.participationInEvents1();

        when(participationInEventsDtoMapper.toModel(any())).thenReturn(participation);
        when(participationInEventsService.update(participation)).thenReturn(participation);
        when(participationInEventsDtoMapper.toDto(participation)).thenReturn(responseDto);

        mockMvc.perform(put("/participation-in-events/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDeleteParticipationInEventsById() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/participation-in-events/{id}", id))
                .andExpect(status().isNoContent());

        verify(participationInEventsService, times(1)).deleteById(id);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
