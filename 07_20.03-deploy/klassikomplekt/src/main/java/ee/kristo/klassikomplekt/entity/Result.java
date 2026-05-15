package ee.kristo.klassikomplekt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SportEvent event;

    @Column(nullable = false)
    private double performance;

    @Column(nullable = false)
    private int points;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private Athlete athlete;
}
