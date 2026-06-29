package yamrestor.restor.dto.request.administration;

import yamrestor.restor.enums.TypeImprimante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImprimanteRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotNull(message = "Le type est obligatoire")
    private TypeImprimante type;

    private String adresse;
    private String modele;
    private Boolean actif = true;
}
