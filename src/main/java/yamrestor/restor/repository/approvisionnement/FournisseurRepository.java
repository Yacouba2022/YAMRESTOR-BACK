package yamrestor.restor.repository.approvisionnement;

import yamrestor.restor.entity.approvisionnement.FournisseurEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<FournisseurEntity, Long> {
    Optional<FournisseurEntity> findByGuid(String guid);
    List<FournisseurEntity> findByActifTrue();

    @Query("SELECT f FROM FournisseurEntity f WHERE f.actif = true AND (:q = '' "
         + "OR LOWER(f.nom) LIKE LOWER(CONCAT('%', :q, '%')) "
         + "OR (f.telephone IS NOT NULL AND LOWER(f.telephone) LIKE LOWER(CONCAT('%', :q, '%')))) ORDER BY f.nom")
    List<FournisseurEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
