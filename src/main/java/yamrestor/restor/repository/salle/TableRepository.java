package yamrestor.restor.repository.salle;

import yamrestor.restor.entity.salle.TableEntity;
import yamrestor.restor.enums.StatutTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TableRepository extends JpaRepository<TableEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"salle"})
    Page<TableEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"salle"})
    Optional<TableEntity> findByGuid(String guid);

    boolean existsByNumero(String numero);

    @EntityGraph(attributePaths = {"salle"})
    @Query(value =
        "SELECT t FROM TableEntity t LEFT JOIN t.salle s " +
        "WHERE (:salleGuid IS NULL OR s.guid = :salleGuid) " +
        "AND (:statut IS NULL OR t.statut = :statut)",
        countQuery =
        "SELECT COUNT(t) FROM TableEntity t LEFT JOIN t.salle s " +
        "WHERE (:salleGuid IS NULL OR s.guid = :salleGuid) " +
        "AND (:statut IS NULL OR t.statut = :statut)")
    Page<TableEntity> search(@Param("salleGuid") String salleGuid,
                             @Param("statut") StatutTable statut,
                             Pageable pageable);

    @Query("SELECT t FROM TableEntity t WHERE t.actif = true AND (:q = '' "
         + "OR LOWER(t.numero) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY t.numero")
    List<TableEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
