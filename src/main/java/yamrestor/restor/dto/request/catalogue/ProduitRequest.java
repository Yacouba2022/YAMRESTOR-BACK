package yamrestor.restor.dto.request.catalogue;

import yamrestor.restor.enums.TypeProduit;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ProduitRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String code;
    private String description;
    private String categorieGuid;
    private TypeProduit type;

    @DecimalMin(value = "0.0", message = "Le prix de vente doit être positif ou nul")
    private BigDecimal prixVente;

    @DecimalMin(value = "0.0", message = "Le taux de TVA doit être positif ou nul")
    private BigDecimal tauxTva;

    private String image;
    private Boolean disponible = true;

    @Min(value = 0, message = "Le temps de préparation doit être positif ou nul")
    private Integer tempsPreparation;

    private Boolean saisonnier = false;
    private Boolean actif = true;
}
