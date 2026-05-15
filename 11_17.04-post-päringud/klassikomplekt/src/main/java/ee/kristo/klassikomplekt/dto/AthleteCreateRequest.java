package ee.kristo.klassikomplekt.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AthleteCreateRequest {

    @NotBlank(message = "Sportlase nimi (name) on kohustuslik.")
    private String name;

    @NotBlank(message = "Riik (country) on kohustuslik.")
    private String country;

    private List<@Valid ResultRequest> results = new ArrayList<>();
}
