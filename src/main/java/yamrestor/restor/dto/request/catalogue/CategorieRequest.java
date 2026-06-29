package yamrestor.restor.dto.request.catalogue;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorieRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String description;
    private String code;
    private Boolean actif = true;
}
