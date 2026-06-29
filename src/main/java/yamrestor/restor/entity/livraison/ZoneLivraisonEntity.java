package yamrestor.restor.entity.livraison;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Zone de livraison avec ses frais associés. */
@Entity
@Table(name = "zones_livraison")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE zones_livraison SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ZoneLivraisonEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Column(precision = 15, scale = 2)
    private BigDecimal frais = BigDecimal.ZERO;

    private Boolean actif = true;
}
