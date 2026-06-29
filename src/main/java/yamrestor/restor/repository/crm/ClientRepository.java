package yamrestor.restor.repository.crm;

import yamrestor.restor.entity.crm.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByGuid(String guid);

    @Query(value =
        "SELECT c FROM ClientEntity c WHERE (:q = '' " +
        "OR LOWER(c.nom) LIKE LOWER(CONCAT('%', :q, '%')) " +
        "OR (c.telephone IS NOT NULL AND LOWER(c.telephone) LIKE LOWER(CONCAT('%', :q, '%')))) " +
        "ORDER BY c.nom",
        countQuery =
        "SELECT COUNT(c) FROM ClientEntity c WHERE (:q = '' " +
        "OR LOWER(c.nom) LIKE LOWER(CONCAT('%', :q, '%')) " +
        "OR (c.telephone IS NOT NULL AND LOWER(c.telephone) LIKE LOWER(CONCAT('%', :q, '%'))))")
    Page<ClientEntity> search(@Param("q") String q, Pageable pageable);

    @Query("SELECT c FROM ClientEntity c WHERE c.actif = true AND (:q = '' "
         + "OR LOWER(c.nom) LIKE LOWER(CONCAT('%', :q, '%')) "
         + "OR LOWER(c.telephone) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY c.nom")
    List<ClientEntity> autocomplete(@Param("q") String q, Pageable pageable);

    /** Clients dont l'anniversaire tombe un jour donné (mois + jour). */
    @Query("SELECT c FROM ClientEntity c WHERE c.dateNaissance IS NOT NULL "
         + "AND MONTH(c.dateNaissance) = :mois AND DAY(c.dateNaissance) = :jour ORDER BY c.nom")
    List<ClientEntity> anniversaires(@Param("mois") int mois, @Param("jour") int jour);
}
