package yamrestor.restor.entity.approvisionnement;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.entity.catalogue.MatierePremiereEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Ligne d'une commande d'achat : une matière première, une quantité, un prix unitaire. */
@Entity
@Table(name = "commande_achat_lignes")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE commande_achat_lignes SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class CommandeAchatLigneEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_achat_id", nullable = false)
    private CommandeAchatEntity commandeAchat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_premiere_id", nullable = false)
    private MatierePremiereEntity matierePremiere;

    @Column(precision = 15, scale = 3)
    private BigDecimal quantite;

    @Column(precision = 15, scale = 2)
    private BigDecimal prixUnitaire = BigDecimal.ZERO;

    @Column(precision = 15, scale = 2)
    private BigDecimal montantLigne = BigDecimal.ZERO;
}
