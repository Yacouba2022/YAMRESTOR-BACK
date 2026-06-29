package yamrestor.restor.entity.catalogue;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

/**
 * Recette d'un produit (plat) : liste des matières premières utilisées avec leurs quantités.
 * Le coût de revient et la marge sont calculés à la volée (cf. RecetteMapper).
 */
@Entity
@Table(name = "recettes")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE recettes SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class RecetteEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private ProduitEntity produit;

    /** Temps de préparation de la recette, en minutes. */
    private Integer tempsPreparation;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RecetteLigneEntity> lignes = new ArrayList<>();
}
