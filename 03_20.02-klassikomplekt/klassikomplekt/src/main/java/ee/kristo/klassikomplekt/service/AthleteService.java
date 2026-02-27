package ee.kristo.klassikomplekt.service;

import ee.kristo.klassikomplekt.dto.AthleteCreateRequest;
import ee.kristo.klassikomplekt.dto.ResultRequest;
import ee.kristo.klassikomplekt.entity.Athlete;
import ee.kristo.klassikomplekt.entity.Result;
import ee.kristo.klassikomplekt.entity.SportEvent;
import ee.kristo.klassikomplekt.repository.AthleteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AthleteService {

    private final AthleteRepository athleteRepository;

    public Athlete addAthlete(AthleteCreateRequest req) {
        Athlete athlete = new Athlete();
        athlete.setName(req.getName());

        for (ResultRequest rr : req.getResults()) {
            Result r = new Result();
            r.setEvent(rr.getEvent());
            r.setPerformance(rr.getPerformance());
            r.setPoints(calculatePoints(rr.getEvent(), rr.getPerformance()));
            r.setAthlete(athlete);
            athlete.getResults().add(r);
        }

        return athleteRepository.save(athlete);
    }

    public int getTotalPoints(Long athleteId) {
        Athlete athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new RuntimeException("Sportlast ei leitud id järgi: " + athleteId));

        return athlete.getResults().stream().mapToInt(Result::getPoints).sum();
    }

    private int calculatePoints(SportEvent event, double performance) {
        if (event == SportEvent.M100) {
            if (performance <= 0 || performance >= 60) {
                throw new RuntimeException("100m tulemus peab olema sekundites ja mõistlikus vahemikus.");
            }
            double A = 25.4347;
            double B = 18.0;
            double C = 1.81;
            double pts = A * Math.pow((B - performance), C);
            return (int) Math.floor(pts);
        }

        if (event == SportEvent.LONG_JUMP) {
            if (performance <= 0 || performance > 12) {
                throw new RuntimeException("Kaugushüppe tulemus peab olema meetrites (nt 8.0) ja mõistlikus vahemikus.");
            }
            double A = 0.14354;
            double B = 220.0;
            double C = 1.4;
            double performanceCm = performance * 100.0;
            double pts = A * Math.pow((performanceCm - B), C);
            return (int) Math.floor(pts);
        }

        throw new RuntimeException("Tundmatu spordiala: " + event);
    }
}
