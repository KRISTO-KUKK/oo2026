package ee.kristo.proovitoo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReturnRentalResponse {
    private Long rentalId;
    private Long filmId;
    private String title;
    private int paidDays;
    private int actualDays;
    private int lateDays;
    private int lateFeeEur;
}