package ee.kristokukk.tahetuvastus.repository;

import ee.kristokukk.tahetuvastus.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
}