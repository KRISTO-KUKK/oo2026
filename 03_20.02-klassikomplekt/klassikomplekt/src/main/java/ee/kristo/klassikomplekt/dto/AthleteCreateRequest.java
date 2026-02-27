package ee.kristo.klassikomplekt.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AthleteCreateRequest {

    @NotBlank(message = "Sportlase nimi (name) on kohustuslik.")
    private String name;

    @NotEmpty(message = "Tulemused (results) peavad olema vähemalt 1 kirje.")
    private List<@Valid ResultRequest> results;
}