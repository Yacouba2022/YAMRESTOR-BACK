package yamrestor.restor.entity.catalogue;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Matière première (ingrédient) : riz, huile, viande, poisson, épices…
 * Porte un stock courant simple ; les mouvements détaillés relèvent du module Stock (Phase 5).
 */
@Entity
@Table(name = "matieres_premieres")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE matieres_premieres SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class MatierePremiereEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Column(unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unite_id")
    private UniteEntity unite;

    @Column(precision = 15, scale = 2)
    private BigDecimal prixAchat;

    @Column(precision = 15, scale = 3)
    private BigDecimal stock = BigDecimal.ZERO;

    @Column(precision = 15, scale = 3)
    private BigDecimal stockMinimum;

    @Column(precision = 15, scale = 3)
    private BigDecimal seuilAlerte;

    private LocalDate datePeremption;

    private String emplacement;

    private Boolean actif = true;
}
