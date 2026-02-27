package ee.kristo.veateated.repository;

import ee.kristo.veateated.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    boolean existsByVin(String vin);
}