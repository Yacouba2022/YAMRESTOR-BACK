package yamrestor.restor.repository.traiteur;

import yamrestor.restor.entity.traiteur.PersonnelTraiteurEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonnelTraiteurRepository extends JpaRepository<PersonnelTraiteurEntity, Long> {
    Optional<PersonnelTraiteurEntity> findByGuid(String guid);
    List<PersonnelTraiteurEntity> findByActifTrue();

    @Query("SELECT p FROM PersonnelTraiteurEntity p WHERE p.actif = true AND (:q = '' "
         + "OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY p.nom")
    List<PersonnelTraiteurEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
