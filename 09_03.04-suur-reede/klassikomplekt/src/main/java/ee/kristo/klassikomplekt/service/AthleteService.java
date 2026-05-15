package ee.kristo.klassikomplekt.service;

import ee.kristo.klassikomplekt.dto.AthletePageResponse;
import ee.kristo.klassikomplekt.dto.AthleteCreateRequest;
import ee.kristo.klassikomplekt.dto.ResultRequest;
import ee.kristo.klassikomplekt.entity.Athlete;
import ee.kristo.klassikomplekt.entity.Result;
import ee.kristo.klassikomplekt.repository.AthleteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AthleteService {

    private final AthleteRepository athleteRepository;

    public Athlete addAthlete(AthleteCreateRequest req) {
        Athlete athlete = new Athlete();
        athlete.setName(req.getName().trim());
        athlete.setCountry(req.getCountry().trim().toUpperCase(Locale.ROOT));

        if (req.getResults() != null) {
            for (ResultRequest resultRequest : req.getResults()) {
                athlete.addResult(createResult(resultRequest));
            }
        }

        return athleteRepository.save(athlete);
    }

    public AthletePageResponse getAthletes(String country, String sortBy, String direction, int page, int size) {
        if (page < 0) {
            throw new RuntimeException("Lehekülg (page) peab olema vähemalt 0.");
        }
        if (size < 1 || size > 100) {
            throw new RuntimeException("Lehekülje suurus (size) peab olema vahemikus 1 kuni 100.");
        }

        String normalizedSortBy = normalizeSortBy(sortBy);
        String normalizedDirection = "asc".equalsIgnoreCase(direction) ? "asc" : "desc";

        List<Athlete> filtered = athleteRepository.findAll().stream()
                .filter(athlete -> !StringUtils.hasText(country)
                        || athlete.getCountry().equalsIgnoreCase(country.trim()))
                .sorted(comparator(normalizedSortBy, normalizedDirection))
                .toList();

        int fromIndex = Math.min(page * size, filtered.size());
        int toIndex = Math.min(fromIndex + size, filtered.size());
        int totalPages = filtered.isEmpty() ? 0 : (int) Math.ceil((double) filtered.size() / size);

        return new AthletePageResponse(
                filtered.subList(fromIndex, toIndex),
                page,
                size,
                filtered.size(),
                totalPages,
                StringUtils.hasText(country) ? country.trim().toUpperCase(Locale.ROOT) : null,
                normalizedSortBy,
                normalizedDirection
        );
    }

    public Athlete getAthlete(Long athleteId) {
        return athleteRepository.findById(athleteId)
                .orElseThrow(() -> new RuntimeException("Sportlast ei leitud id järgi: " + athleteId));
    }

    public Result addResult(Long athleteId, ResultRequest req) {
        Athlete athlete = getAthlete(athleteId);
        Result result = createResult(req);
        athlete.addResult(result);
        Athlete savedAthlete = athleteRepository.save(athlete);
        return savedAthlete.getResults().get(savedAthlete.getResults().size() - 1);
    }

    public int getTotalPoints(Long athleteId) {
        return getAthlete(athleteId).getTotalPoints();
    }

    private Result createResult(ResultRequest req) {
        if (req.getEvent() == null) {
            throw new RuntimeException("Spordiala (event) on kohustuslik.");
        }
        if (req.getPerformance() == null) {
            throw new RuntimeException("Tulemus (performance) on kohustuslik.");
        }

        Result result = new Result();
        result.setEvent(req.getEvent());
        result.setPerformance(req.getPerformance());
        result.setPoints(req.getEvent().calculatePoints(req.getPerformance()));
        return result;
    }

    private String normalizeSortBy(String sortBy) {
        if (!StringUtils.hasText(sortBy)) {
            return "totalPoints";
        }

        String value = sortBy.trim();
        if (value.equals("name") || value.equals("country") || value.equals("totalPoints")) {
            return value;
        }

        throw new RuntimeException("Sortida saab ainult väljade järgi: name, country, totalPoints.");
    }

    private Comparator<Athlete> comparator(String sortBy, String direction) {
        Comparator<Athlete> comparator = switch (sortBy) {
            case "name" -> Comparator.comparing(Athlete::getName, String.CASE_INSENSITIVE_ORDER);
            case "country" -> Comparator.comparing(Athlete::getCountry, String.CASE_INSENSITIVE_ORDER);
            default -> Comparator.comparingInt(Athlete::getTotalPoints);
        };

        return "desc".equals(direction) ? comparator.reversed() : comparator;
    }
}
