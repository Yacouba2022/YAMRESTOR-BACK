package yamrestor.restor.entity.traiteur;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Ligne d'un devis traiteur : une désignation (ou menu), une quantité, un prix unitaire. */
@Entity
@Table(name = "devis_lignes")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE devis_lignes SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class DevisLigneEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "devis_id", nullable = false)
    private DevisEntity devis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_traiteur_id")
    private MenuTraiteurEntity menuTraiteur;

    @Column(nullable = false)
    private String designation;

    @Column(precision = 15, scale = 2)
    private BigDecimal quantite = BigDecimal.ONE;

    @Column(precision = 15, scale = 2)
    private BigDecimal prixUnitaire = BigDecimal.ZERO;

    @Column(precision = 15, scale = 2)
    private BigDecimal montantLigne = BigDecimal.ZERO;
}
