package yamrestor.restor.entity.catalogue;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * Catégorie de produits : Plats, Boissons, Desserts, Entrées, Menus, Cocktails…
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE categories SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class CategorieEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(unique = true)
    private String code;

    private Boolean actif = true;
}
