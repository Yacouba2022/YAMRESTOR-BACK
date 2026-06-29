package yamrestor.restor.dto.request.administration;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter @Setter
public class ProfilRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    private String description;
    private Boolean isActive = true;

    /** GUIDs des rôles regroupés par ce profil */
    private Set<String> roleGuids;

    /** GUIDs des permissions additionnelles attribuées directement au profil */
    private Set<String> permissionGuids;
}
