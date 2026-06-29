package yamrestor.restor.repository.crm;

import yamrestor.restor.entity.crm.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<PromotionEntity, Long> {
    Optional<PromotionEntity> findByGuid(String guid);
    Optional<PromotionEntity> findByCode(String code);
    List<PromotionEntity> findByActifTrue();
    boolean existsByCode(String code);
}
