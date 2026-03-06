package ee.kristo.proovitoo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RentFilmResponse {
    private Long rentalId;
    private Long filmId;
    private String title;
    private String type;
    private int days;
    private int priceEur;
}