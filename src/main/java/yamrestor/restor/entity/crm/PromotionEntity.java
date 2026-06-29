package yamrestor.restor.entity.crm;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.TypePromotion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/** Promotion / réduction : pourcentage, montant fixe ou happy hour, avec période et code coupon optionnel. */
@Entity
@Table(name = "promotions")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE promotions SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class PromotionEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypePromotion type = TypePromotion.POURCENTAGE;

    /** Valeur de la remise (% si POURCENTAGE/HAPPY_HOUR, montant si MONTANT_FIXE). */
    @Column(precision = 15, scale = 2)
    private BigDecimal valeur = BigDecimal.ZERO;

    /** Code coupon (optionnel, unique s'il est renseigné). */
    @Column(unique = true)
    private String code;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    /** Plage horaire pour un happy hour. */
    private LocalTime heureDebut;
    private LocalTime heureFin;

    private Boolean actif = true;

    /** Vrai si la promotion s'applique à l'instant donné (période + plage horaire happy hour). */
    @Transient
    public boolean estActive(LocalDate jour, LocalTime heure) {
        if (Boolean.FALSE.equals(actif)) return false;
        if (dateDebut != null && jour.isBefore(dateDebut)) return false;
        if (dateFin != null && jour.isAfter(dateFin)) return false;
        if (type == TypePromotion.HAPPY_HOUR && heureDebut != null && heureFin != null) {
            return !heure.isBefore(heureDebut) && !heure.isAfter(heureFin);
        }
        return true;
    }
}
