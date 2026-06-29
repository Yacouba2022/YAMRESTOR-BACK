package yamrestor.restor.repository.catalogue;

import yamrestor.restor.entity.catalogue.MatierePremiereEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MatierePremiereRepository extends JpaRepository<MatierePremiereEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"unite"})
    Page<MatierePremiereEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"unite"})
    Optional<MatierePremiereEntity> findByGuid(String guid);

    boolean existsByCode(String code);

    @Query(value =
        "SELECT m FROM MatierePremiereEntity m LEFT JOIN m.unite u " +
        "WHERE (:q = '' OR LOWER(m.nom) LIKE LOWER(CONCAT('%', :q, '%')) " +
        "   OR (m.code IS NOT NULL AND LOWER(m.code) LIKE LOWER(CONCAT('%', :q, '%')))) " +
        "AND (:uniteGuid IS NULL OR u.guid = :uniteGuid) " +
        "AND (:actif IS NULL OR m.actif = :actif) " +
        "AND (:stockFaible = false OR (m.seuilAlerte IS NOT NULL AND m.stock <= m.seuilAlerte))",
        countQuery =
        "SELECT COUNT(m) FROM MatierePremiereEntity m LEFT JOIN m.unite u " +
        "WHERE (:q = '' OR LOWER(m.nom) LIKE LOWER(CONCAT('%', :q, '%')) " +
        "   OR (m.code IS NOT NULL AND LOWER(m.code) LIKE LOWER(CONCAT('%', :q, '%')))) " +
        "AND (:uniteGuid IS NULL OR u.guid = :uniteGuid) " +
        "AND (:actif IS NULL OR m.actif = :actif) " +
        "AND (:stockFaible = false OR (m.seuilAlerte IS NOT NULL AND m.stock <= m.seuilAlerte))")
    @EntityGraph(attributePaths = {"unite"})
    Page<MatierePremiereEntity> search(@Param("q") String q,
                                       @Param("uniteGuid") String uniteGuid,
                                       @Param("actif") Boolean actif,
                                       @Param("stockFaible") boolean stockFaible,
                                       Pageable pageable);

    @Query("SELECT m FROM MatierePremiereEntity m WHERE m.actif = true AND (:q = '' "
         + "OR LOWER(m.nom) LIKE LOWER(CONCAT('%', :q, '%')) "
         + "OR LOWER(m.code) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY m.nom")
    List<MatierePremiereEntity> autocomplete(@Param("q") String q, Pageable pageable);

    /** Matières dont le stock est au plus égal au seuil d'alerte. */
    @EntityGraph(attributePaths = {"unite"})
    @Query("SELECT m FROM MatierePremiereEntity m WHERE m.actif = true "
         + "AND m.seuilAlerte IS NOT NULL AND m.stock <= m.seuilAlerte ORDER BY m.nom")
    List<MatierePremiereEntity> findEnAlerte();

    /** Valorisation totale du stock = somme(stock × prix d'achat) sur les matières actives. */
    @Query("SELECT COALESCE(SUM(m.stock * m.prixAchat), 0) FROM MatierePremiereEntity m "
         + "WHERE m.actif = true AND m.prixAchat IS NOT NULL AND m.stock IS NOT NULL")
    java.math.BigDecimal valorisationTotale();

    /** Nombre de matières sous le seuil d'alerte. */
    @Query("SELECT COUNT(m) FROM MatierePremiereEntity m WHERE m.actif = true "
         + "AND m.seuilAlerte IS NOT NULL AND m.stock <= m.seuilAlerte")
    long countEnAlerte();

    /** Matières dont la date de péremption est échue ou proche (≤ date donnée). */
    @Query("SELECT m FROM MatierePremiereEntity m WHERE m.actif = true "
         + "AND m.datePeremption IS NOT NULL AND m.datePeremption <= :date ORDER BY m.datePeremption")
    List<MatierePremiereEntity> findPerimantAvant(@Param("date") java.time.LocalDate date);
}
