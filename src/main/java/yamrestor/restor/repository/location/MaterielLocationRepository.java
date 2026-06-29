package yamrestor.restor.repository.location;

import yamrestor.restor.entity.location.MaterielLocationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MaterielLocationRepository extends JpaRepository<MaterielLocationEntity, Long> {
    Optional<MaterielLocationEntity> findByGuid(String guid);
    List<MaterielLocationEntity> findByActifTrue();

    @Query("SELECT m FROM MaterielLocationEntity m WHERE m.actif = true AND (:q = '' "
         + "OR LOWER(m.nom) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY m.nom")
    List<MaterielLocationEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
