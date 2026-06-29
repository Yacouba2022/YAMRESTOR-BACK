package yamrestor.restor.dto.salle;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SalleDTO {
    private String guid;
    private String nom;
    private String description;
    private Boolean actif;
}
