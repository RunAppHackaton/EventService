package com.runapp.eventservice.controller;

import java.util.List;

import com.runapp.eventservice.dto.request.EventStatusRequestDto;
import com.runapp.eventservice.dto.response.EventStatusResponseDto;
import com.runapp.eventservice.service.dto.mapper.DtoMapper;
import com.runapp.eventservice.model.EventStatus;
import com.runapp.eventservice.service.EventStatusService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/event-statuses")
public class EventStatusController {
    private final EventStatusService eventStatusService;
    private final DtoMapper<EventStatus, EventStatusRequestDto,
            EventStatusResponseDto> eventStatusDtoMapper;

    @Operation(summary = "add event status")
    @PostMapping
    public ResponseEntity<EventStatusResponseDto> add(
            @RequestBody EventStatusRequestDto statusRequestDto
    ) {
        EventStatusResponseDto responseDto = eventStatusDtoMapper
                .toDto(eventStatusService.add(eventStatusDtoMapper.toModel(statusRequestDto)));
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "get event status by id", description = "event status must exist in db")
    @GetMapping("/{id}")
    public ResponseEntity<EventStatusResponseDto> get(@PathVariable("id") Long id) {
        EventStatusResponseDto ResponseDto =
                eventStatusDtoMapper.toDto(eventStatusService.getById(id));
        return new ResponseEntity<>(ResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "get all event statuses")
    @GetMapping("/all")
    public ResponseEntity<List<EventStatusResponseDto>> getAll() {
        List<EventStatusResponseDto> responseDtos = eventStatusService.getAll()
                .stream()
                .map(eventStatusDtoMapper::toDto)
                .toList();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @Operation(summary = "update event status", description = "pass id of existing event and event status object(with changed fields")
    @PutMapping("/{id}")
    public ResponseEntity<EventStatusResponseDto> get(@PathVariable("id") Long id,
                                      @RequestBody EventStatusRequestDto eventStatusRequestDto
    ) {
        EventStatus eventStatus = eventStatusDtoMapper.toModel(eventStatusRequestDto);
        eventStatus.setId(id);
        EventStatusResponseDto responseDto =
                eventStatusDtoMapper.toDto(eventStatusService.update(eventStatus));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "delete event status by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        eventStatusService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
