package ee.kristo.klassikomplekt.controller;

import ee.kristo.klassikomplekt.dto.AthleteCreateRequest;
import ee.kristo.klassikomplekt.entity.Athlete;
import ee.kristo.klassikomplekt.service.AthleteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/athletes")
@RequiredArgsConstructor
public class AthleteController {

    private final AthleteService athleteService;

    @PostMapping
    public Athlete addAthlete(@Valid @RequestBody AthleteCreateRequest req) {
        return athleteService.addAthlete(req);
    }

    @GetMapping("/{id}/total-points")
    public int getTotalPoints(@PathVariable Long id) {
        return athleteService.getTotalPoints(id);
    }
}
