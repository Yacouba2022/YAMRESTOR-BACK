package yamrestor.restor.repository.administration;

import yamrestor.restor.entity.administration.ImprimanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImprimanteRepository extends JpaRepository<ImprimanteEntity, Long> {
    Optional<ImprimanteEntity> findByGuid(String guid);
    List<ImprimanteEntity> findByActifTrue();
}
