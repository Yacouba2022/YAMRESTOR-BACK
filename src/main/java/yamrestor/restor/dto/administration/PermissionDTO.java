package yamrestor.restor.dto.administration;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PermissionDTO {
    private String guid;
    private String nom;
    private String code;
    private String description;
    private String module;
}
