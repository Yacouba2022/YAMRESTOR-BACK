package yamrestor.restor.repository.salle;

import yamrestor.restor.entity.salle.CommandeEntity;
import yamrestor.restor.enums.StatutCommande;
import yamrestor.restor.enums.TypeCommande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface CommandeRepository extends JpaRepository<CommandeEntity, Long> {

    @EntityGraph(attributePaths = {"table", "serveur", "lignes", "lignes.produit"})
    Optional<CommandeEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"table", "serveur"})
    @Query(value =
        "SELECT c FROM CommandeEntity c " +
        "WHERE (:statut IS NULL OR c.statut = :statut) " +
        "AND (:type IS NULL OR c.type = :type) " +
        "ORDER BY c.createdAt DESC",
        countQuery =
        "SELECT COUNT(c) FROM CommandeEntity c " +
        "WHERE (:statut IS NULL OR c.statut = :statut) " +
        "AND (:type IS NULL OR c.type = :type)")
    Page<CommandeEntity> search(@Param("statut") StatutCommande statut,
                                @Param("type") TypeCommande type,
                                Pageable pageable);

    @Query("SELECT COUNT(c) FROM CommandeEntity c WHERE c.statut <> yamrestor.restor.enums.StatutCommande.ANNULEE "
         + "AND c.createdAt >= :debut AND c.createdAt <= :fin")
    long countEntre(@Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin);

    @Query("SELECT COALESCE(SUM(c.montantTotal), 0) FROM CommandeEntity c "
         + "WHERE c.statut <> yamrestor.restor.enums.StatutCommande.ANNULEE "
         + "AND c.createdAt >= :debut AND c.createdAt <= :fin")
    BigDecimal chiffreAffairesEntre(@Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin);

    long countByStatut(StatutCommande statut);
}
