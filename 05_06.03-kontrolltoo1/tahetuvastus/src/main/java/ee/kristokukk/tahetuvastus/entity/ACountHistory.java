package ee.kristokukk.tahetuvastus.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "a_count_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ACountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int count;
}
