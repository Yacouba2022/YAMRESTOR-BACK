package yamrestor.restor.repository.crm;

import yamrestor.restor.entity.crm.BonAchatEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BonAchatRepository extends JpaRepository<BonAchatEntity, Long> {

    @EntityGraph(attributePaths = {"client"})
    Optional<BonAchatEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"client"})
    List<BonAchatEntity> findByClientGuidOrderByCreatedAtDesc(String clientGuid);
}
