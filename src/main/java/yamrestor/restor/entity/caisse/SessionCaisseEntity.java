package yamrestor.restor.entity.caisse;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.entity.administration.UserEntity;
import yamrestor.restor.enums.StatutSessionCaisse;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Session de caisse : ouverture avec un fond, fermeture avec comptage et calcul de l'écart. */
@Entity
@Table(name = "sessions_caisse")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE sessions_caisse SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class SessionCaisseEntity extends BaseEntity {

    @Column(unique = true)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caissier_id")
    private UserEntity caissier;

    @Column(precision = 15, scale = 2)
    private BigDecimal fondInitial = BigDecimal.ZERO;

    private LocalDateTime dateOuverture;
    private LocalDateTime dateFermeture;

    /** Total encaissé (tous modes) sur la session — figé à la fermeture. */
    @Column(precision = 15, scale = 2)
    private BigDecimal totalEncaisse = BigDecimal.ZERO;

    /** Total encaissé en espèces — sert au calcul du montant théorique en caisse. */
    @Column(precision = 15, scale = 2)
    private BigDecimal totalEspeces = BigDecimal.ZERO;

    /** Montant théorique attendu en caisse = fond initial + espèces encaissées. */
    @Column(precision = 15, scale = 2)
    private BigDecimal montantTheorique = BigDecimal.ZERO;

    /** Montant réellement compté à la fermeture (saisi par le caissier). */
    @Column(precision = 15, scale = 2)
    private BigDecimal fondFinalReel;

    /** Écart = fond final réel − montant théorique. */
    @Column(precision = 15, scale = 2)
    private BigDecimal ecart;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutSessionCaisse statut = StatutSessionCaisse.OUVERTE;
}
