package yamrestor.restor.entity.catalogue;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/** Unité de mesure (kg, L, pièce, portion…) utilisée par les produits et matières premières. */
@Entity
@Table(name = "unites")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE unites SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class UniteEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Column(unique = true, nullable = false)
    private String code;

    private String symbole;

    private Boolean actif = true;
}
