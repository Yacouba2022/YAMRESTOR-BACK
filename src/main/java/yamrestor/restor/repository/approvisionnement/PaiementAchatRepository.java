package yamrestor.restor.repository.approvisionnement;

import yamrestor.restor.entity.approvisionnement.PaiementAchatEntity;
import yamrestor.restor.enums.StatutPaiement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaiementAchatRepository extends JpaRepository<PaiementAchatEntity, Long> {

    @EntityGraph(attributePaths = {"fournisseur", "commandeAchat"})
    Optional<PaiementAchatEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"fournisseur", "commandeAchat"})
    @Query(value = "SELECT p FROM PaiementAchatEntity p WHERE (:statut IS NULL OR p.statut = :statut) ORDER BY p.createdAt DESC",
           countQuery = "SELECT COUNT(p) FROM PaiementAchatEntity p WHERE (:statut IS NULL OR p.statut = :statut)")
    Page<PaiementAchatEntity> search(@Param("statut") StatutPaiement statut, Pageable pageable);
}
