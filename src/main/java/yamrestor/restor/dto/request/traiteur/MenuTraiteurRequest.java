package yamrestor.restor.dto.request.traiteur;

import yamrestor.restor.enums.TypeMenuTraiteur;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class MenuTraiteurRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private TypeMenuTraiteur type;

    @DecimalMin(value = "0.0", message = "Le prix par personne doit être positif ou nul")
    private BigDecimal prixParPersonne;

    @DecimalMin(value = "0.0", message = "Le prix forfaitaire doit être positif ou nul")
    private BigDecimal prixForfaitaire;

    private String description;
    private Boolean actif = true;
}
