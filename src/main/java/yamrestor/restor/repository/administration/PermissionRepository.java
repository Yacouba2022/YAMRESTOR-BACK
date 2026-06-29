package yamrestor.restor.repository.administration;

import yamrestor.restor.entity.administration.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    Optional<PermissionEntity> findByCode(String code);
    Optional<PermissionEntity> findByGuid(String guid);
    boolean existsByCode(String code);
}
