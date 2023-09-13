package hakaton.eventservice.controller;

import java.util.List;
import hakaton.eventservice.dto.request.ParticipationInEventsRequestDto;
import hakaton.eventservice.dto.response.ParticipationInEventsResponseDto;
import hakaton.eventservice.model.ParticipationInEvents;
import hakaton.eventservice.service.ParticipationInEventsService;
import hakaton.eventservice.service.dto.mapper.DtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/participation-in-events")
public class ParticipationInEventsController {
    private final ParticipationInEventsService participationInEventsService;
    private final DtoMapper<ParticipationInEvents,
            ParticipationInEventsRequestDto,
            ParticipationInEventsResponseDto> participationInEventsDtoMapper;

    @Operation(summary = "get participation in events by id", description = "must exist in db")
    @GetMapping("/{id}")
    public ParticipationInEventsResponseDto get(@PathVariable("id") Long id) {
        return participationInEventsDtoMapper
                    .toDto(participationInEventsService.getById(id));
    }

    @Operation(summary = "get all participation in events")
    @GetMapping("/all")
    public List<ParticipationInEventsResponseDto> getAll() {
        return participationInEventsService.getAll().stream()
                .map(participationInEventsDtoMapper::toDto)
                .toList();
    }

    @Operation(summary = "add participation in events")
    @PostMapping
    public ParticipationInEventsResponseDto add(
            @Valid @RequestBody ParticipationInEventsRequestDto requestDto) {
        return participationInEventsDtoMapper.toDto(participationInEventsService
                .add(participationInEventsDtoMapper.toModel(requestDto)));
    }

    @Operation(summary = "update participation in events",
            description = "pass id of existing object,"
                    + " and object of type participation in events (with changed fields)")
    @PutMapping("/{id}")
    public ParticipationInEventsResponseDto update(
            @PathVariable Long id, @Valid @RequestBody ParticipationInEventsRequestDto requestDto) {
        ParticipationInEvents participation = participationInEventsDtoMapper.toModel(requestDto);
        participation.setId(id);
        return participationInEventsDtoMapper
                        .toDto(participationInEventsService.update(participation));
    }

    @Operation(summary = "delete participation in events by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        participationInEventsService.deleteById(id);
    }
}
