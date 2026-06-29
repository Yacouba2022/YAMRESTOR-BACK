package yamrestor.restor.repository.comptabilite;

import yamrestor.restor.entity.comptabilite.CategorieDepenseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategorieDepenseRepository extends JpaRepository<CategorieDepenseEntity, Long> {
    Optional<CategorieDepenseEntity> findByGuid(String guid);
    List<CategorieDepenseEntity> findByActifTrue();
    boolean existsByCode(String code);

    @Query("SELECT c FROM CategorieDepenseEntity c WHERE c.actif = true AND (:q = '' "
         + "OR LOWER(c.nom) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY c.nom")
    List<CategorieDepenseEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
