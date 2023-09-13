package hakaton.eventservice.controller;

import java.util.List;
import hakaton.eventservice.dto.request.EventRequestDto;
import hakaton.eventservice.dto.response.EventResponseDto;
import hakaton.eventservice.model.Event;
import hakaton.eventservice.service.EventService;
import hakaton.eventservice.service.dto.mapper.DtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final DtoMapper<Event, EventRequestDto, EventResponseDto> eventDtoMapper;

    @Operation(summary = "get event by id", description = "customer must exist")
    @GetMapping("/{id}")
    public EventResponseDto get(@PathVariable("id") Long id) {
        return eventDtoMapper.toDto(eventService.getById(id));
    }

    @Operation(summary = "get all events")
    @GetMapping("/all")
    public List<EventResponseDto> getAll() {
        return eventService.getAll().stream()
                .map(eventDtoMapper::toDto)
                .toList();
    }

    @Operation(summary = "add event to db")
    @PostMapping
    public EventResponseDto add(@Valid @RequestBody EventRequestDto requestDto) {
        return eventDtoMapper.toDto(eventService
                .add(eventDtoMapper.toModel(requestDto)));
    }

    @Operation(summary = "update event", description = "pass id of existing event, and object of type event (with changed fields)")
    @PutMapping("/{id}")
    public EventResponseDto update(@PathVariable Long id,
                                   @Valid @RequestBody EventRequestDto requestDto) {
        Event event = eventDtoMapper.toModel(requestDto);
        event.setId(id);
        return eventDtoMapper.toDto(eventService.update(event));
    }

    @Operation(summary = "delete event by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        eventService.deleteById(id);
    }
}
