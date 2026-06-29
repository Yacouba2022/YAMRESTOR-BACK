package yamrestor.restor.repository.catalogue;

import yamrestor.restor.entity.catalogue.RecetteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecetteRepository extends JpaRepository<RecetteEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"produit"})
    Page<RecetteEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"produit", "lignes", "lignes.matierePremiere", "lignes.unite"})
    Optional<RecetteEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"produit", "lignes", "lignes.matierePremiere", "lignes.unite"})
    Optional<RecetteEntity> findByProduitGuid(String produitGuid);

    boolean existsByProduitGuid(String produitGuid);
}
