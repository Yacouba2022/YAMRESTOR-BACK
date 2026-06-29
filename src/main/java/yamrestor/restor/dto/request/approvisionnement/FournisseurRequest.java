package yamrestor.restor.dto.request.approvisionnement;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FournisseurRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String telephone;
    private String email;
    private String adresse;
    private Boolean actif = true;
}
