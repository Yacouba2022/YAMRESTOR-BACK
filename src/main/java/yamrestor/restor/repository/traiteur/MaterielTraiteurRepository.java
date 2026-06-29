package yamrestor.restor.repository.traiteur;

import yamrestor.restor.entity.traiteur.MaterielTraiteurEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MaterielTraiteurRepository extends JpaRepository<MaterielTraiteurEntity, Long> {
    Optional<MaterielTraiteurEntity> findByGuid(String guid);
    List<MaterielTraiteurEntity> findByActifTrue();

    @Query("SELECT m FROM MaterielTraiteurEntity m WHERE m.actif = true AND (:q = '' "
         + "OR LOWER(m.nom) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY m.nom")
    List<MaterielTraiteurEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
