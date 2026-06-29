package yamrestor.restor.repository.salle;

import yamrestor.restor.entity.salle.CommandeLigneEntity;
import yamrestor.restor.enums.StatutPreparation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommandeLigneRepository extends JpaRepository<CommandeLigneEntity, Long> {

    @EntityGraph(attributePaths = {"commande", "commande.table", "produit"})
    Optional<CommandeLigneEntity> findByGuid(String guid);

    /**
     * Lignes des commandes ENVOYEE dont l'état de préparation est dans la liste donnée
     * (écran cuisine). Triées par priorité puis ancienneté.
     */
    @EntityGraph(attributePaths = {"commande", "commande.table", "produit"})
    @Query("SELECT l FROM CommandeLigneEntity l JOIN l.commande c "
         + "WHERE c.statut = yamrestor.restor.enums.StatutCommande.ENVOYEE "
         + "AND l.statutPreparation IN :statuts "
         + "ORDER BY c.priorite DESC, c.createdAt ASC")
    List<CommandeLigneEntity> findPourCuisine(@Param("statuts") List<StatutPreparation> statuts);

    /** Top produits vendus : [produitNom, quantité totale, montant total]. */
    @Query("SELECT l.produitNom, SUM(l.quantite), SUM(l.montantLigne) FROM CommandeLigneEntity l JOIN l.commande c "
         + "WHERE c.statut <> yamrestor.restor.enums.StatutCommande.ANNULEE "
         + "AND c.createdAt >= :debut AND c.createdAt <= :fin "
         + "GROUP BY l.produitNom ORDER BY SUM(l.quantite) DESC")
    List<Object[]> topProduits(@Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin, Pageable pageable);

    /** Ventes par catégorie : [catégorieNom, montant total]. */
    @Query("SELECT cat.nom, SUM(l.montantLigne) FROM CommandeLigneEntity l JOIN l.commande c "
         + "JOIN l.produit p LEFT JOIN p.categorie cat "
         + "WHERE c.statut <> yamrestor.restor.enums.StatutCommande.ANNULEE "
         + "AND c.createdAt >= :debut AND c.createdAt <= :fin "
         + "GROUP BY cat.nom ORDER BY SUM(l.montantLigne) DESC")
    List<Object[]> ventesParCategorie(@Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin);

    /** TVA collectée estimée sur la période = somme(montantLigne × tauxTva / 100). */
    @Query("SELECT COALESCE(SUM(l.montantLigne * p.tauxTva / 100), 0) FROM CommandeLigneEntity l "
         + "JOIN l.commande c JOIN l.produit p "
         + "WHERE c.statut <> yamrestor.restor.enums.StatutCommande.ANNULEE AND p.tauxTva IS NOT NULL "
         + "AND c.createdAt >= :debut AND c.createdAt <= :fin")
    BigDecimal tvaCollectee(@Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin);
}
