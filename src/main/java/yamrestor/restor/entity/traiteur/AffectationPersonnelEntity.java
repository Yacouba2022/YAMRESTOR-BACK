package yamrestor.restor.entity.traiteur;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/** Affectation d'un membre du personnel à une prestation. */
@Entity
@Table(name = "affectations_personnel")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE affectations_personnel SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class AffectationPersonnelEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestation_id", nullable = false)
    private PrestationEntity prestation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_id", nullable = false)
    private PersonnelTraiteurEntity personnel;

    /** Rôle sur la prestation (ex. « Chef de cuisine », « Chef de rang »). */
    private String role;
}
