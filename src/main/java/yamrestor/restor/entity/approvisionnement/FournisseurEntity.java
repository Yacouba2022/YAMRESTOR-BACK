package yamrestor.restor.entity.approvisionnement;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

/** Fournisseur de matières premières. Le solde représente le montant dû au fournisseur. */
@Entity
@Table(name = "fournisseurs")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE fournisseurs SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class FournisseurEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    private String telephone;
    private String email;
    private String adresse;

    /** Montant dû au fournisseur (augmente à la réception, diminue au paiement). */
    @Column(precision = 15, scale = 2)
    private BigDecimal solde = BigDecimal.ZERO;

    private Boolean actif = true;
}
