package yamrestor.restor.dto.request.comptabilite;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategorieDepenseRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    private String code;
    private Boolean actif = true;
}
