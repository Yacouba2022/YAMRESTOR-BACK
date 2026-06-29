package yamrestor.restor.repository.traiteur;

import yamrestor.restor.entity.traiteur.VehiculeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehiculeRepository extends JpaRepository<VehiculeEntity, Long> {
    Optional<VehiculeEntity> findByGuid(String guid);
    List<VehiculeEntity> findByActifTrue();

    @Query("SELECT v FROM VehiculeEntity v WHERE v.actif = true AND (:q = '' "
         + "OR LOWER(v.nom) LIKE LOWER(CONCAT('%', :q, '%')) "
         + "OR (v.immatriculation IS NOT NULL AND LOWER(v.immatriculation) LIKE LOWER(CONCAT('%', :q, '%')))) ORDER BY v.nom")
    List<VehiculeEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
