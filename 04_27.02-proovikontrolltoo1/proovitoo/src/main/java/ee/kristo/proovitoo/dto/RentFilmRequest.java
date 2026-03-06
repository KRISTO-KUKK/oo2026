package ee.kristo.proovitoo.dto;

import lombok.Data;

@Data
public class RentFilmRequest {
    private Long filmId;
    private int days;
}