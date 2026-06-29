package yamrestor.restor.entity.administration;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE permissions SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class PermissionEntity extends BaseEntity {

    /** Code technique unique, ex. "consulter-utilisateurs" */
    @Column(unique = true, nullable = false)
    private String code;

    /** Libellé court optionnel, ex. "Créer un utilisateur" */
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    /** Module fonctionnel de regroupement, ex. "utilisateur", "produit" */
    private String module;
}
