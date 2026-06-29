package yamrestor.restor.entity.traiteur;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.ModePaiement;
import yamrestor.restor.enums.StatutPaiement;
import yamrestor.restor.enums.TypePaiementPrestation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Paiement d'une prestation traiteur (acompte ou solde). */
@Entity
@Table(name = "paiements_prestation")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE paiements_prestation SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class PaiementPrestationEntity extends BaseEntity {

    @Column(unique = true)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestation_id", nullable = false)
    private PrestationEntity prestation;

    @Column(precision = 15, scale = 2)
    private BigDecimal montant = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModePaiement mode = ModePaiement.ESPECES;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypePaiementPrestation type = TypePaiementPrestation.ACOMPTE;

    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPaiement statut = StatutPaiement.VALIDE;
}
