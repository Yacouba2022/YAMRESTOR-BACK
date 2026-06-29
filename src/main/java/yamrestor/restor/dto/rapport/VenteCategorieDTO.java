package yamrestor.restor.dto.rapport;

import lombok.*;

import java.math.BigDecimal;

/** Ligne du rapport « ventes par catégorie ». */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VenteCategorieDTO {
    private String categorie;
    private BigDecimal montant;
}
