package ee.kristo.proovitoo.repository;

import ee.kristo.proovitoo.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}