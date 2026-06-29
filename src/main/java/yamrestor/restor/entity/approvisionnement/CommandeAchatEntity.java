package yamrestor.restor.entity.approvisionnement;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.StatutCommandeAchat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Commande d'achat (bon de commande fournisseur). */
@Entity
@Table(name = "commandes_achat")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE commandes_achat SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class CommandeAchatEntity extends BaseEntity {

    @Column(unique = true)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fournisseur_id", nullable = false)
    private FournisseurEntity fournisseur;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCommandeAchat statut = StatutCommandeAchat.BROUILLON;

    @Column(precision = 15, scale = 2)
    private BigDecimal montantTotal = BigDecimal.ZERO;

    private LocalDate dateReception;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @OneToMany(mappedBy = "commandeAchat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommandeAchatLigneEntity> lignes = new ArrayList<>();
}
