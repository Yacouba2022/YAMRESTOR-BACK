package yamrestor.restor.dto.administration;

import lombok.*;

import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProfilDTO {
    private String guid;
    private String nom;
    private String description;
    private Boolean isActive;
    /** Rôles regroupés par le profil */
    private Set<RoleDTO> roles;
    /** Permissions additionnelles attribuées directement au profil (éditables) */
    private Set<PermissionDTO> permissionsSupplementaires;
    /** Permissions effectives (union des permissions des rôles + additionnelles) — lecture seule */
    private Set<String> permissions;
    /** Nombre d'utilisateurs assignés à ce profil */
    private Long usersCount;
}
