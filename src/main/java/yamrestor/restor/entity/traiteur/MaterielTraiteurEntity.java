package yamrestor.restor.entity.traiteur;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.EtatMateriel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/** Matériel propre du traiteur affecté aux prestations : marmites, réchauds, glacières, vaisselle… */
@Entity
@Table(name = "materiel_traiteur")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE materiel_traiteur SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class MaterielTraiteurEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    private String categorie;

    @Column(nullable = false)
    private Integer quantiteTotale = 0;

    @Column(nullable = false)
    private Integer quantiteDisponible = 0;

    @Enumerated(EnumType.STRING)
    private EtatMateriel etat = EtatMateriel.BON;

    private Boolean actif = true;
}
