package yamrestor.restor.entity.traiteur;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.TypeVehicule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/** Véhicule du parc traiteur (camion, fourgonnette, véhicule léger). */
@Entity
@Table(name = "vehicules")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE vehicules SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class VehiculeEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Column(unique = true)
    private String immatriculation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeVehicule type = TypeVehicule.FOURGONNETTE;

    private Integer kilometrage;

    private Boolean actif = true;
}
