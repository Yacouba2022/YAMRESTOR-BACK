package yamrestor.restor.repository.traiteur;

import yamrestor.restor.entity.traiteur.AffectationPersonnelEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AffectationPersonnelRepository extends JpaRepository<AffectationPersonnelEntity, Long> {

    @EntityGraph(attributePaths = {"personnel", "prestation"})
    Optional<AffectationPersonnelEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"personnel"})
    List<AffectationPersonnelEntity> findByPrestationGuid(String prestationGuid);
}
