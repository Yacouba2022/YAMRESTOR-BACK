package yamrestor.restor.repository.administration;

import yamrestor.restor.entity.administration.ParametreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParametreRepository extends JpaRepository<ParametreEntity, Long> {
    Optional<ParametreEntity> findTopByOrderByIdAsc();
}
