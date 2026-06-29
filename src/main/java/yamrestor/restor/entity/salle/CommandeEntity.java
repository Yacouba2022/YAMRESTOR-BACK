package yamrestor.restor.entity.salle;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.entity.administration.UserEntity;
import yamrestor.restor.enums.Priorite;
import yamrestor.restor.enums.StatutCommande;
import yamrestor.restor.enums.TypeCommande;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Commande (sur place / à emporter / livraison). */
@Entity
@Table(name = "commandes")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE commandes SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class CommandeEntity extends BaseEntity {

    @Column(unique = true)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeCommande type = TypeCommande.SUR_PLACE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private TableEntity table;

    /** Serveur ayant pris la commande (utilisateur). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serveur_id")
    private UserEntity serveur;

    private String clientNom;
    private String clientTelephone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCommande statut = StatutCommande.EN_COURS;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priorite priorite = Priorite.NORMALE;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(precision = 15, scale = 2)
    private BigDecimal montantTotal = BigDecimal.ZERO;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommandeLigneEntity> lignes = new ArrayList<>();
}
