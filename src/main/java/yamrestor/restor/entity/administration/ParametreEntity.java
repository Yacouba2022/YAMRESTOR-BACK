package yamrestor.restor.entity.administration;

import yamrestor.restor.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Paramètres généraux du restaurant (ligne unique / singleton).
 * Informations d'identité, devise, TVA, préfixes de numérotation automatique.
 */
@Entity
@Table(name = "parametres")
@Getter
@Setter
@NoArgsConstructor
public class ParametreEntity extends BaseEntity {

    // ─── Identité ──────────────────────────────────────────────────────────────
    @Column(nullable = false)
    private String nomRestaurant = "Mon Restaurant";

    private String slogan;
    private String logo;
    private String email;
    private String telephone;
    private String telephone2;
    private String adresse;
    private String ville;
    private String pays;
    private String siteWeb;

    // ─── Devise & TVA ───────────────────────────────────────────────────────────
    private String devise = "XOF";
    private String symboleDevise = "FCFA";

    @Column(precision = 5, scale = 2)
    private BigDecimal tauxTvaDefaut = new BigDecimal("18.00");

    // ─── Numérotation automatique ────────────────────────────────────────────────
    private Boolean numerotationAuto = true;
    private String prefixeCommande = "CMD";
    private String prefixeTicket = "TKT";
    private String prefixeFacture = "FAC";
    private String prefixeReservation = "RES";

    // ─── Documents ────────────────────────────────────────────────────────────────
    @Column(columnDefinition = "TEXT")
    private String piedPageTicket;

    @Column(columnDefinition = "TEXT")
    private String conditionsGenerales;
}
