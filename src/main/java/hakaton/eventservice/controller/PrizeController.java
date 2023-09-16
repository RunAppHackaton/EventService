package hakaton.eventservice.controller;

import java.util.List;
import hakaton.eventservice.dto.request.PrizeRequestDto;
import hakaton.eventservice.dto.response.PrizeResponseDto;
import hakaton.eventservice.model.Prize;
import hakaton.eventservice.service.PrizeService;
import hakaton.eventservice.service.dto.mapper.DtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/prizes")
public class PrizeController {
    private final PrizeService prizeService;
    private final DtoMapper<Prize, PrizeRequestDto, PrizeResponseDto> prizeDtoMapper;

    @Operation(summary = "get prize by id", description = "prize must exist in db")
    @GetMapping("/{id}")
    public ResponseEntity<PrizeResponseDto> get(@PathVariable("id") Long id) {
        PrizeResponseDto responseDto = prizeDtoMapper.toDto(prizeService.getById(id));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "get all prizes")
    @GetMapping("/all")
    public ResponseEntity<List<PrizeResponseDto>> getAll() {
        List<PrizeResponseDto> responseDtos = prizeService.getAll()
                .stream()
                .map(prizeDtoMapper::toDto)
                .toList();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @Operation(summary = "add prize to db")
    @PostMapping
    public ResponseEntity<PrizeResponseDto> add(@Valid @RequestBody PrizeRequestDto requestDto) {
        PrizeResponseDto responseDto = prizeDtoMapper
                .toDto(prizeService.add(prizeDtoMapper.toModel(requestDto)));
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "update prize", description = "pass id of existing prize, and prize object (with changed fields)")
    @PutMapping("/{id}")
    public ResponseEntity<PrizeResponseDto> update(@PathVariable("id") Long id,
                                       @Valid @RequestBody PrizeRequestDto requestDto
    ) {
        Prize prize = prizeDtoMapper.toModel(requestDto);
        prize.setId(id);
        PrizeResponseDto responseDto = prizeDtoMapper.toDto(prizeService.update(prize));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "delete prize by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        prizeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
