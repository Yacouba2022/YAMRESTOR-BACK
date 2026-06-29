package yamrestor.restor.dto.request.administration;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PermissionRequest {
    private String nom;

    @NotBlank(message = "Le code est obligatoire")
    private String code;

    private String description;
    private String module;
}
