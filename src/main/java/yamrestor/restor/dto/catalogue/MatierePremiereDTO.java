package yamrestor.restor.dto.catalogue;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MatierePremiereDTO {
    private String guid;
    private String nom;
    private String code;
    private String uniteGuid;
    private String uniteNom;
    private BigDecimal prixAchat;
    private BigDecimal stock;
    private BigDecimal stockMinimum;
    private BigDecimal seuilAlerte;
    private LocalDate datePeremption;
    private String emplacement;
    private Boolean actif;
    /** Vrai si stock ≤ seuil d'alerte (calculé). */
    private Boolean stockFaible;
}
