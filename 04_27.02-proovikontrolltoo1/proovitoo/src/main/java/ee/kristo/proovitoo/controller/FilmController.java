package ee.kristo.proovitoo.controller;

import ee.kristo.proovitoo.dto.ChangeFilmTypeRequest;
import ee.kristo.proovitoo.entity.Film;
import ee.kristo.proovitoo.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @DeleteMapping("/{id}")
    public void removeFilm(@PathVariable Long id) {
        filmService.removeFilm(id);
    }

    @PutMapping("/{id}/type")
    public Film changeType(@PathVariable Long id, @RequestBody ChangeFilmTypeRequest req) {
        return filmService.changeFilmType(id, req.getType());
    }

    @GetMapping
    public List<Film> getAll() {
        return filmService.getAllFilms();
    }

    @GetMapping("/in-stock")
    public List<Film> getInStock() {
        return filmService.getFilmsInStock();
    }
}