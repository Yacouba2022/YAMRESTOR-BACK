package yamrestor.restor.repository.comptabilite;

import yamrestor.restor.entity.comptabilite.DepenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface DepenseRepository extends JpaRepository<DepenseEntity, Long> {

    @EntityGraph(attributePaths = {"categorie"})
    Optional<DepenseEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"categorie"})
    @Query(value =
        "SELECT d FROM DepenseEntity d LEFT JOIN d.categorie c " +
        "WHERE (:debut IS NULL OR d.dateDepense >= :debut) " +
        "AND (:fin IS NULL OR d.dateDepense <= :fin) " +
        "AND (:categorieGuid IS NULL OR c.guid = :categorieGuid) ORDER BY d.dateDepense DESC",
        countQuery =
        "SELECT COUNT(d) FROM DepenseEntity d LEFT JOIN d.categorie c " +
        "WHERE (:debut IS NULL OR d.dateDepense >= :debut) " +
        "AND (:fin IS NULL OR d.dateDepense <= :fin) " +
        "AND (:categorieGuid IS NULL OR c.guid = :categorieGuid)")
    Page<DepenseEntity> search(@Param("debut") LocalDate debut,
                               @Param("fin") LocalDate fin,
                               @Param("categorieGuid") String categorieGuid,
                               Pageable pageable);

    @Query("SELECT COALESCE(SUM(d.montant), 0) FROM DepenseEntity d "
         + "WHERE d.dateDepense >= :debut AND d.dateDepense <= :fin")
    BigDecimal sommeEntre(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
}
