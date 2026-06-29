package yamrestor.restor.dto.request.traiteur;

import yamrestor.restor.enums.EtatMateriel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MaterielTraiteurRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String categorie;

    @Min(value = 0, message = "La quantité doit être positive ou nulle")
    private Integer quantiteTotale;

    private EtatMateriel etat;
    private Boolean actif = true;
}
