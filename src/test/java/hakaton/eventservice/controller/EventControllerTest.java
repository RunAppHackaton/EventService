package hakaton.eventservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hakaton.eventservice.dto.request.EventImageDeleteRequest;
import hakaton.eventservice.dto.request.EventRequestDto;
import hakaton.eventservice.dto.response.EventResponseDto;
import hakaton.eventservice.exception.NoEntityFoundException;
import hakaton.eventservice.model.Event;
import hakaton.eventservice.service.dto.mapper.DtoMapper;
import hakaton.eventservice.service.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventServiceImpl eventService;

    @MockBean
    private DtoMapper<Event, EventRequestDto, EventResponseDto> eventDtoMapper;

    private Event event;
    private EventResponseDto eventResponseDto;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        event = new Event(1L);
        eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(1L);
    }

    @Test
    public void testGetAllWhenEventsExistThenReturnEvents() throws Exception {
        EventResponseDto eventResponseDto1 = new EventResponseDto();
        EventResponseDto eventResponseDto2 = new EventResponseDto();

        Event event1 = new Event();
        Event event2 = new Event();

        when(eventService.getAll()).thenReturn(Arrays.asList(event1, event2));
        when(eventDtoMapper.toDto(event1)).thenReturn(eventResponseDto1);
        when(eventDtoMapper.toDto(event2)).thenReturn(eventResponseDto2);

        mockMvc.perform(MockMvcRequestBuilders.get("/events/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}]"));
    }

    @Test
    public void testGetAllWhenNoEventsThenReturnEmptyList() throws Exception {
        when(eventService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/events/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    public void testAddWhenValidEventRequestDtoThenReturnCreated() throws Exception {
        EventRequestDto mockRequestDto = new EventRequestDto();
        Event mockEvent = new Event();
        EventResponseDto mockResponseDto = new EventResponseDto();

        when(eventDtoMapper.toModel(mockRequestDto)).thenReturn(mockEvent);
        when(eventService.add(mockEvent)).thenReturn(mockEvent);
        when(eventDtoMapper.toDto(mockEvent)).thenReturn(mockResponseDto);

        String requestDtoJson = objectMapper.writeValueAsString(mockRequestDto);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetWhenValidIdThenReturnEventResponseDto() throws Exception {
        when(eventService.getById(1L)).thenReturn(event);
        when(eventDtoMapper.toDto(event)).thenReturn(eventResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/events/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }

    @Test
    public void testGetWhenNonExistentIdThenReturnNotFound() throws Exception {
        when(eventService.getById(1L)).thenThrow(new NoEntityFoundException("Event with id: " + 1L + " doesn't exist"));

        mockMvc.perform(MockMvcRequestBuilders.get("/events/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteWhenValidIdThenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/events/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUploadEventImageWhenValidFileAndEventIdThenReturnEventResponseDto() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        when(eventService.uploadEventImage(1L, file, "EventService/")).thenReturn(event);
        when(eventDtoMapper.toDto(event)).thenReturn(eventResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/events/upload-image")
                        .file(file)
                        .param("event_id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1}"));
    }

    @Test
    public void testDeleteEventImageWhenValidEventImageDeleteRequestThenReturnOk() throws Exception {
        EventImageDeleteRequest request = new EventImageDeleteRequest();
        request.setEvent_id(1L);
        request.setFile_uri("file_uri");

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(delete("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateWhenValidIdAndRequestDtoThenReturnUpdatedEvent() throws Exception {
        EventRequestDto requestDto = new EventRequestDto();
        EventResponseDto responseDto = new EventResponseDto();
        Event event = new Event();

        when(eventDtoMapper.toModel(any(EventRequestDto.class))).thenReturn(event);
        when(eventService.update(any(Event.class))).thenReturn(event);
        when(eventDtoMapper.toDto(any(Event.class))).thenReturn(responseDto);

        mockMvc.perform(put("/events/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateWhenNonExistentIdThenReturnNotFound() throws Exception {
        EventRequestDto requestDto = new EventRequestDto();

        when(eventDtoMapper.toModel(any(EventRequestDto.class))).thenReturn(new Event());
        when(eventService.update(any(Event.class))).thenThrow(new NoEntityFoundException(""));

        mockMvc.perform(put("/events/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteEventImageWhenImageDeletedThenReturnNoContent() throws Exception {
        EventImageDeleteRequest request = new EventImageDeleteRequest("image.jpg", 1L);
        when(eventService.deleteEventImage(request, "EventService/")).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(MockMvcRequestBuilders.delete("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"file_uri\":\"image.jpg\",\"event_id\":1}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteWhenValidIdThenNoContent() throws Exception {
        // Arrange
        doNothing().when(eventService).deleteById(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/events/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteWhenInvalidIdThenNotFound() throws Exception {
        // Arrange
        doThrow(new NoEntityFoundException("message")).when(eventService).deleteById(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/events/{id}", -1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}