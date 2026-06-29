package yamrestor.restor.entity.crm;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Fiche client : contacts, préférences, anniversaire, points de fidélité et remise. */
@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE clients SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ClientEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    private String telephone;
    private String email;
    private String adresse;

    /** Date de naissance (sert aux notifications d'anniversaire). */
    private LocalDate dateNaissance;

    @Column(columnDefinition = "TEXT")
    private String preferences;

    /** Solde de points de fidélité accumulés. */
    @Column(nullable = false)
    private Integer pointsFidelite = 0;

    /** Remise habituelle accordée au client (en %). */
    @Column(precision = 5, scale = 2)
    private BigDecimal remisePourcentage = BigDecimal.ZERO;

    private Boolean actif = true;
}
