package yamrestor.restor.entity.catalogue;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Ligne d'une recette : une matière première, une quantité, une unité. */
@Entity
@Table(name = "recette_lignes")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE recette_lignes SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class RecetteLigneEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recette_id", nullable = false)
    private RecetteEntity recette;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_premiere_id", nullable = false)
    private MatierePremiereEntity matierePremiere;

    @Column(precision = 15, scale = 3)
    private BigDecimal quantite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unite_id")
    private UniteEntity unite;
}
