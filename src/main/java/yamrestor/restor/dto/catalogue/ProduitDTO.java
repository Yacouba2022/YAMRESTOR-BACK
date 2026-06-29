package yamrestor.restor.dto.catalogue;

import yamrestor.restor.enums.TypeProduit;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProduitDTO {
    private String guid;
    private String nom;
    private String code;
    private String description;
    private String categorieGuid;
    private String categorieNom;
    private TypeProduit type;
    private BigDecimal prixVente;
    private BigDecimal tauxTva;
    private String image;
    private Boolean disponible;
    private Integer tempsPreparation;
    private Boolean saisonnier;
    private Boolean actif;
}
