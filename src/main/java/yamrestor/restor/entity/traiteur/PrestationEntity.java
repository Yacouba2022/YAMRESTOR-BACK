package yamrestor.restor.entity.traiteur;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.entity.administration.UserEntity;
import yamrestor.restor.enums.StatutPrestation;
import yamrestor.restor.enums.TypeEvenement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/** Prestation traiteur : un évènement à organiser, de la demande à la réalisation. */
@Entity
@Table(name = "prestations")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE prestations SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class PrestationEntity extends BaseEntity {

    @Column(unique = true)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeEvenement typeEvenement = TypeEvenement.FETE_PRIVEE;

    @Column(nullable = false)
    private LocalDate dateEvenement;

    private LocalTime heure;
    private String lieu;

    private String clientNom;
    private String clientTelephone;

    /** Responsable de la prestation (utilisateur). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsable_id")
    private UserEntity responsable;

    private Integer nombreConvivesPrevu;
    private Integer nombreAdultes;
    private Integer nombreEnfants;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPrestation statut = StatutPrestation.DEVIS;

    /** Montant facturé (issu du devis/contrat validé). */
    @Column(precision = 15, scale = 2)
    private BigDecimal montantTotal = BigDecimal.ZERO;

    /** Montant déjà encaissé (acomptes + solde). */
    @Column(precision = 15, scale = 2)
    private BigDecimal montantPaye = BigDecimal.ZERO;

    // ─── Coûts (pour le calcul de rentabilité) ───────────────────────────────
    @Column(precision = 15, scale = 2)
    private BigDecimal coutMatieresPremieres = BigDecimal.ZERO;
    @Column(precision = 15, scale = 2)
    private BigDecimal coutPersonnel = BigDecimal.ZERO;
    @Column(precision = 15, scale = 2)
    private BigDecimal coutTransport = BigDecimal.ZERO;
    @Column(precision = 15, scale = 2)
    private BigDecimal coutMateriel = BigDecimal.ZERO;
    @Column(precision = 15, scale = 2)
    private BigDecimal coutDivers = BigDecimal.ZERO;
}
