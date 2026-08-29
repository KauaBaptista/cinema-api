package br.com.barros.Cinema.controller;

import br.com.barros.Cinema.database.model.FilmeEntity;
import br.com.barros.Cinema.dto.FilmeRequestDto;
import br.com.barros.Cinema.dto.FilmeResponseDto;
import br.com.barros.Cinema.service.FilmeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
@RequiredArgsConstructor
public class FilmeController {

    private final FilmeService filmeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FilmeResponseDto> findAll() {
        return filmeService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FilmeResponseDto findById(@PathVariable Long id) {
        return filmeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmeEntity save(@Valid @RequestBody FilmeRequestDto filmeRequestDto) {
        return filmeService.save(filmeRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        filmeService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FilmeEntity update(@Valid @RequestBody FilmeRequestDto filmeRequestDto, @PathVariable Long id) {
        return filmeService.update(filmeRequestDto, id);
    }
}
