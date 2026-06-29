package yamrestor.restor.entity.comptabilite;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/** Catégorie de dépense : Eau, Électricité, Gaz, Internet, Salaires, Transport, Loyer, Divers… */
@Entity
@Table(name = "categories_depense")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE categories_depense SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class CategorieDepenseEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Column(unique = true)
    private String code;

    private Boolean actif = true;
}
