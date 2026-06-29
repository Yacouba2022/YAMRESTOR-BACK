package yamrestor.restor.dto.request.administration;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class RoleRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le code est obligatoire")
    private String code;

    private String description;
    private Boolean isActive = true;

    /** GUIDs des permissions accordées par ce rôle */
    private Set<String> permissionGuids;
}
