package yamrestor.restor.repository.stock;

import yamrestor.restor.entity.stock.MouvementStockEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MouvementStockRepository extends JpaRepository<MouvementStockEntity, Long> {

    @EntityGraph(attributePaths = {"matierePremiere"})
    @Query(value =
        "SELECT m FROM MouvementStockEntity m LEFT JOIN m.matierePremiere mp " +
        "WHERE (:matiereGuid IS NULL OR mp.guid = :matiereGuid) ORDER BY m.createdAt DESC",
        countQuery =
        "SELECT COUNT(m) FROM MouvementStockEntity m LEFT JOIN m.matierePremiere mp " +
        "WHERE (:matiereGuid IS NULL OR mp.guid = :matiereGuid)")
    Page<MouvementStockEntity> search(@Param("matiereGuid") String matiereGuid, Pageable pageable);
}
