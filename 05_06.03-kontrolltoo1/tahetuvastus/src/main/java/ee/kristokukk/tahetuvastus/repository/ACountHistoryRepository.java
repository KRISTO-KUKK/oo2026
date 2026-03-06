package ee.kristokukk.tahetuvastus.repository;


import ee.kristokukk.tahetuvastus.entity.ACountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ACountHistoryRepository extends JpaRepository<ACountHistory, Long> {
}
