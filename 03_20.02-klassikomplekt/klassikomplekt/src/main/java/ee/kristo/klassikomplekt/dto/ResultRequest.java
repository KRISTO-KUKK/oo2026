package ee.kristo.klassikomplekt.dto;

import ee.kristo.klassikomplekt.entity.SportEvent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ResultRequest {

    @NotNull(message = "Spordiala (event) on kohustuslik.")
    private SportEvent event;

    @Positive(message = "Tulemus (performance) peab olema positiivne number.")
    private double performance;
}
