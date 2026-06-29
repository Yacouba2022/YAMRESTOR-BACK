package yamrestor.restor.repository.traiteur;

import yamrestor.restor.entity.traiteur.AffectationMaterielEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AffectationMaterielRepository extends JpaRepository<AffectationMaterielEntity, Long> {

    @EntityGraph(attributePaths = {"materiel", "prestation"})
    Optional<AffectationMaterielEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"materiel"})
    List<AffectationMaterielEntity> findByPrestationGuid(String prestationGuid);
}
