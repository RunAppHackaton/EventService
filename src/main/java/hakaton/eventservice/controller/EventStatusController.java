package hakaton.eventservice.controller;

import java.util.List;
import hakaton.eventservice.dto.request.EventStatusRequestDto;
import hakaton.eventservice.dto.response.EventStatusResponseDto;
import hakaton.eventservice.model.EventStatus;
import hakaton.eventservice.service.EventStatusService;
import hakaton.eventservice.service.dto.mapper.DtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
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
    public EventStatusResponseDto add(@RequestBody EventStatusRequestDto statusRequestDto) {
        return eventStatusDtoMapper.toDto(eventStatusService
                .add(eventStatusDtoMapper.toModel(statusRequestDto)));
    }

    @Operation(summary = "get event status by id", description = "event status must exist in db")
    @GetMapping("/{id}")
    public EventStatusResponseDto get(@PathVariable("id") Long id) {
        return eventStatusDtoMapper.toDto(eventStatusService.getById(id));
    }

    @Operation(summary = "get all event statuses")
    @GetMapping("/all")
    public List<EventStatusResponseDto> getAll() {
        return eventStatusService.getAll().stream()
                .map(eventStatusDtoMapper::toDto)
                .toList();
    }

    @Operation(summary = "update event status", description = "pass id of existing event and event status object(with changed fields")
    @PutMapping("/{id}")
    public EventStatusResponseDto get(@PathVariable("id") Long id,
                                      @RequestBody EventStatusRequestDto eventStatusRequestDto) {
        EventStatus eventStatus = eventStatusDtoMapper.toModel(eventStatusRequestDto);
        eventStatus.setId(id);
        return eventStatusDtoMapper.toDto(eventStatusService.update(eventStatus));
    }

    @Operation(summary = "delete event status by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        eventStatusService.deleteById(id);
    }
}
