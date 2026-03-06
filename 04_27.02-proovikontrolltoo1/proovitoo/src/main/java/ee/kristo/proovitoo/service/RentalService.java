package ee.kristo.proovitoo.service;

import ee.kristo.proovitoo.dto.RentFilmResponse;
import ee.kristo.proovitoo.dto.ReturnRentalResponse;
import ee.kristo.proovitoo.entity.Film;
import ee.kristo.proovitoo.entity.Rental;
import ee.kristo.proovitoo.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final FilmService filmService;
    private final RentalRepository rentalRepository;
    private final PriceCalculator priceCalculator;

    public RentFilmResponse rentFilm(Long filmId, int days) {
        if (filmId == null) {
            throw new RuntimeException("filmId is required");
        }
        if (days <= 0) {
            throw new RuntimeException("days must be at least 1");
        }

        Film film = filmService.getFilm(filmId);
        if (!film.isInStock()) {
            throw new RuntimeException("Film is not in stock: " + filmId);
        }

        int price = priceCalculator.totalPrice(film.getType(), days);

        film.setInStock(false);
        filmService.saveFilm(film);

        Rental rental = Rental.builder()
                .film(film)
                .paidDays(days)
                .rentedAt(LocalDate.now())
                .returnedAt(null)
                .build();

        Rental saved = rentalRepository.save(rental);

        return RentFilmResponse.builder()
                .rentalId(saved.getId())
                .filmId(film.getId())
                .title(film.getTitle())
                .type(film.getType().name())
                .days(days)
                .priceEur(price)
                .build();
    }

    public ReturnRentalResponse returnRental(Long rentalId, LocalDate returnDate) {
        if (rentalId == null) {
            throw new RuntimeException("rentalId is required");
        }

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found: " + rentalId));

        if (rental.getReturnedAt() != null) {
            throw new RuntimeException("Rental is already returned: " + rentalId);
        }

        LocalDate actualReturnDate = returnDate != null ? returnDate : LocalDate.now();
        if (actualReturnDate.isBefore(rental.getRentedAt())) {
            throw new RuntimeException("returnDate cannot be before rentedAt");
        }

        int actualDays = (int) ChronoUnit.DAYS.between(rental.getRentedAt(), actualReturnDate) + 1;
        int paidDays = rental.getPaidDays();
        int lateDays = Math.max(0, actualDays - paidDays);

        Film film = rental.getFilm();

        int paidPrice = priceCalculator.totalPrice(film.getType(), paidDays);
        int actualPrice = priceCalculator.totalPrice(film.getType(), actualDays);
        int lateFee = Math.max(0, actualPrice - paidPrice);

        rental.setReturnedAt(actualReturnDate);
        rentalRepository.save(rental);

        film.setInStock(true);
        filmService.saveFilm(film);

        return ReturnRentalResponse.builder()
                .rentalId(rental.getId())
                .filmId(film.getId())
                .title(film.getTitle())
                .paidDays(paidDays)
                .actualDays(actualDays)
                .lateDays(lateDays)
                .lateFeeEur(lateFee)
                .build();
    }
}