package br.com.barros.Cinema.controller;

import br.com.barros.Cinema.dto.SessaoRequestDto;
import br.com.barros.Cinema.dto.SessaoResponseDto;
import br.com.barros.Cinema.service.SessaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessoes")
@RequiredArgsConstructor
public class SessaoController {

    private final SessaoService sessaoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SessaoResponseDto> findAll(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "5") int size
    ) {
        return sessaoService.findAll(page,size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SessaoResponseDto findById(@PathVariable Long id) {
        return sessaoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessaoResponseDto save(@Valid @RequestBody SessaoRequestDto sessaoRequestDto) {
        return sessaoService.save(sessaoRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        sessaoService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SessaoResponseDto update(@Valid @RequestBody SessaoRequestDto sessaoRequestDto, @PathVariable Long id) {
        return sessaoService.update(sessaoRequestDto, id);
    }
}
