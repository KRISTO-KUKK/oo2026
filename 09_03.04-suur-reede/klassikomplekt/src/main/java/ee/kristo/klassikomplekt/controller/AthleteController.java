package ee.kristo.klassikomplekt.controller;

import ee.kristo.klassikomplekt.dto.AthletePageResponse;
import ee.kristo.klassikomplekt.dto.AthleteCreateRequest;
import ee.kristo.klassikomplekt.dto.ResultRequest;
import ee.kristo.klassikomplekt.entity.Athlete;
import ee.kristo.klassikomplekt.entity.Result;
import ee.kristo.klassikomplekt.entity.SportEvent;
import ee.kristo.klassikomplekt.service.AthleteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/athletes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AthleteController {

    private final AthleteService athleteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Athlete addAthlete(@Valid @RequestBody AthleteCreateRequest req) {
        return athleteService.addAthlete(req);
    }

    @GetMapping
    public AthletePageResponse getAthletes(
            @RequestParam(required = false) String country,
            @RequestParam(defaultValue = "totalPoints") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return athleteService.getAthletes(country, sortBy, direction, page, size);
    }

    @GetMapping("/{id}")
    public Athlete getAthlete(@PathVariable Long id) {
        return athleteService.getAthlete(id);
    }

    @PostMapping("/{id}/results")
    @ResponseStatus(HttpStatus.CREATED)
    public Result addResult(@PathVariable Long id, @Valid @RequestBody ResultRequest req) {
        return athleteService.addResult(id, req);
    }

    @GetMapping("/{id}/total-points")
    public int getTotalPoints(@PathVariable Long id) {
        return athleteService.getTotalPoints(id);
    }

    @GetMapping("/events")
    public SportEvent[] getEvents() {
        return SportEvent.values();
    }
}
