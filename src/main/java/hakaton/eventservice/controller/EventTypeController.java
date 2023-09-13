package hakaton.eventservice.controller;

import java.util.List;
import hakaton.eventservice.dto.request.EventTypeRequestDto;
import hakaton.eventservice.dto.response.EventTypeResponseDto;
import hakaton.eventservice.model.EventType;
import hakaton.eventservice.service.EventTypeService;
import hakaton.eventservice.service.dto.mapper.DtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
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
    public EventTypeResponseDto get(@PathVariable("id") Long id) {
        return eventTypeDtoMapper.toDto(eventTypeService.getById(id));
    }

    @Operation(summary = "get all event types")
    @GetMapping("/all")
    public List<EventTypeResponseDto> getAll() {
        return eventTypeService.getAll().stream()
                .map(eventTypeDtoMapper::toDto)
                .toList();
    }

    @Operation(summary = "add event type")
    @PostMapping
    public EventTypeResponseDto add(@RequestBody EventTypeRequestDto requestDto) {
        return eventTypeDtoMapper.toDto(eventTypeService
                    .add(eventTypeDtoMapper.toModel(requestDto)));
    }

    @Operation(summary = "update event type",
            description = "pass id of existing event and event type object (with changed fields)")
    @PutMapping("/{id}")
    public EventTypeResponseDto update(@PathVariable Long id,
                                       @RequestBody EventTypeRequestDto requestDto) {
        EventType eventType = eventTypeDtoMapper.toModel(requestDto);
        eventType.setId(id);
        return eventTypeDtoMapper.toDto(eventTypeService.update(eventType));
    }

    @Operation(summary = "delete event type by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        eventTypeService.deleteById(id);
    }
}
