package yamrestor.restor.entity.caisse;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.entity.salle.CommandeEntity;
import yamrestor.restor.enums.StatutPaiement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Paiement d'une commande, éventuellement réparti sur plusieurs modes (paiement mixte). */
@Entity
@Table(name = "paiements")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE paiements SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class PaiementEntity extends BaseEntity {

    @Column(unique = true)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    private CommandeEntity commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_caisse_id")
    private SessionCaisseEntity sessionCaisse;

    /** Montant total réglé (= somme des lignes). */
    @Column(precision = 15, scale = 2)
    private BigDecimal montant = BigDecimal.ZERO;

    /** Monnaie rendue (pour les espèces). */
    @Column(precision = 15, scale = 2)
    private BigDecimal rendu = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPaiement statut = StatutPaiement.VALIDE;

    @OneToMany(mappedBy = "paiement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PaiementLigneEntity> lignes = new ArrayList<>();
}
