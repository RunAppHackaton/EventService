package com.runapp.eventservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runapp.eventservice.dto.request.PrizeRequestDto;
import com.runapp.eventservice.dto.response.PrizeResponseDto;
import com.runapp.eventservice.exception.GlobalExceptionHandler;
import com.runapp.eventservice.model.Prize;
import com.runapp.eventservice.service.PrizeService;
import com.runapp.eventservice.service.dto.mapper.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class PrizeControllerTest {

    @Mock
    private PrizeService prizeService;

    @Mock
    private DtoMapper<Prize, PrizeRequestDto, PrizeResponseDto> prizeDtoMapper;

    @InjectMocks
    private PrizeController prizeController;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(prizeController)
                .setControllerAdvice(GlobalExceptionHandler.class).build();
    }

    @Test
    void testGetPrizeById() throws Exception {
        Long id = 1L;
        PrizeResponseDto responseDto = new PrizeResponseDto();
        when(prizeDtoMapper.toDto(any())).thenReturn(responseDto);
        when(prizeService.getById(id)).thenReturn(new Prize());

        prizeController.get(id);

        verify(prizeService, times(1)).getById(id);
        verify(prizeDtoMapper, times(1)).toDto(any());
    }

    @Test
    void testGetAllPrizes() throws Exception {
        List<Prize> prizes = new ArrayList<>();

        when(prizeService.getAll()).thenReturn(prizes);

        prizeController.getAll();

        verify(prizeService, times(1)).getAll();
        // Verify that the toDto method is called for each Prize object
        for (Prize prize : prizes) {
            verify(prizeDtoMapper, times(1)).toDto(prize);
        }
    }

    @Test
    void testAddPrize() throws Exception {
        PrizeRequestDto requestDto = new PrizeRequestDto();
        PrizeResponseDto responseDto = new PrizeResponseDto();
        when(prizeDtoMapper.toModel(requestDto)).thenReturn(new Prize());
        when(prizeService.add(any())).thenReturn(new Prize());
        when(prizeDtoMapper.toDto(any())).thenReturn(responseDto);

        prizeController.add(requestDto);

        verify(prizeService, times(1)).add(any());
        verify(prizeDtoMapper, times(1)).toModel(requestDto);
        verify(prizeDtoMapper, times(1)).toDto(any());
    }

    @Test
    public void testAdd_PrizeAlreadyExists() throws Exception {
        // Mock PrizeRequestDto
        PrizeRequestDto requestDto = new PrizeRequestDto();
        // Set fields of requestDto as needed

        // Perform POST request
        mockMvc.perform(post("/prizes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest()); // Expecting 400 Bad Request
    }

    @Test
    void testUpdatePrize() throws Exception {
        Long id = 1L;
        PrizeRequestDto requestDto = new PrizeRequestDto();
        PrizeResponseDto responseDto = new PrizeResponseDto();
        when(prizeDtoMapper.toModel(requestDto)).thenReturn(new Prize());
        when(prizeService.update(any())).thenReturn(new Prize());
        when(prizeDtoMapper.toDto(any())).thenReturn(responseDto);

        prizeController.update(id, requestDto);

        verify(prizeService, times(1)).update(any());
        verify(prizeDtoMapper, times(1)).toModel(requestDto);
        verify(prizeDtoMapper, times(1)).toDto(any());
    }

    @Test
    void testDeletePrize() throws Exception {
        Long id = 1L;
        ResponseEntity<Void> expectedResponse = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        doNothing().when(prizeService).deleteById(id);

        prizeController.delete(id);

        verify(prizeService, times(1)).deleteById(id);
    }
}

