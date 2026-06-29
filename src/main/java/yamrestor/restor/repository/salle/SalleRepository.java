package yamrestor.restor.repository.salle;

import yamrestor.restor.entity.salle.SalleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SalleRepository extends JpaRepository<SalleEntity, Long> {
    Optional<SalleEntity> findByGuid(String guid);
    List<SalleEntity> findByActifTrue();

    @Query("SELECT s FROM SalleEntity s WHERE s.actif = true AND (:q = '' "
         + "OR LOWER(s.nom) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY s.nom")
    List<SalleEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
