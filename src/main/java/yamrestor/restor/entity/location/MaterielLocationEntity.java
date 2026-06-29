package yamrestor.restor.entity.location;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.EtatMateriel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Matériel proposé à la location : tables, chaises, tentes, vaisselle, sonorisation, groupes électrogènes… */
@Entity
@Table(name = "materiels_location")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE materiels_location SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class MaterielLocationEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    /** Catégorie libre : Mobilier, Tente, Vaisselle, Sonorisation, Énergie… */
    private String categorie;

    @Column(nullable = false)
    private Integer quantiteTotale = 0;

    @Column(nullable = false)
    private Integer quantiteDisponible = 0;

    /** Prix de location unitaire (par évènement / période). */
    @Column(precision = 15, scale = 2)
    private BigDecimal prixLocation = BigDecimal.ZERO;

    /** Caution unitaire demandée. */
    @Column(precision = 15, scale = 2)
    private BigDecimal caution = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private EtatMateriel etat = EtatMateriel.BON;

    private Boolean actif = true;
}
