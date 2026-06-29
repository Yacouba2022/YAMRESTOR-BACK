package yamrestor.restor.entity.approvisionnement;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.ModePaiement;
import yamrestor.restor.enums.StatutPaiement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Paiement effectué à un fournisseur (diminue le solde dû). */
@Entity
@Table(name = "paiements_achat")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE paiements_achat SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class PaiementAchatEntity extends BaseEntity {

    @Column(unique = true)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fournisseur_id", nullable = false)
    private FournisseurEntity fournisseur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_achat_id")
    private CommandeAchatEntity commandeAchat;

    @Column(precision = 15, scale = 2)
    private BigDecimal montant = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModePaiement mode = ModePaiement.ESPECES;

    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPaiement statut = StatutPaiement.VALIDE;
}
