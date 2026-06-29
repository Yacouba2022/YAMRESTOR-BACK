package yamrestor.restor.entity.traiteur;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.StatutContrat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Contrat traiteur, issu de la conversion d'un devis. */
@Entity
@Table(name = "contrats")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE contrats SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ContratEntity extends BaseEntity {

    @Column(unique = true)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestation_id", nullable = false)
    private PrestationEntity prestation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "devis_id")
    private DevisEntity devis;

    @Column(precision = 15, scale = 2)
    private BigDecimal montant = BigDecimal.ZERO;

    @Column(columnDefinition = "TEXT")
    private String conditions;

    private LocalDate dateSignature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutContrat statut = StatutContrat.BROUILLON;
}
