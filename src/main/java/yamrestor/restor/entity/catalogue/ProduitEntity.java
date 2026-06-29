package yamrestor.restor.entity.catalogue;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.TypeProduit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Produit vendu : plat, boisson, dessert, menu, cocktail… */
@Entity
@Table(name = "produits")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE produits SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ProduitEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Column(unique = true)
    private String code;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id")
    private CategorieEntity categorie;

    @Enumerated(EnumType.STRING)
    private TypeProduit type = TypeProduit.PLAT;

    @Column(precision = 15, scale = 2)
    private BigDecimal prixVente;

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxTva;

    private String image;

    /** Disponible à la vente (rupture, hors saison…). */
    private Boolean disponible = true;

    /** Temps de préparation estimé, en minutes. */
    private Integer tempsPreparation;

    private Boolean saisonnier = false;

    private Boolean actif = true;
}
