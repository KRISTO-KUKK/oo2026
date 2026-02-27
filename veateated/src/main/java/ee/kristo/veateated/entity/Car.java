package ee.kristo.veateated.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cars", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cars_vin", columnNames = "vin")
})
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mark (make) ei tohi olla tühi.")
    @Size(max = 50, message = "Mark (make) võib olla kuni 50 tähemärki.")
    private String make;

    @NotBlank(message = "Mudel (model) ei tohi olla tühi.")
    @Size(max = 50, message = "Mudel (model) võib olla kuni 50 tähemärki.")
    private String model;

    @Min(value = 1886, message = "Aasta (year) peab olema vähemalt 1886.")
    @Max(value = 2100, message = "Aasta (year) ei tohi olla suurem kui 2100.")
    private int year;

    @Positive(message = "Hind (price) peab olema positiivne number.")
    private double price;

    @NotBlank(message = "VIN ei tohi olla tühi.")
    @Pattern(regexp = "^[A-HJ-NPR-Z0-9]{17}$",
            message = "VIN peab olema 17 tähemärki (ilma I, O, Q) ja ainult suured tähed/numbrid.")
    private String vin;

    @NotBlank(message = "Registreerimisnumber (regNumber) ei tohi olla tühi.")
    @Pattern(regexp = "^[A-Z0-9\\-]{2,10}$",
            message = "Registreerimisnumber peab olema 2–10 märki ja sisaldama ainult A-Z, 0-9 või '-'.")
    private String regNumber;
}