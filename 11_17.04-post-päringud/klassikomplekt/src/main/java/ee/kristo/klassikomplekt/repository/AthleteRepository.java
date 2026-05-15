package ee.kristo.klassikomplekt.repository;

import ee.kristo.klassikomplekt.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
