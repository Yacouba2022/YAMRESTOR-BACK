package yamrestor.restor.dto.rapport;

import lombok.*;

import java.math.BigDecimal;

/** Ligne du rapport « produits les plus vendus ». */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProduitVenduDTO {
    private String produitNom;
    private long quantite;
    private BigDecimal montant;
}
