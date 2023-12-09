package com.runapp.eventservice.controller;

import java.util.List;

import com.runapp.eventservice.dto.response.EventTypeResponseDto;
import com.runapp.eventservice.service.EventTypeService;
import com.runapp.eventservice.service.dto.mapper.DtoMapper;
import com.runapp.eventservice.dto.request.EventTypeRequestDto;
import com.runapp.eventservice.model.EventType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/event-types")
public class EventTypeController {
    private final EventTypeService eventTypeService;
    private final DtoMapper<EventType,
                    EventTypeRequestDto, EventTypeResponseDto> eventTypeDtoMapper;

    @Operation(summary = "get event type by id", description = "event type must exist in db")
    @GetMapping("/{id}")
    public ResponseEntity<EventTypeResponseDto> get(@PathVariable("id") Long id) {
        EventTypeResponseDto responseDto = eventTypeDtoMapper.toDto(eventTypeService.getById(id));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "get all event types")
    @GetMapping("/all")
    public ResponseEntity<List<EventTypeResponseDto>> getAll() {
        List<EventTypeResponseDto> responseDtos = eventTypeService.getAll()
                .stream()
                .map(eventTypeDtoMapper::toDto)
                .toList();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @Operation(summary = "add event type")
    @PostMapping
    public ResponseEntity<EventTypeResponseDto> add(@RequestBody EventTypeRequestDto requestDto) {
        EventTypeResponseDto responseDto = eventTypeDtoMapper
                .toDto(eventTypeService.add(eventTypeDtoMapper.toModel(requestDto)));
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "update event type",
            description = "pass id of existing event and event type object (with changed fields)")
    @PutMapping("/{id}")
    public ResponseEntity<EventTypeResponseDto> update(@PathVariable Long id,
                                       @RequestBody EventTypeRequestDto requestDto
    ) {
        EventType eventType = eventTypeDtoMapper.toModel(requestDto);
        eventType.setId(id);
        EventTypeResponseDto responseDto =
                eventTypeDtoMapper.toDto(eventTypeService.update(eventType));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "delete event type by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        eventTypeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
