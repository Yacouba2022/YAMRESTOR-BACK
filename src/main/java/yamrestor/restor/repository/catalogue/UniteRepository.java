package yamrestor.restor.repository.catalogue;

import yamrestor.restor.entity.catalogue.UniteEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UniteRepository extends JpaRepository<UniteEntity, Long> {
    Optional<UniteEntity> findByGuid(String guid);
    List<UniteEntity> findByActifTrue();
    boolean existsByCode(String code);

    @Query("SELECT u FROM UniteEntity u WHERE u.actif = true AND (:q = '' "
         + "OR LOWER(u.nom) LIKE LOWER(CONCAT('%', :q, '%')) "
         + "OR LOWER(u.code) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY u.nom")
    List<UniteEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
