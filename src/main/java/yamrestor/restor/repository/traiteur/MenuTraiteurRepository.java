package yamrestor.restor.repository.traiteur;

import yamrestor.restor.entity.traiteur.MenuTraiteurEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuTraiteurRepository extends JpaRepository<MenuTraiteurEntity, Long> {
    Optional<MenuTraiteurEntity> findByGuid(String guid);
    List<MenuTraiteurEntity> findByActifTrue();

    @Query("SELECT m FROM MenuTraiteurEntity m WHERE m.actif = true AND (:q = '' "
         + "OR LOWER(m.nom) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY m.nom")
    List<MenuTraiteurEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
