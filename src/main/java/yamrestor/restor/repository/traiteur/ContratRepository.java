package yamrestor.restor.repository.traiteur;

import yamrestor.restor.entity.traiteur.ContratEntity;
import yamrestor.restor.enums.StatutContrat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContratRepository extends JpaRepository<ContratEntity, Long> {

    @EntityGraph(attributePaths = {"prestation", "devis"})
    Optional<ContratEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"prestation"})
    @Query(value = "SELECT c FROM ContratEntity c WHERE (:statut IS NULL OR c.statut = :statut) ORDER BY c.createdAt DESC",
           countQuery = "SELECT COUNT(c) FROM ContratEntity c WHERE (:statut IS NULL OR c.statut = :statut)")
    Page<ContratEntity> search(@Param("statut") StatutContrat statut, Pageable pageable);
}
