package ee.kristo.proovitoo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReturnRentalRequest {
    private LocalDate returnDate;
}