package yamrestor.restor.repository.location;

import yamrestor.restor.entity.location.ContratLocationEntity;
import yamrestor.restor.enums.StatutContratLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ContratLocationRepository extends JpaRepository<ContratLocationEntity, Long> {

    @EntityGraph(attributePaths = {"lignes", "lignes.materiel"})
    Optional<ContratLocationEntity> findByGuid(String guid);

    @Query(value = "SELECT c FROM ContratLocationEntity c WHERE (:statut IS NULL OR c.statut = :statut) ORDER BY c.createdAt DESC",
           countQuery = "SELECT COUNT(c) FROM ContratLocationEntity c WHERE (:statut IS NULL OR c.statut = :statut)")
    Page<ContratLocationEntity> search(@Param("statut") StatutContratLocation statut, Pageable pageable);

    /** Contrats dont la date de début tombe dans la période (pour le calendrier des évènements). */
    @Query("SELECT c FROM ContratLocationEntity c WHERE c.dateDebut >= :debut AND c.dateDebut <= :fin "
         + "AND c.statut <> yamrestor.restor.enums.StatutContratLocation.ANNULE ORDER BY c.dateDebut")
    List<ContratLocationEntity> entrePeriode(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
}
