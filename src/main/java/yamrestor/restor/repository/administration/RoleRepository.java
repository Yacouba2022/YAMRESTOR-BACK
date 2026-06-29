package yamrestor.restor.repository.administration;

import yamrestor.restor.entity.administration.RoleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByCode(String code);
    Optional<RoleEntity> findByGuid(String guid);
    boolean existsByCode(String code);

    @Query("SELECT r FROM RoleEntity r WHERE r.isActive = true AND (:q = '' "
         + "OR LOWER(r.nom) LIKE LOWER(CONCAT('%', :q, '%')) "
         + "OR LOWER(r.code) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY r.nom")
    List<RoleEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
