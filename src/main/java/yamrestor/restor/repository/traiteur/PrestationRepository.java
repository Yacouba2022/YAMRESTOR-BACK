package yamrestor.restor.repository.traiteur;

import yamrestor.restor.entity.traiteur.PrestationEntity;
import yamrestor.restor.enums.StatutPrestation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PrestationRepository extends JpaRepository<PrestationEntity, Long> {

    @EntityGraph(attributePaths = {"responsable"})
    Optional<PrestationEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"responsable"})
    @Query(value =
        "SELECT p FROM PrestationEntity p " +
        "WHERE (:statut IS NULL OR p.statut = :statut) " +
        "AND (:date IS NULL OR p.dateEvenement = :date) ORDER BY p.dateEvenement DESC",
        countQuery =
        "SELECT COUNT(p) FROM PrestationEntity p " +
        "WHERE (:statut IS NULL OR p.statut = :statut) " +
        "AND (:date IS NULL OR p.dateEvenement = :date)")
    Page<PrestationEntity> search(@Param("statut") StatutPrestation statut,
                                  @Param("date") LocalDate date,
                                  Pageable pageable);

    /** Prestations (hors annulées) dont la date d'évènement tombe dans la période — calendrier. */
    @Query("SELECT p FROM PrestationEntity p WHERE p.dateEvenement >= :debut AND p.dateEvenement <= :fin "
         + "AND p.statut <> yamrestor.restor.enums.StatutPrestation.ANNULEE ORDER BY p.dateEvenement")
    List<PrestationEntity> entrePeriode(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);

    /** Toutes les prestations (tous statuts) sur la période — rapports traiteur. */
    @Query("SELECT p FROM PrestationEntity p WHERE p.dateEvenement >= :debut AND p.dateEvenement <= :fin")
    List<PrestationEntity> toutesEntre(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
}
