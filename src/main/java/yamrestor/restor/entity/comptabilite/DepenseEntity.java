package yamrestor.restor.entity.comptabilite;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.ModePaiement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Dépense de fonctionnement (eau, électricité, salaires, loyer…). */
@Entity
@Table(name = "depenses")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE depenses SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class DepenseEntity extends BaseEntity {

    @Column(nullable = false)
    private String libelle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id")
    private CategorieDepenseEntity categorie;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal montant = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDate dateDepense;

    @Enumerated(EnumType.STRING)
    private ModePaiement mode = ModePaiement.ESPECES;

    @Column(columnDefinition = "TEXT")
    private String description;
}
