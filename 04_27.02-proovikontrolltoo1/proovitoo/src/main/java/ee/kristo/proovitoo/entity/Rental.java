package ee.kristo.proovitoo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "rentals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Film film;

    @Column(nullable = false)
    private int paidDays;

    @Column(nullable = false)
    private LocalDate rentedAt;

    private LocalDate returnedAt;
}