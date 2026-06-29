package yamrestor.restor.entity.salle;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/** Salle / espace du restaurant : Terrasse, Salle VIP, Salle climatisée, Jardin, Bar… */
@Entity
@Table(name = "salles")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE salles SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class SalleEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean actif = true;
}
