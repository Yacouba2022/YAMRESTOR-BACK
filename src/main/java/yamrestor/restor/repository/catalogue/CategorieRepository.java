package yamrestor.restor.repository.catalogue;

import yamrestor.restor.entity.catalogue.CategorieEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategorieRepository extends JpaRepository<CategorieEntity, Long> {
    Optional<CategorieEntity> findByGuid(String guid);
    List<CategorieEntity> findByActifTrue();
    boolean existsByCode(String code);

    @Query("SELECT c FROM CategorieEntity c WHERE c.actif = true AND (:q = '' "
         + "OR LOWER(c.nom) LIKE LOWER(CONCAT('%', :q, '%')) "
         + "OR LOWER(c.code) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY c.nom")
    List<CategorieEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
