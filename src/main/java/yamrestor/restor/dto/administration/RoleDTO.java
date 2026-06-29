package yamrestor.restor.dto.administration;

import lombok.*;

import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RoleDTO {
    private String guid;
    private String nom;
    private String code;
    private String description;
    private Boolean isActive;
    private Set<PermissionDTO> permissions;
}
