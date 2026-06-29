package yamrestor.restor.repository.livraison;

import yamrestor.restor.entity.livraison.LivraisonEntity;
import yamrestor.restor.enums.StatutLivraison;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LivraisonRepository extends JpaRepository<LivraisonEntity, Long> {

    @EntityGraph(attributePaths = {"commande", "livreur", "zone"})
    Optional<LivraisonEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"commande", "livreur", "zone"})
    @Query(value = "SELECT l FROM LivraisonEntity l WHERE (:statut IS NULL OR l.statut = :statut) ORDER BY l.createdAt DESC",
           countQuery = "SELECT COUNT(l) FROM LivraisonEntity l WHERE (:statut IS NULL OR l.statut = :statut)")
    Page<LivraisonEntity> search(@Param("statut") StatutLivraison statut, Pageable pageable);
}
