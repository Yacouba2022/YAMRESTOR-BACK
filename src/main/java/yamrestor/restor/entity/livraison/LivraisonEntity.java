package yamrestor.restor.entity.livraison;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.entity.administration.UserEntity;
import yamrestor.restor.entity.salle.CommandeEntity;
import yamrestor.restor.enums.StatutLivraison;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Livraison rattachée à une commande de type LIVRAISON. */
@Entity
@Table(name = "livraisons")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE livraisons SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class LivraisonEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    private CommandeEntity commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livreur_id")
    private UserEntity livreur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private ZoneLivraisonEntity zone;

    @Column(precision = 15, scale = 2)
    private BigDecimal frais = BigDecimal.ZERO;

    private String adresse;
    private String telephone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutLivraison statut = StatutLivraison.EN_ATTENTE;

    private LocalDateTime dateLivraison;

    @Column(columnDefinition = "TEXT")
    private String commentaire;
}
