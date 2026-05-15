package ee.kristo.klassikomplekt.dto;

import ee.kristo.klassikomplekt.entity.Athlete;

import java.util.List;

public record AthletePageResponse(
        List<Athlete> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        String country,
        String sortBy,
        String direction
) {
}
