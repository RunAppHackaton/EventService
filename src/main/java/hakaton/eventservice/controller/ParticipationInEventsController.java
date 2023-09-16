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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ParticipationInEventsResponseDto> get(@PathVariable("id") Long id) {
        ParticipationInEventsResponseDto responseDto =
                participationInEventsDtoMapper.toDto(participationInEventsService.getById(id));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "get all participation in events")
    @GetMapping("/all")
    public ResponseEntity<List<ParticipationInEventsResponseDto>> getAll() {
        List<ParticipationInEventsResponseDto> responseDtos = participationInEventsService.getAll()
                .stream()
                .map(participationInEventsDtoMapper::toDto)
                .toList();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @Operation(summary = "add participation in events")
    @PostMapping
    public ResponseEntity<ParticipationInEventsResponseDto> add(
            @Valid @RequestBody ParticipationInEventsRequestDto requestDto
    ) {
        ParticipationInEventsResponseDto responseDto = participationInEventsDtoMapper
                .toDto(participationInEventsService.add(participationInEventsDtoMapper.toModel(requestDto)));
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "update participation in events",
            description = "pass id of existing object,"
                    + " and object of type participation in events (with changed fields)")
    @PutMapping("/{id}")
    public ResponseEntity<ParticipationInEventsResponseDto> update(
            @PathVariable Long id, @Valid @RequestBody ParticipationInEventsRequestDto requestDto) {
        ParticipationInEvents participation = participationInEventsDtoMapper.toModel(requestDto);
        participation.setId(id);
        ParticipationInEventsResponseDto responseDto = participationInEventsDtoMapper
                .toDto(participationInEventsService.update(participation));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "delete participation in events by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        participationInEventsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
