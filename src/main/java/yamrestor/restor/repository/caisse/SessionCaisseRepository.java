package yamrestor.restor.repository.caisse;

import yamrestor.restor.entity.caisse.SessionCaisseEntity;
import yamrestor.restor.enums.StatutSessionCaisse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SessionCaisseRepository extends JpaRepository<SessionCaisseEntity, Long> {

    @EntityGraph(attributePaths = {"caissier"})
    Optional<SessionCaisseEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"caissier"})
    Optional<SessionCaisseEntity> findFirstByCaissierGuidAndStatut(String caissierGuid, StatutSessionCaisse statut);

    @EntityGraph(attributePaths = {"caissier"})
    @Query(value = "SELECT s FROM SessionCaisseEntity s WHERE (:statut IS NULL OR s.statut = :statut) ORDER BY s.dateOuverture DESC",
           countQuery = "SELECT COUNT(s) FROM SessionCaisseEntity s WHERE (:statut IS NULL OR s.statut = :statut)")
    Page<SessionCaisseEntity> search(@Param("statut") StatutSessionCaisse statut, Pageable pageable);
}
