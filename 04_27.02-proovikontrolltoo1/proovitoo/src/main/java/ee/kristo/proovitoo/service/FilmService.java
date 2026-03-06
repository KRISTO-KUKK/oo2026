package ee.kristo.proovitoo.service;

import ee.kristo.proovitoo.entity.Film;
import ee.kristo.proovitoo.entity.FilmType;
import ee.kristo.proovitoo.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;

    public Film addFilm(Film film) {
        if (film.getTitle() == null || film.getTitle().trim().isEmpty()) {
            throw new RuntimeException("Title is required");
        }
        if (film.getType() == null) {
            throw new RuntimeException("Film type is required");
        }
        film.setInStock(true);
        return filmRepository.save(film);
    }

    public void removeFilm(Long id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found: " + id));

        if (!film.isInStock()) {
            throw new RuntimeException("Film is currently rented and cannot be removed: " + id);
        }

        filmRepository.delete(film);
    }

    public Film changeFilmType(Long id, FilmType newType) {
        if (newType == null) {
            throw new RuntimeException("Film type is required");
        }

        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found: " + id));

        film.setType(newType);
        return filmRepository.save(film);
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public List<Film> getFilmsInStock() {
        return filmRepository.findByInStockTrue();
    }

    public Film getFilm(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found: " + id));
    }

    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }
}