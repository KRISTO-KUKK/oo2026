package ee.kristo.veateated.controller;

import ee.kristo.veateated.entity.Car;
import ee.kristo.veateated.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @GetMapping
    public List<Car> getCars() {
        return carService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car addCar(@Valid @RequestBody Car car) {
        return carService.add(car);
    }
}