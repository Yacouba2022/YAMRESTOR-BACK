package yamrestor.restor.entity.traiteur;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/** Affectation de matériel traiteur à une prestation (réserve la quantité). */
@Entity
@Table(name = "affectations_materiel")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE affectations_materiel SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class AffectationMaterielEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestation_id", nullable = false)
    private PrestationEntity prestation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materiel_id", nullable = false)
    private MaterielTraiteurEntity materiel;

    @Column(nullable = false)
    private Integer quantite = 1;
}
