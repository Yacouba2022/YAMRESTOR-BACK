package yamrestor.restor.entity.crm;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.StatutBonAchat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Bon d'achat fidélité, obtenu en convertissant des points. */
@Entity
@Table(name = "bons_achat")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE bons_achat SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class BonAchatEntity extends BaseEntity {

    @Column(unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @Column(precision = 15, scale = 2)
    private BigDecimal montant = BigDecimal.ZERO;

    private Integer pointsUtilises;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutBonAchat statut = StatutBonAchat.DISPONIBLE;

    private LocalDate dateExpiration;
}
