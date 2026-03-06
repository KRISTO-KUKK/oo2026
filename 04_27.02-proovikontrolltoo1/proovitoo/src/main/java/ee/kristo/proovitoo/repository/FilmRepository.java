package ee.kristo.proovitoo.repository;

import ee.kristo.proovitoo.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findByInStockTrue();
}