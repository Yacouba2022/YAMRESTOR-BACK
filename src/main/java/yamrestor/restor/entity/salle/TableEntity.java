package yamrestor.restor.entity.salle;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.StatutTable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/** Table physique du restaurant (« table » étant réservé en SQL, la table porte le nom tables_restaurant). */
@Entity
@Table(name = "tables_restaurant")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE tables_restaurant SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class TableEntity extends BaseEntity {

    @Column(nullable = false)
    private String numero;

    private Integer capacite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id")
    private SalleEntity salle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutTable statut = StatutTable.LIBRE;

    private Boolean actif = true;
}
