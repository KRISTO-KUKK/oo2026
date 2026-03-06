package ee.kristo.proovitoo.controller;

import ee.kristo.proovitoo.dto.RentFilmRequest;
import ee.kristo.proovitoo.dto.RentFilmResponse;
import ee.kristo.proovitoo.dto.ReturnRentalRequest;
import ee.kristo.proovitoo.dto.ReturnRentalResponse;
import ee.kristo.proovitoo.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    public RentFilmResponse rent(@RequestBody RentFilmRequest req) {
        return rentalService.rentFilm(req.getFilmId(), req.getDays());
    }

    @PostMapping("/{id}/return")
    public ReturnRentalResponse returnRental(@PathVariable Long id, @RequestBody(required = false) ReturnRentalRequest req) {
        return rentalService.returnRental(id, req != null ? req.getReturnDate() : null);
    }
}