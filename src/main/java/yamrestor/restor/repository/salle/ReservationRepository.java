package yamrestor.restor.repository.salle;

import yamrestor.restor.entity.salle.ReservationEntity;
import yamrestor.restor.enums.StatutReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @EntityGraph(attributePaths = {"table", "table.salle"})
    Optional<ReservationEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"table"})
    @Query(value =
        "SELECT r FROM ReservationEntity r " +
        "WHERE (:date IS NULL OR r.dateReservation = :date) " +
        "AND (:statut IS NULL OR r.statut = :statut) " +
        "ORDER BY r.dateReservation DESC, r.heure ASC",
        countQuery =
        "SELECT COUNT(r) FROM ReservationEntity r " +
        "WHERE (:date IS NULL OR r.dateReservation = :date) " +
        "AND (:statut IS NULL OR r.statut = :statut)")
    Page<ReservationEntity> search(@Param("date") LocalDate date,
                                   @Param("statut") StatutReservation statut,
                                   Pageable pageable);

    /** Réservations d'un jour donné (hors annulées) — pour le dashboard / notifications. */
    @EntityGraph(attributePaths = {"table"})
    @Query("SELECT r FROM ReservationEntity r WHERE r.dateReservation = :date "
         + "AND r.statut <> yamrestor.restor.enums.StatutReservation.ANNULEE ORDER BY r.heure")
    List<ReservationEntity> duJour(@Param("date") LocalDate date);

    long countByDateReservationAndStatutNot(LocalDate date, StatutReservation statut);

    /** Réservations (hors annulées) dont la date tombe dans la période — calendrier des évènements. */
    @EntityGraph(attributePaths = {"table"})
    @Query("SELECT r FROM ReservationEntity r WHERE r.dateReservation >= :debut AND r.dateReservation <= :fin "
         + "AND r.statut <> yamrestor.restor.enums.StatutReservation.ANNULEE ORDER BY r.dateReservation, r.heure")
    List<ReservationEntity> entrePeriode(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
}
