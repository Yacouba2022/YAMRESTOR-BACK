package yamrestor.restor.repository.approvisionnement;

import yamrestor.restor.entity.approvisionnement.CommandeAchatEntity;
import yamrestor.restor.enums.StatutCommandeAchat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommandeAchatRepository extends JpaRepository<CommandeAchatEntity, Long> {

    @EntityGraph(attributePaths = {"fournisseur", "lignes", "lignes.matierePremiere"})
    Optional<CommandeAchatEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"fournisseur"})
    @Query(value = "SELECT c FROM CommandeAchatEntity c WHERE (:statut IS NULL OR c.statut = :statut) ORDER BY c.createdAt DESC",
           countQuery = "SELECT COUNT(c) FROM CommandeAchatEntity c WHERE (:statut IS NULL OR c.statut = :statut)")
    Page<CommandeAchatEntity> search(@Param("statut") StatutCommandeAchat statut, Pageable pageable);
}
