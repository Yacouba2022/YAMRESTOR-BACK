package yamrestor.restor.repository.traiteur;

import yamrestor.restor.entity.traiteur.PaiementPrestationEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaiementPrestationRepository extends JpaRepository<PaiementPrestationEntity, Long> {

    @EntityGraph(attributePaths = {"prestation"})
    Optional<PaiementPrestationEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"prestation"})
    List<PaiementPrestationEntity> findByPrestationGuidOrderByCreatedAtDesc(String prestationGuid);
}
