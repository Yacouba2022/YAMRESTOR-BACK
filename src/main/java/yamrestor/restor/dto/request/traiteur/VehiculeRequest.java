package yamrestor.restor.dto.request.traiteur;

import yamrestor.restor.enums.TypeVehicule;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VehiculeRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String immatriculation;

    @NotNull(message = "Le type est obligatoire")
    private TypeVehicule type;

    @Min(value = 0, message = "Le kilométrage doit être positif ou nul")
    private Integer kilometrage;

    private Boolean actif = true;
}
