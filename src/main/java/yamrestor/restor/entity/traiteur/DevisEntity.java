package yamrestor.restor.entity.traiteur;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.StatutDevis;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Devis traiteur rattaché à une prestation. */
@Entity
@Table(name = "devis")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE devis SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class DevisEntity extends BaseEntity {

    @Column(unique = true)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestation_id", nullable = false)
    private PrestationEntity prestation;

    @Column(precision = 15, scale = 2)
    private BigDecimal montantTotal = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDevis statut = StatutDevis.BROUILLON;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @OneToMany(mappedBy = "devis", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DevisLigneEntity> lignes = new ArrayList<>();
}
