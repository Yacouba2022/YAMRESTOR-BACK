package yamrestor.restor.entity.traiteur;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.TypePersonnelTraiteur;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Membre du personnel traiteur (cuisinier, serveur, chauffeur, décorateur, agent, coordinateur). */
@Entity
@Table(name = "personnel_traiteur")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE personnel_traiteur SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class PersonnelTraiteurEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypePersonnelTraiteur fonction = TypePersonnelTraiteur.SERVEUR;

    private String telephone;

    /** Coût journalier facturé pour la rentabilité d'une prestation. */
    @Column(precision = 15, scale = 2)
    private BigDecimal coutJournalier = BigDecimal.ZERO;

    private Boolean actif = true;
}
