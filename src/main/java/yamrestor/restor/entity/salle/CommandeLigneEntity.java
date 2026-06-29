package yamrestor.restor.entity.salle;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.entity.catalogue.ProduitEntity;
import yamrestor.restor.enums.StatutPreparation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Ligne de commande : un produit, une quantité, un prix unitaire (figé à la prise de commande). */
@Entity
@Table(name = "commande_lignes")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE commande_lignes SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class CommandeLigneEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    private CommandeEntity commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id")
    private ProduitEntity produit;

    /** Libellé du produit figé au moment de la commande. */
    private String produitNom;

    @Column(nullable = false)
    private Integer quantite = 1;

    @Column(precision = 15, scale = 2)
    private BigDecimal prixUnitaire = BigDecimal.ZERO;

    @Column(precision = 15, scale = 2)
    private BigDecimal montantLigne = BigDecimal.ZERO;

    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPreparation statutPreparation = StatutPreparation.EN_ATTENTE;
}
