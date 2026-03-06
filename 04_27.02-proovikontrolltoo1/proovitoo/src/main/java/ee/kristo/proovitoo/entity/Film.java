package ee.kristo.proovitoo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "films")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FilmType type;

    @Column(name = "in_stock", nullable = false)
    private boolean inStock;
}
