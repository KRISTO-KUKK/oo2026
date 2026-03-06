package ee.kristo.proovitoo.dto;

import ee.kristo.proovitoo.entity.FilmType;
import lombok.Data;

@Data
public class ChangeFilmTypeRequest {
    private FilmType type;
}