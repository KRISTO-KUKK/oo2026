package kukk.kristo.filmileht.controller;

import kukk.kristo.filmileht.entity.Movie;
import kukk.kristo.filmileht.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // GET /movies
    @GetMapping
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    // GET /movies/{id}
    @GetMapping("/{id}")
    public Movie getById(@PathVariable Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    }

    // POST /movies
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@RequestBody Movie movie) {
        // Make sure client can't force an ID
        movie.setId(null);
        return movieRepository.save(movie);
    }

    // DELETE /movies/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
        movieRepository.deleteById(id);
    }
}