package yamrestor.restor.entity.traiteur;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Affectation d'un véhicule (et son chauffeur) à une prestation. */
@Entity
@Table(name = "affectations_vehicule")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE affectations_vehicule SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class AffectationVehiculeEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestation_id", nullable = false)
    private PrestationEntity prestation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicule_id", nullable = false)
    private VehiculeEntity vehicule;

    private String chauffeurNom;
    private Integer kmDepart;
    private Integer kmRetour;

    @Column(precision = 15, scale = 2)
    private BigDecimal carburant;
}
