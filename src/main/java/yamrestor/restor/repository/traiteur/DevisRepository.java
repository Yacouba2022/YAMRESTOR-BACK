package yamrestor.restor.repository.traiteur;

import yamrestor.restor.entity.traiteur.DevisEntity;
import yamrestor.restor.enums.StatutDevis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DevisRepository extends JpaRepository<DevisEntity, Long> {

    @EntityGraph(attributePaths = {"prestation", "lignes", "lignes.menuTraiteur"})
    Optional<DevisEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"prestation"})
    @Query(value = "SELECT d FROM DevisEntity d WHERE (:statut IS NULL OR d.statut = :statut) ORDER BY d.createdAt DESC",
           countQuery = "SELECT COUNT(d) FROM DevisEntity d WHERE (:statut IS NULL OR d.statut = :statut)")
    Page<DevisEntity> search(@Param("statut") StatutDevis statut, Pageable pageable);
}
