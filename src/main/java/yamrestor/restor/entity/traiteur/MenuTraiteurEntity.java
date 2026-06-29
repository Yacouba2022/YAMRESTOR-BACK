package yamrestor.restor.entity.traiteur;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.TypeMenuTraiteur;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Menu traiteur : standard ou personnalisé, tarifé par personne ou au forfait. */
@Entity
@Table(name = "menus_traiteur")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE menus_traiteur SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class MenuTraiteurEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeMenuTraiteur type = TypeMenuTraiteur.STANDARD;

    @Column(precision = 15, scale = 2)
    private BigDecimal prixParPersonne;

    @Column(precision = 15, scale = 2)
    private BigDecimal prixForfaitaire;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean actif = true;
}
