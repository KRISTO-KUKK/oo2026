package kukk.kristo.filmileht.repository;

import kukk.kristo.filmileht.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}