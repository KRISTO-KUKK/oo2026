package ee.kristo.proovitoo.service;

import ee.kristo.proovitoo.entity.FilmType;
import org.springframework.stereotype.Service;

@Service
public class PriceCalculator {

    private static final int PREMIUM_PRICE = 4;
    private static final int BASIC_PRICE = 3;

    public int totalPrice(FilmType type, int days) {
        if (days <= 0) {
            throw new RuntimeException("Days must be at least 1");
        }

        return switch (type) {
            case NEW_RELEASE -> PREMIUM_PRICE * days;
            case REGULAR -> BASIC_PRICE + Math.max(0, days - 3) * BASIC_PRICE;
            case OLD -> BASIC_PRICE + Math.max(0, days - 5) * BASIC_PRICE;
        };
    }
}