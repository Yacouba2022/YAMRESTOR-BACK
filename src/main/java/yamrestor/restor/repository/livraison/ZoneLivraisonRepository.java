package yamrestor.restor.repository.livraison;

import yamrestor.restor.entity.livraison.ZoneLivraisonEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ZoneLivraisonRepository extends JpaRepository<ZoneLivraisonEntity, Long> {
    Optional<ZoneLivraisonEntity> findByGuid(String guid);
    List<ZoneLivraisonEntity> findByActifTrue();

    @Query("SELECT z FROM ZoneLivraisonEntity z WHERE z.actif = true AND (:q = '' "
         + "OR LOWER(z.nom) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY z.nom")
    List<ZoneLivraisonEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
