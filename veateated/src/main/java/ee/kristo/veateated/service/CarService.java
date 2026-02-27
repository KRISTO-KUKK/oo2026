package ee.kristo.veateated.service;

import ee.kristo.veateated.entity.Car;
import ee.kristo.veateated.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public Car add(Car car) {
        if (carRepository.existsByVin(car.getVin())) {
            throw new RuntimeException("VIN on juba olemas: " + car.getVin());
        }
        return carRepository.save(car);
    }
}