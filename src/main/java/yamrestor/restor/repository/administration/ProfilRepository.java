package yamrestor.restor.repository.administration;

import yamrestor.restor.entity.administration.ProfilEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfilRepository extends JpaRepository<ProfilEntity, Long> {
    Optional<ProfilEntity> findByNom(String nom);
    Optional<ProfilEntity> findByGuid(String guid);

    @Query("SELECT p FROM ProfilEntity p WHERE p.isActive = true AND (:q = '' "
         + "OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY p.nom")
    List<ProfilEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
