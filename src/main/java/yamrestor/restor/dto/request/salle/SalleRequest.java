package yamrestor.restor.dto.request.salle;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SalleRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    private String description;
    private Boolean actif = true;
}
