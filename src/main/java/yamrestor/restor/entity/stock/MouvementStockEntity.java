package yamrestor.restor.entity.stock;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.entity.catalogue.MatierePremiereEntity;
import yamrestor.restor.enums.TypeMouvement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Mouvement de stock d'une matière première (entrée, sortie, ajustement, consommation). */
@Entity
@Table(name = "mouvements_stock")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE mouvements_stock SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class MouvementStockEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_premiere_id", nullable = false)
    private MatierePremiereEntity matierePremiere;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeMouvement type;

    /** Variation signée appliquée au stock (positive pour une entrée, négative pour une sortie). */
    @Column(precision = 15, scale = 3)
    private BigDecimal quantite;

    @Column(precision = 15, scale = 3)
    private BigDecimal stockAvant;

    @Column(precision = 15, scale = 3)
    private BigDecimal stockApres;

    private String motif;

    /** Référence du document à l'origine (n° commande, commande d'achat…). */
    private String reference;
}
