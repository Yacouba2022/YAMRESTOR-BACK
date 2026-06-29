package yamrestor.restor.repository.caisse;

import yamrestor.restor.entity.caisse.PaiementEntity;
import yamrestor.restor.enums.StatutPaiement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaiementRepository extends JpaRepository<PaiementEntity, Long> {

    @EntityGraph(attributePaths = {"commande", "sessionCaisse", "lignes"})
    Optional<PaiementEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"lignes"})
    List<PaiementEntity> findBySessionCaisseGuidAndStatut(String sessionGuid, StatutPaiement statut);

    @EntityGraph(attributePaths = {"lignes"})
    List<PaiementEntity> findByCommandeGuidAndStatut(String commandeGuid, StatutPaiement statut);

    @EntityGraph(attributePaths = {"commande", "sessionCaisse", "lignes"})
    @Query(value = "SELECT p FROM PaiementEntity p WHERE (:statut IS NULL OR p.statut = :statut) ORDER BY p.createdAt DESC",
           countQuery = "SELECT COUNT(p) FROM PaiementEntity p WHERE (:statut IS NULL OR p.statut = :statut)")
    Page<PaiementEntity> search(@Param("statut") StatutPaiement statut, Pageable pageable);

    /** Recettes encaissées (paiements validés) sur la période. */
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM PaiementEntity p "
         + "WHERE p.statut = yamrestor.restor.enums.StatutPaiement.VALIDE "
         + "AND p.createdAt >= :debut AND p.createdAt <= :fin")
    BigDecimal recettesEntre(@Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin);
}
