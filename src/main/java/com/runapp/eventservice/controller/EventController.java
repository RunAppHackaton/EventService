package com.runapp.eventservice.controller;

import java.util.List;

import com.runapp.eventservice.service.impl.EventServiceImpl;
import com.runapp.eventservice.dto.request.EventImageDeleteRequest;
import com.runapp.eventservice.dto.request.EventRequestDto;
import com.runapp.eventservice.dto.response.EventResponseDto;
import com.runapp.eventservice.model.Event;
import com.runapp.eventservice.service.dto.mapper.DtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/events")
public class EventController {
    private final EventServiceImpl eventService;
    private final DtoMapper<Event, EventRequestDto, EventResponseDto> eventDtoMapper;
    private final String storageDirectory = "EventService/";

    @Operation(summary = "get event by id", description = "customer must exist")
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> get(@PathVariable("id") Long id) {
        EventResponseDto responseDto = eventDtoMapper.toDto(eventService.getById(id));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "get all events")
    @GetMapping("/all")
    public ResponseEntity<List<EventResponseDto>> getAll() {
        List<EventResponseDto> responseDtos = eventService.getAll().stream()
                .map(eventDtoMapper::toDto)
                .toList();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @Operation(summary = "add event to db")
    @PostMapping
    public ResponseEntity<EventResponseDto> add(@Valid @RequestBody EventRequestDto requestDto) {
        EventResponseDto responseDto = eventDtoMapper.toDto(eventService
                .add(eventDtoMapper.toModel(requestDto)));
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "update event", description = "pass id of existing event, and object of type event (with changed fields)")
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> update(@PathVariable Long id,
                                                   @Valid @RequestBody EventRequestDto requestDto) {
        Event event = eventDtoMapper.toModel(requestDto);
        event.setId(id);
        EventResponseDto responseDto = eventDtoMapper.toDto(eventService.update(event));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "delete event by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        eventService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "upload event image", description = "save image event and upload image to storage")
    @PostMapping("/upload-image")
    public EventResponseDto uploadEventImage(@RequestParam("file") MultipartFile file, @RequestParam("event_id") Long eventId) {
        return eventDtoMapper.toDto(eventService.uploadEventImage(eventId, file, storageDirectory));
    }

    @Operation(summary = "upload event image", description = "delete image event and delete image from storage")
    @DeleteMapping
    public ResponseEntity<Object> deleteEventImage(@RequestBody EventImageDeleteRequest request) {
        return eventService.deleteEventImage(request, storageDirectory);
    }
}
