package yamrestor.restor.entity.location;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.StatutContratLocation;

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

/** Contrat de location de matériel pour un client sur une période. */
@Entity
@Table(name = "contrats_location")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE contrats_location SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ContratLocationEntity extends BaseEntity {

    @Column(unique = true)
    private String numero;

    @Column(nullable = false)
    private String clientNom;

    private String clientTelephone;

    @Column(nullable = false)
    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Column(precision = 15, scale = 2)
    private BigDecimal montantTotal = BigDecimal.ZERO;

    @Column(precision = 15, scale = 2)
    private BigDecimal cautionTotale = BigDecimal.ZERO;

    @Column(precision = 15, scale = 2)
    private BigDecimal montantPaye = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutContratLocation statut = StatutContratLocation.RESERVE;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @OneToMany(mappedBy = "contratLocation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ContratLocationLigneEntity> lignes = new ArrayList<>();
}
