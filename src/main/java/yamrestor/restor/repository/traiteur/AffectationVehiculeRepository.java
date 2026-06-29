package yamrestor.restor.repository.traiteur;

import yamrestor.restor.entity.traiteur.AffectationVehiculeEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AffectationVehiculeRepository extends JpaRepository<AffectationVehiculeEntity, Long> {

    @EntityGraph(attributePaths = {"vehicule", "prestation"})
    Optional<AffectationVehiculeEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"vehicule"})
    List<AffectationVehiculeEntity> findByPrestationGuid(String prestationGuid);
}
