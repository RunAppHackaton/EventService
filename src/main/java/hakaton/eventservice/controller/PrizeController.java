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
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/prizes")
public class PrizeController {
    private final PrizeService prizeService;
    private final DtoMapper<Prize, PrizeRequestDto, PrizeResponseDto> prizeDtoMapper;

    @Operation(summary = "get prize by id", description = "prize must exist in db")
    @GetMapping("/{id}")
    public PrizeResponseDto get(@PathVariable("id") Long id) {
        return prizeDtoMapper.toDto(prizeService.getById(id));
    }

    @Operation(summary = "get all prizes")
    @GetMapping("/all")
    public List<PrizeResponseDto> getAll() {
        return prizeService.getAll().stream()
                .map(prizeDtoMapper::toDto)
                .toList();
    }

    @Operation(summary = "add prize to db")
    @PostMapping
    public PrizeResponseDto add(@Valid @RequestBody PrizeRequestDto requestDto) {
        return prizeDtoMapper.toDto(prizeService
                .add(prizeDtoMapper.toModel(requestDto)));
    }

    @Operation(summary = "update prize", description = "pass id of existing prize, and prize object (with changed fields)")
    @PutMapping("/{id}")
    public PrizeResponseDto update(@PathVariable("id") Long id,
                                       @Valid @RequestBody PrizeRequestDto requestDto) {
        Prize prize = prizeDtoMapper.toModel(requestDto);
        prize.setId(id);
        return prizeDtoMapper.toDto(prizeService.update(prize));
    }

    @Operation(summary = "delete prize by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        prizeService.deleteById(id);
    }
}
