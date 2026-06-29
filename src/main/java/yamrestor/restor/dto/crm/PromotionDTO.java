package yamrestor.restor.dto.crm;

import yamrestor.restor.enums.TypePromotion;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PromotionDTO {
    private String guid;
    private String nom;
    private TypePromotion type;
    private BigDecimal valeur;
    private String code;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Boolean actif;
    /** Vrai si la promotion est applicable à l'instant présent (calculé). */
    private Boolean activeMaintenant;
}
