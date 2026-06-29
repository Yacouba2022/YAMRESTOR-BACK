package yamrestor.restor.entity.location;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Ligne d'un contrat de location : un matériel, une quantité, un prix unitaire. */
@Entity
@Table(name = "contrat_location_lignes")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE contrat_location_lignes SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ContratLocationLigneEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrat_location_id", nullable = false)
    private ContratLocationEntity contratLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materiel_id", nullable = false)
    private MaterielLocationEntity materiel;

    @Column(nullable = false)
    private Integer quantite = 1;

    @Column(precision = 15, scale = 2)
    private BigDecimal prixUnitaire = BigDecimal.ZERO;

    @Column(precision = 15, scale = 2)
    private BigDecimal montantLigne = BigDecimal.ZERO;

    @Column(precision = 15, scale = 2)
    private BigDecimal caution = BigDecimal.ZERO;
}
