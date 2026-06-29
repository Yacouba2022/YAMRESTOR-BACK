package yamrestor.restor.entity.caisse;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.ModePaiement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Une part d'un paiement réglée via un mode donné (espèces, CB, mobile money…). */
@Entity
@Table(name = "paiement_lignes")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE paiement_lignes SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class PaiementLigneEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paiement_id", nullable = false)
    private PaiementEntity paiement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModePaiement mode;

    @Column(precision = 15, scale = 2)
    private BigDecimal montant = BigDecimal.ZERO;

    /** Référence externe (n° de transaction CB / mobile money / chèque). */
    private String reference;
}
