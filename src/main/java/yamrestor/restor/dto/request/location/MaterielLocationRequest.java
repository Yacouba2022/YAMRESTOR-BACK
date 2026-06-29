package yamrestor.restor.dto.request.location;

import yamrestor.restor.enums.EtatMateriel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class MaterielLocationRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String categorie;

    @Min(value = 0, message = "La quantité doit être positive ou nulle")
    private Integer quantiteTotale;

    @DecimalMin(value = "0.0", message = "Le prix de location doit être positif ou nul")
    private BigDecimal prixLocation;

    @DecimalMin(value = "0.0", message = "La caution doit être positive ou nulle")
    private BigDecimal caution;

    private EtatMateriel etat;
    private Boolean actif = true;
}
