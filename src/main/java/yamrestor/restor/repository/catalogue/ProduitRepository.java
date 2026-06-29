package yamrestor.restor.repository.catalogue;

import yamrestor.restor.entity.catalogue.ProduitEntity;
import yamrestor.restor.enums.TypeProduit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends JpaRepository<ProduitEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"categorie"})
    Page<ProduitEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"categorie"})
    Optional<ProduitEntity> findByGuid(String guid);

    boolean existsByCode(String code);

    @Query(value =
        "SELECT p FROM ProduitEntity p LEFT JOIN p.categorie c " +
        "WHERE (:q = '' OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :q, '%')) " +
        "   OR (p.code IS NOT NULL AND LOWER(p.code) LIKE LOWER(CONCAT('%', :q, '%')))) " +
        "AND (:categorieGuid IS NULL OR c.guid = :categorieGuid) " +
        "AND (:type IS NULL OR p.type = :type) " +
        "AND (:disponible IS NULL OR p.disponible = :disponible) " +
        "AND (:actif IS NULL OR p.actif = :actif)",
        countQuery =
        "SELECT COUNT(p) FROM ProduitEntity p LEFT JOIN p.categorie c " +
        "WHERE (:q = '' OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :q, '%')) " +
        "   OR (p.code IS NOT NULL AND LOWER(p.code) LIKE LOWER(CONCAT('%', :q, '%')))) " +
        "AND (:categorieGuid IS NULL OR c.guid = :categorieGuid) " +
        "AND (:type IS NULL OR p.type = :type) " +
        "AND (:disponible IS NULL OR p.disponible = :disponible) " +
        "AND (:actif IS NULL OR p.actif = :actif)")
    @EntityGraph(attributePaths = {"categorie"})
    Page<ProduitEntity> search(@Param("q") String q,
                               @Param("categorieGuid") String categorieGuid,
                               @Param("type") TypeProduit type,
                               @Param("disponible") Boolean disponible,
                               @Param("actif") Boolean actif,
                               Pageable pageable);

    @Query("SELECT p FROM ProduitEntity p WHERE p.actif = true AND (:q = '' "
         + "OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :q, '%')) "
         + "OR LOWER(p.code) LIKE LOWER(CONCAT('%', :q, '%'))) ORDER BY p.nom")
    List<ProduitEntity> autocomplete(@Param("q") String q, Pageable pageable);
}
